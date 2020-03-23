
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

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

import models.Book;
import models.Bookstore;
import models.BookstoreOwner;
import models.Customer;
import models.Sale;

import static helpers.TestHelper.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerTest {
    Customer customer;

    @Autowired
    private MockMvc mockMvc;

    String path = "/api/customers";

    
    @Test
    public void testACreateCustomer() throws Exception{
        customer = new Customer("cus_user", "cus_password", "cus_name", "cus_address", "cus_email", "cus_phoneNumber");

        mockMvc.perform(
            post(path)
            .content(asJsonString(customer))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testBGetCustomers() throws Exception {
        mockMvc.perform(
            get(path)
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testCGetCustomerById() throws Exception {
        mockMvc.perform(
            get(path+"/1")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("cus_name"));
    }

    @Test
    public void testDAddBookToCustomerCart() throws Exception {
        Book b = new Book("book_name", "book_isbn", "book_picture", "book_description", "book_author", "book_publisher");
        mockMvc.perform(
            put(path+"/1/shoppingcart")
            .content(asJsonString(b))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.books.length()").value(1))
            .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void testEGetCustomerShoppingCart() throws Exception {
        mockMvc.perform(
            get(path+"/1/shoppingcart")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.books.length()").value(1))
            .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void testFCheckoutCustomer() throws Exception {
        mockMvc.perform(
            post(path+"/1/checkout")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.books.length()").value(1))
            .andExpect(jsonPath("$.books[0].name").value("book_name"));
    }

    @Test
    public void testFGetCustomerSales() throws Exception {
        mockMvc.perform(
            get(path+"/1/sales")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].books[0].name").value("book_name"));
    }

}