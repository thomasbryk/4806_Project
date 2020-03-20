import models.Book;
import org.junit.Before;
import org.junit.Test;

public class BookTest {
    private Book book;
    private String name = "Test Book";
    private String isbn = "1234567890";
    private String picture = "picture.jpeg";
    private String description = "book for testing purposes";
    private String author = "George Orwell";
    private String publisher = "96024 publishing";

    @Before
    public void setUp() {
        this.book = new Book(this.name, this.isbn, this.picture, this.description, this.author, this.publisher);
    }

    @Test
    public void testEquals(){
        Book secondBook = new Book(this.name, this.isbn, this.picture, this.description, this.author, this.publisher);
        assert (this.book.equals(secondBook));
    }
}