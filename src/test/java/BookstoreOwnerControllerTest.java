

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

import models.Bookstore;
import models.BookstoreOwner;

import static helpers.TestHelper.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookstoreOwnerControllerTest {
    BookstoreOwner owner;

    @Autowired
    private MockMvc mockMvc;

    String path = "/api/bookstoreowners";

    
    @Test
    public void testACreateOwner() throws Exception{
        owner = new BookstoreOwner("owner_username","owner_password","owner_name");
        System.out.println(mockMvc);
        mockMvc.perform(
            post(path)
            .content(asJsonString(owner))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testBGetOwners() throws Exception {
        mockMvc.perform(
            get(path)
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testCGetOwnerById() throws Exception {
        mockMvc.perform(
            get(path+"/1")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("owner_name"))
            .andExpect(jsonPath("$.username").value("owner_username"));
    }

    @Test
    public void testDAddBookstoreToOwner() throws Exception {
        Bookstore bs = new Bookstore("bookstore_name");
        mockMvc.perform(
            put(path+"/1/bookstores")
            .content(asJsonString(bs))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
        
    }

    @Test
    public void testEGetOwnerBookstores() throws Exception {
        mockMvc.perform(
            get(path+"/1/bookstores")
            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].name").value("bookstore_name"));
    }

}