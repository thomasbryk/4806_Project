

import static helpers.TestHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
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

    private boolean lastTestCase = false;

    String path = "/api/books";

    
    @Test
    public void TestACreateBook() throws Exception{
        book = new Book("book_name", "book_isbn", "book_picture", "book_description", "book_author", "book_publisher");

        mockMvc.perform(
            post(path)
            .content(asJsonString(book))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
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
            get(path+"/1")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("book_name"))
            .andExpect(jsonPath("$.id").value(1));
        lastTestCase = true;
    }

    @After
    public void cleanup(){
        if(lastTestCase) bookRepository.deleteAll();
    }


}