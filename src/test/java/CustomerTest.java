import models.Book;
import models.ShoppingCart;
import org.junit.Before;

import models.Customer;
import org.junit.Test;

import java.util.ArrayList;

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

    @Test
    public void TestGetPurchasedBooks(){
        ShoppingCart shoppingCart = this.customer.getShoppingCart();

        Book book1 = new Book("Book 1", "book1-isbn", "book1-picture", "book1-description", "book1-author", "book1-publisher");
        Book book2 = new Book("Book 2", "book2-isbn", "book2-picture", "book2-description", "book2-author", "book2-publisher");

        shoppingCart.addBook(book1);
        shoppingCart.addBook(book2);
        shoppingCart.checkout();

        ArrayList<Book> purchasedBooks = new ArrayList<Book>();
        purchasedBooks.add(book1);
        purchasedBooks.add(book2);

        assert(this.customer.getPurchasedBooks().equals(purchasedBooks));
    }
}
