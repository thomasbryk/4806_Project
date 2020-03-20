import models.Bookstore;
import models.BookstoreOwner;
import org.junit.Before;
import org.junit.Test;

public class BookstoreOwnerTest {
    private String name = "Test Bookstore";
    private BookstoreOwner bookstoreOwner;

    @Before
    public void setUp() {
        this.bookstoreOwner = new BookstoreOwner(name);
    }

    /**
     * Test the addBookstore() method in BookstoreOwner.
     *
     * Expected condition: The BookstoreOwner now contains the Bookstore and the Bookstore contains this BookstoreOwner
     */
    @Test
    public void testAddBookstore(){
        Bookstore bookstore = new Bookstore("Test Bookstore");

        this.bookstoreOwner.addBookstore(bookstore);

        assert(this.bookstoreOwner.getBookstores().contains(bookstore));
        assert(bookstore.getBookstoreOwner().equals(this.bookstoreOwner));
    }

    /**
     * Test the removeBook() method in BookstoreOwner.
     *
     * Expected condition: The BookstoreOwner no longer contains the Bookstore and the Bookstore no longer has a BookstoreOwner
     */
    @Test
    public void testRemoveBookstore(){
        Bookstore bookstore = new Bookstore("Test Bookstore");
        bookstore.setId(1L);

        this.bookstoreOwner.addBookstore(bookstore);
        this.bookstoreOwner.removeBookstoreById(1L);

        assert(!this.bookstoreOwner.getBookstores().contains(bookstore));
        assert(bookstore.getBookstoreOwner() == null);
    }
}
