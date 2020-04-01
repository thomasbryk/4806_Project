
import static helpers.TestHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Autowired CustomerRepository customerRepository;


    String path = "/api/customers";
    
    @Before
    public void setup(){
        customer = new Customer("cus_user", "cus_password", "cus_name", "cus_address", "cus_email", "cus_phoneNumber");
        customer = customerRepository.save(customer);
    }

    @After
    public void cleanup(){
        customerRepository.deleteAll();
    }

    @Test
    public void TestACreateCustomer() throws Exception {
        Customer customer2 = new Customer("cus_user2", "cus_password", "cus_name2", "cus_address", "cus_email", "cus_phoneNumber");

        mockMvc.perform(
                post(path)
                .content(asJsonString(customer2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("cus_name2"));
    }

    @Test
    public void TestBGetCustomers() throws Exception {

        mockMvc.perform(
                get(path)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void TestCGetCustomerById() throws Exception {
        mockMvc.perform(
                get(path + "/" +customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("cus_name"));
    }

    @Test
    public void TestDAddBookToCustomerCart() throws Exception {
        Book b = new Book("book_name", "book_isbn", "book_picture", "book_description", "book_author",
                "book_publisher");
        mockMvc.perform(put(path + "/"+customer.getId()+"/shoppingcart").content(asJsonString(b)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void TestEGetCustomerShoppingCart() throws Exception {
        TestDAddBookToCustomerCart();
        mockMvc.perform(get(path + "/"+customer.getId()+"/shoppingcart").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void TestFCheckoutCustomer() throws Exception {
        TestDAddBookToCustomerCart();
        mockMvc.perform(post(path + "/"+customer.getId()+"/checkout").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void TestFGetCustomerSales() throws Exception {
        TestFCheckoutCustomer();
        mockMvc.perform(get(path + "/"+customer.getId()+"/sales").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].books[0].name").value("book_name"));
    }
    @Test
    public void TestGDeleteCustomer() throws Exception{ 
        mockMvc.perform(
            delete(path)
            .accept(MediaType.APPLICATION_JSON)
            .content(asJsonString(customer))
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").doesNotExist());
    }

}