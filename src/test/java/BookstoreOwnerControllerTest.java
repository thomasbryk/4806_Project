
import static helpers.TestHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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

import models.Bookstore;
import models.BookstoreOwner;
import repositories.BookstoreOwnerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookstoreOwnerControllerTest {
    BookstoreOwner owner;

    @Autowired
    private MockMvc mockMvc;

    String path = "/api/bookstoreowners";

    @Autowired
    private BookstoreOwnerRepository bookstoreOwnerRepository;

    private boolean lastTestCase = false;

    @Before
    public void setup(){
        owner = new BookstoreOwner("owner_username","owner_password","owner_name");
        owner = bookstoreOwnerRepository.save(owner);
    }

    @After
    public void cleanup(){
        bookstoreOwnerRepository.deleteAll();
    }
    
    @Test
    public void TestACreateOwner() throws Exception{
        BookstoreOwner owner2 = new BookstoreOwner("owner_username2","owner_password","owner_name2");
        System.out.println(mockMvc);
        mockMvc.perform(
            post(path)
            .content(asJsonString(owner2))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("owner_name2"));
    }

    @Test
    public void TestBGetOwners() throws Exception {
        mockMvc.perform(
            get(path)
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void TestCGetOwnerById() throws Exception {
        mockMvc.perform(
            get(path+"/"+owner.getId())
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("owner_name"))
            .andExpect(jsonPath("$.username").value("owner_username"));
    }

    @Test
    public void TestDAddBookstoreToOwner() throws Exception {
        

        Bookstore bs = new Bookstore("bookstore_name");
        mockMvc.perform(
            put(path+"/"+owner.getId()+"/bookstores")
            .content(asJsonString(bs))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
        
    }

    @Test
    public void TestEGetOwnerBookstores() throws Exception {
        TestDAddBookstoreToOwner();
        
        mockMvc.perform(
            get(path+"/"+owner.getId()+"/bookstores")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].name").value("bookstore_name"));
        }


    @Test
    public void TestFDeleteBookstoreOwner() throws Exception {
        mockMvc.perform(
            delete(path)
            .accept(MediaType.APPLICATION_JSON)
            .content(asJsonString(owner))
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").doesNotExist());
    }
}