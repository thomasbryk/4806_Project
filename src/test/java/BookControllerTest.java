

import static helpers.TestHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import repositories.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerTest {
    Book book;

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private BookRepository bookRepository;

    String path = "/api/books";

    @Before
    public void setup(){
        book = new Book("book_name", "book_isbn", "book_picture", "book_description", "book_author", "book_publisher");
        book = bookRepository.save(book);
    }

    @After
    public void cleanup(){
        bookRepository.deleteAll();
    }

    
    @Test
    public void TestACreateBook() throws Exception{
        Book book2 = new Book("book2_name", "book2_isbn", "book2_picture", "book2_description", "book2_author", "book2_publisher");

        mockMvc.perform(
            post(path)
            .content(asJsonString(book2))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("book2_name"));
    }

    @Test
    public void TestBGetBooks() throws Exception {
        mockMvc.perform(
            get(path)
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void TestCGetBookById() throws Exception {
        mockMvc.perform(
            get(path+"/"+book.getId())
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("book_name"));
    }
}