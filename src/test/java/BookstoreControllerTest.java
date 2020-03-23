

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
import models.Sale;

import static helpers.TestHelper.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookstoreControllerTest {
    Bookstore bookstore;

    @Autowired
    private MockMvc mockMvc;

    String path = "/api/bookstores";

    
    @Test
    public void TestACreateBookstore() throws Exception{
        bookstore = new Bookstore("bookstore_name");
        System.out.println(mockMvc);
        mockMvc.perform(
            post(path)
            .content(asJsonString(bookstore))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void TestBGetBookstores() throws Exception {
        mockMvc.perform(
            get(path)
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void TestCGetBookstoreById() throws Exception {
        mockMvc.perform(
            get(path+"/1")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("bookstore_name"));
    }

    @Test
    public void TestDAddBookToBookstore() throws Exception {
        Book b = new Book("book_name", "book_isbn", "book_picture", "book_description", "book_author", "book_publisher");
        mockMvc.perform(
            put(path+"/1/books")
            .content(asJsonString(b))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].name").value("book_name"));
    }

    @Test
    public void TestEGetBookstoreBooks() throws Exception {
        mockMvc.perform(
            get(path+"/1/books")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].name").value("book_name"));
    }



}