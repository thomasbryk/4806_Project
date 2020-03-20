import models.Book;
import org.junit.Before;
import org.junit.Test;

public class BookTest {
    private Book book;
    private String name;
    private String isbn;
    private String picture;
    private String description;
    private String author;
    private String publisher;

    @Before
    public void setUp() {
        this.name = "Test Book";
        this.isbn = "1234567890";
        this.picture = "picture.jpeg";
        this.description = "book for testing purposes";
        this.author = "George Orwell";
        this.publisher = "96024 publishing";
        this.book = new Book(this.name, this.isbn, this.picture, this.description, this.author, this.publisher);
    }
}