import org.junit.Before;

import models.Customer;

public class CustomerTest {
    private String name = "Test Customer";
    private String address = "Test Address";
    private String email = "test@email.com";
    private String phoneNumber = "123-4567";
    private String username = "bookstoreUsername";
    private String password = "bookstorePassword";
    private Customer customer;

    @Before
    public void setUp() {
        this.customer = new Customer(this.name, this.address, this.email, this.phoneNumber, this.username, this.password);
    }
}
