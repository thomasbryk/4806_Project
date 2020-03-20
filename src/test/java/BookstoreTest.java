import models.Book;
import models.Bookstore;
import org.junit.Before;
import org.junit.Test;

public class BookstoreTest {
    private String name = "Test Bookstore";
    private Bookstore bookstore;

    @Before
    public void setUp() {
        this.bookstore = new Bookstore((this.name));
    }

    /**
     * Test the addBook() method in Book.
     *
     * Expected condition: The Bookstore contains the Book and the Book contains the Bookstore
     */
    @Test
    public void testAddBook(){
        Book book = new Book("Test Book", "1234567890", "picture.jpeg", "book for testing purposes", "George Orwell", "96024 publishing");

        //Add's a Book to Bookstore
        this.bookstore.addBook(book);

        //Test to ensure this Book has registered this Bookstore
        assert(book.getBookstore().equals(this.bookstore));

        //Test to ensure the Bookstore has this Book
        assert(this.bookstore.getBooks().contains(book));
    }

    /**
     * Test the removeBook() method in Bookstore when Book is available.
     *
     * Expected condition: The Bookstore no longer contains the Book and the Book no longer has a Bookstore
     */
    @Test
    public void testRemoveBookWhenAvailable(){
        Book book = new Book("Test Book", "1234567890", "picture.jpeg", "book for testing purposes", "George Orwell", "96024 publishing");
        book.setId(1L);

        this.bookstore.addBook(book);
        this.bookstore.removeBookById(1L);

        assert(!this.bookstore.getBooks().contains(book));
        assert(book.getBookstore() == null);
    }


    /**
     * Test the removeBook() method in Bookstore when Book is not available.
     *
     * Expected condition: The Bookstore still contains the Book and the Book
     */
    @Test
    public void testRemoveBookWhenNotAvailable(){
        Book book = new Book("Test Book", "1234567890", "picture.jpeg", "book for testing purposes", "George Orwell", "96024 publishing");
        book.setId(1L);
        book.setAvailable(false);

        this.bookstore.addBook(book);
        this.bookstore.removeBookById(1L);

        assert(this.bookstore.getBooks().contains(book));
        assert(book.getBookstore() == this.bookstore);
    }
}
