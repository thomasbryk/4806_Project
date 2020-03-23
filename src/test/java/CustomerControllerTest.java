
import static helpers.TestHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import models.Book;
import models.Customer;
import repositories.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerTest {
    Customer customer;



    @Autowired
    private MockMvc mockMvc;

    @Autowired CustomerRepository repository;

    private boolean lastTestCase = false;

    String path = "/api/customers";

    @Test
    public void TestACreateCustomer() throws Exception {
        customer = new Customer("cus_user", "cus_password", "cus_name", "cus_address", "cus_email", "cus_phoneNumber");

        mockMvc.perform(post(path).content(asJsonString(customer)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("cus_name"));
    }

    @Test
    public void TestBGetCustomers() throws Exception {

        mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void TestCGetCustomerById() throws Exception {
        mockMvc.perform(get(path + "/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("cus_name"));
    }

    @Test
    public void TestDAddBookToCustomerCart() throws Exception {
        Book b = new Book("book_name", "book_isbn", "book_picture", "book_description", "book_author",
                "book_publisher");
        mockMvc.perform(put(path + "/1/shoppingcart").content(asJsonString(b)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void TestEGetCustomerShoppingCart() throws Exception {
        mockMvc.perform(get(path + "/1/shoppingcart").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void TestFCheckoutCustomer() throws Exception {
        mockMvc.perform(post(path + "/1/checkout").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void TestFGetCustomerSales() throws Exception {
        mockMvc.perform(get(path + "/1/sales").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].books[0].name").value("book_name"));
        lastTestCase = true;
    }

    @After
    public void cleanup() {
        if(lastTestCase) 
            repository.deleteAll();
    }

}