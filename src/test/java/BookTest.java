import models.Book;
import org.junit.Before;
import org.junit.Test;

public class BookTest {
    private Book book;
    private String name = "Test Book";
    private String isbn = "1234567890";
    private String picture = "picture.jpeg";
    private String description = "book for Testing purposes";
    private String author = "George Orwell";
    private String publisher = "96024 publishing";

    @Before
    public void setUp() {
        this.book = new Book(this.name, this.isbn, this.picture, this.description, this.author, this.publisher);
    }

    /**
     * Test the equals() method in Book.
     *
     * Expected outcome: The same book is equal and a different book is not equal.
     */
    @Test
    public void TestEquals(){
        Book sameBook = new Book(this.name, this.isbn, this.picture, this.description, this.author, this.publisher);
        Book differentBook = new Book("Different Test Book", "987654321", "different_picture.jpeg", "book for Testing purposes", "Mark Twain", "96024 publishing");

        assert (this.book.equals(sameBook));
        assert (!this.book.equals(differentBook));
    }
}