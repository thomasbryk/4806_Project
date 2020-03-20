import models.Customer;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {
    private String name = "Test Customer";
    private String address = "Test Address";
    private String email = "test@email.com";
    private String phoneNumber = "123-4567";
    private Customer customer;

    @Before
    public void setUp() {
        this.customer = new Customer(this.name, this.address, this.email, this.phoneNumber);
    }


}
