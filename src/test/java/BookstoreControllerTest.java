

import static helpers.TestHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
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

import models.Book;
import models.Bookstore;
import repositories.BookstoreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookstoreControllerTest {
    Bookstore bookstore;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookstoreRepository bookstoreRepository;


    String path = "/api/bookstores";

    @Before
    public void setup(){
        bookstore = new Bookstore("bookstore_name");
        bookstore = bookstoreRepository.save(bookstore);
    }

    @After
    public void cleanup(){
        bookstoreRepository.deleteAll();
    }

    
    @Test
    public void TestACreateBookstore() throws Exception{
        Bookstore bookstore2 = new Bookstore("bookstore_name2");
        System.out.println(mockMvc);
        mockMvc.perform(
            post(path)
            .content(asJsonString(bookstore2))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("bookstore_name2"));
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
            get(path+"/"+bookstore.getId())
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("bookstore_name"));
    }

    @Test
    public void TestDAddBookToBookstore() throws Exception {
        Book b = new Book("book_name", "book_isbn", "book_picture", "book_description", "book_author", "book_publisher");
        mockMvc.perform(
            put(path+"/"+bookstore.getId()+"/books")
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
        TestDAddBookToBookstore();

        mockMvc.perform(
            get(path+"/"+bookstore.getId()+"/books")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].name").value("book_name"));
    }


    



}