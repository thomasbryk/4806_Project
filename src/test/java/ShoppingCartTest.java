import models.Book;
import models.Bookstore;
import models.Sale;
import models.ShoppingCart;
import org.junit.Before;
import org.junit.Test;

public class ShoppingCartTest {
    private ShoppingCart shoppingCart;

    @Before
    public void setUp() {
        this.shoppingCart = new ShoppingCart();
    }

    /**
     * Test the checkout() method in ShoppingCart.
     *
     * Expected outcome: The Book is removed from the ShoppingCart, the Book is no longer available, the Book is no longer in any ShoppingCart, the Book has a reference to the
     *                   Sale that is created, the Sale has a reference to the Book's Bookstore,
     */
    @Test
    public void TestAddBook(){
        Book book = new Book("Test Book", "1234567890", "picture.jpeg", "book for Testing purposes", "George Orwell", "96024 publishing");
        this.shoppingCart.addBook(book);

        assert(this.shoppingCart.getBooks().contains(book));
        assert(book.getShoppingCarts().contains(this.shoppingCart));
    }

    /**
     * Test the checkout() method in ShoppingCart when the cart is empty
     *
     * Expected outcome: null is returned from checkout()
     */
    @Test
    public void TestCheckoutEmptyCart(){
        assert(this.shoppingCart.checkout() == null);
    }

    /**
     * Test the checkout() method in ShoppingCart for a single Book
     *
     * Expected outcome: The book is removed from the ShoppingCart, the Book is no longer available for purchase, the Book is no longer in any ShoppingCart, the Book has a reference to the
     *                   Sale that is created, the Sale has a reference to the Book's Bookstore,
     */
    @Test
    public void TestCheckoutSingleBook(){
        Book book = new Book("Test Book", "1234567890", "picture.jpeg", "book for Testing purposes", "George Orwell", "96024 publishing");
        Bookstore bookstore = new Bookstore("Test Bookstore");
        bookstore.addBook(book);

        this.shoppingCart.addBook(book);
        Sale sale = this.shoppingCart.checkout();

        assert(this.shoppingCart.getBooks().isEmpty());
        assert(!book.getAvailable());
        assert(book.getShoppingCarts().isEmpty());
        assert(book.getSale().equals(sale));
        assert(sale.getBookstores().contains(bookstore));
    }

    /**
     * Test the checkout() method in ShoppingCart for multiple Book from the same bookstore.
     *
     * Expected outcome: The Books are removed from the ShoppingCart, the Books are no longer available for purchase, the Books are no longer in any ShoppingCart, the Books have a reference to the
     *                   Sale that is created, the Sale has a reference to the Book's Bookstore,
     */
    @Test
    public void TestCheckoutMultipleBooksFromOneBookstore(){
        Book book1 = new Book("Test Book 1", "1234567890", "picture1.jpeg", "book 1 for Testing purposes", "George Orwell", "96024 publishing");
        Book book2 = new Book("Test Book 2", "1234567890", "picture2.jpeg", "book 2 for Testing purposes", "Mark Twain", "96024 publishing");
        Bookstore bookstore = new Bookstore("Test Bookstore");
        bookstore.addBook(book1);
        bookstore.addBook(book2);

        this.shoppingCart.addBook(book1);
        this.shoppingCart.addBook(book2);
        Sale sale = this.shoppingCart.checkout();

        assert(this.shoppingCart.getBooks().isEmpty());
        assert(!book1.getAvailable());
        assert(!book2.getAvailable());
        assert(book1.getShoppingCarts().isEmpty());
        assert(book2.getShoppingCarts().isEmpty());
        assert(book1.getSale().equals(sale));
        assert(book2.getSale().equals(sale));
        assert(sale.getBookstores().contains(bookstore));
    }

    /**
     * Test the checkout() method in ShoppingCart for multiple books from multiple bookstore.
     *
     * Expected outcome: The Books are removed from the ShoppingCart, the Books are no longer available for purchase, the Books are no longer in any ShoppingCart, the Books have a reference to the
     *                   Sale that is created, the Sale has a reference to both the Book's Bookstores,
     */
    @Test
    public void TestCheckoutMultipleBooksFromMultipleBookstores(){
        Book book1 = new Book("Test Book 1", "1234567890", "picture1.jpeg", "book 1 for Testing purposes", "George Orwell", "96024 publishing");
        Book book2 = new Book("Test Book 2", "1234567890", "picture2.jpeg", "book 2 for Testing purposes", "Mark Twain", "96024 publishing");
        Bookstore bookstore1 = new Bookstore("Test Bookstore");
        Bookstore bookstore2 = new Bookstore("Test Bookstore");
        bookstore1.addBook(book1);
        bookstore2.addBook(book2);

        this.shoppingCart.addBook(book1);
        this.shoppingCart.addBook(book2);
        Sale sale = this.shoppingCart.checkout();

        assert(this.shoppingCart.getBooks().isEmpty());
        assert(!book1.getAvailable());
        assert(!book2.getAvailable());
        assert(book1.getShoppingCarts().isEmpty());
        assert(book2.getShoppingCarts().isEmpty());
        assert(book1.getSale().equals(sale));
        assert(book2.getSale().equals(sale));
        assert(sale.getBookstores().contains(bookstore1));
        assert(sale.getBookstores().contains(bookstore2));
    }
}
