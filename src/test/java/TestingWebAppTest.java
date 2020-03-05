import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
public class TestingWebAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAddressBook() throws Exception {
        this.mockMvc.perform(post("/api/newAddressBook")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/api/addBuddy").param("id", "1").param("name", "ThomasTheBrykenator").param("phonenumber", "12345")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/api/getAddressBook").param("id", "1")).andDo(print()).andExpect(status().isOk());
    }
}