import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import models.*;
import repositories.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes= {application.WebLauncher.class})
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookstoreRestTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookstoreRepository bookstoreRepository;
    @Autowired
    private BookstoreOwnerRepository bookstoreOwnerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private SaleRepository saleRepository;

    static Long testBookstoreOwnerId;
    static String testBookstoreOwnerName = "testBookStoreOwner";
    static String testBookstoreOwnerUsername = "user_owner";
    static String testBookstoreOwnerPassword = "password_owner";
    static Long testBookstoreId;
    static String testBookstoreName = "testBookstoreName";
    static Long testCustomerId;
    static String testCustomerUsername = "user_customer";
    static String testCustomerPassword = "password_customer"; 
    static String testCustomerName = "testCustomer";
    static String testCustomerAddress = "123 Fake St.";
    static String testCustomerEmail = "test@email.com";
    static String testCustomerPhoneNumber = "555-1234";
    static Long testBookId;
    static String testBookName = "testBookName";
    static String testBookIsbn = "0123456789";
    static String testBookPicture = "testPicture.jpeg";
    static String testBookDescription = "This is a book for testing purposes.";
    static String testBookAuthor = "Test Author";
    static String testBookPublisher = "Test Publisher";

    /**
     * Creates a new BookstoreOwner via REST, then queries the bookstoreOwnerRepository to confirm that the BookstoreOwner has been created with the correct attributes.
     * @throws Exception
     */
    @Test
    @Order(1)
    public void createBookstoreOwner() throws Exception {
        this.mockMvc.perform(
                post("/api/newBookstoreOwner").param("bookstoreOwnerName", this.testBookstoreOwnerName).param("bookstoreOwnerUsername", this.testBookstoreOwnerUsername).param("bookstoreOwnerPassword", this.testBookstoreOwnerPassword))
                .andExpect(status().isOk());

        //Query repository for newly created BookstoreOwner
        List<BookstoreOwner> bookstoreOwners = this.bookstoreOwnerRepository.findByName(this.testBookstoreOwnerName);

        //Test that there is one BookstoreOwner in the repository
        assert(bookstoreOwners.size() == 1);

        //Test that the BookstoreOwner that exists in the repository is the newly created BookStoreOwner
        BookstoreOwner bookstoreOwner = bookstoreOwners.get(0);
        assert(bookstoreOwner.getName().equals(this.testBookstoreOwnerName));

        //Save ID of BookstoreOwner for future test case use
        this.testBookstoreOwnerId = bookstoreOwner.getId();
    }

    /**
     * Creates a new Bookstore for the test BookstoreOwner via REST, then queries the bookstoreOwnerRepository to confirm the Bookstore has been created with the correct attributes and added to the BookstoreOwners bookstores
     * @throws Exception
     */
    @Test
    @Order(2)
    @WithMockUser(username = "user2", password = "pass2", roles="ADMIN")
    public void createBookstore() throws Exception {
        this.mockMvc.perform(
                post("/api/newBookstore")
                        .param("bookstoreName", this.testBookstoreName)
                        .param("bookstoreOwnerId", this.testBookstoreOwnerId.toString()))
                .andExpect(status().isOk());

        //Query repository for BookstoreOwner created in createBookstoreOwner() test
        Optional<BookstoreOwner> bookstoreOwner = this.bookstoreOwnerRepository.findById(this.testBookstoreOwnerId);

        //Get all Bookstores from the BookstoreOwner
        List<Bookstore> bookstores = new ArrayList<Bookstore>(bookstoreOwner.get().getBookstores());

        //Test that there is one Bookstore in the BookstoreOwner (from the repository)
        assert(bookstores.size() == 1);

        //Test that the Bookstore that exists in the repository is the newly created BookStoreOwner
        Bookstore bookstore = bookstores.get(0);
        assert(bookstore.getName().equals(this.testBookstoreName));

        //Save ID of BookstoreOwner for future test case use
        this.testBookstoreId = bookstore.getId();
    }

    /**
     * Creates a new Book for the BookstoreOwner's first Bookstore via REST, then queries bookstoreRepository to confirm the Book has been added to the Bookstore with the correct attributes.
     * @throws Exception
     */
    @Test
    @Order(3)
    @WithMockUser(username = "user2", password = "pass2", roles="ADMIN")
    public void createBook() throws Exception {
        this.mockMvc.perform(
                post("/api/newBook")
                        .param("bookName", this.testBookName)
                        .param("isbn", this.testBookIsbn)
                        .param("picture", this.testBookPicture)
                        .param("description", this.testBookDescription)
                        .param("author", this.testBookAuthor)
                        .param("publisher", this.testBookPublisher)
                        .param("bookstoreId", this.testBookstoreId.toString()))
                .andExpect(status().isOk());

        //Query repository for BookstoreOwner created in createBookstoreOwner() test
        Optional<Bookstore> optionalBookstore = this.bookstoreRepository.findById(this.testBookstoreId);
        Bookstore bookstore = optionalBookstore.get();

        //Get all Books from the Bookstore
        List<Book> books = bookstore.getBooks();

        //Test that there is one Book in the Bookstore (from the repository)
        assert(books.size() == 1);

        //Test that the Book that exists in the repository is the newly created Book
        Book book = books.get(0);
        assert(book.getName().equals(this.testBookName)
            && book.getIsbn().equals(this.testBookIsbn)
            && book.getPicture().equals(this.testBookPicture)
            && book.getDescription().equals(this.testBookDescription)
            && book.getAuthor().equals(this.testBookAuthor)
            && book.getPublisher().equals(this.testBookPublisher));

        //Save ID of Book for future test case use
        this.testBookId = book.getId();
    }

    /**
     * Creates a new Customer via REST, then queries customerRepository to confirm customer has been created with the correct attributes.
     * @throws Exception
     */
    @Test
    @Order(4)
    public void createCustomer() throws Exception {
        this.mockMvc.perform(
                post("/api/newCustomer")
                        .param("customerName", this.testCustomerName)
                        .param("address", this.testCustomerAddress)
                        .param("email", this.testCustomerEmail)
                        .param("phoneNumber", this.testCustomerPhoneNumber)
                        .param("username",this.testCustomerUsername)
                        .param("password",this.testCustomerPassword)
                        )
                .andExpect(status().isOk());

        //Query repository for newly created Customer
        List<Customer> customers = this.customerRepository.findByName(this.testCustomerName);

        //Test that there is one Customer in the repository
        assert(customers.size() == 1);

        //Test that the Customer that exists in the repository is the newly created Customer
        Customer customer = customers.get(0);
        assert(customer.getName().equals(this.testCustomerName)
            && customer.getAddress().equals(this.testCustomerAddress)
            && customer.getEmail().equals(this.testCustomerEmail)
            && customer.getPhoneNumber().equals(this.testCustomerPhoneNumber));

        //Save ID of Customer for future test case use
        this.testCustomerId = customer.getId();
    }

    /**
     * Adds a Book to a Customer's ShoppingCart via REST, then queries customerRepository to confirm that the Customer's ShoppingCart contains the Book
     * @throws Exception
     */
    @Test
    @Order(5)
    @WithMockUser(username = "user1", password = "pass1", roles="USER")
    public void addBookToCustomersShoppingCart() throws Exception {
        this.mockMvc.perform(
                post("/api/addBookToCustomersShoppingCart")
                        .param("customerId", this.testCustomerId.toString())
                        .param("bookId", this.testBookId.toString()))
                .andExpect(status().isOk());

        //Query repository for newly created test Customer
        Optional<Customer> optionalCustomer = this.customerRepository.findById(this.testCustomerId);
        Customer customer = optionalCustomer.get();

        //Get the Customer's ShoppingCart
        ShoppingCart shoppingCart = customer.getShoppingCart();

        //Get the Books from the ShoppingCart
        List<Book> books = new ArrayList<Book>(shoppingCart.getBooks());

        //Test that there is one Book in the Shopping Cart
        assert(books.size() == 1);

        //Test that the Book is the testBook
        Book book = books.get(0);
        assert(book.getName().equals(this.testBookName) && book.getId() == this.testBookId);
    }

    /**
     * Creates a Sale for Customer via Rest, then queries the customerRepository to determine whether:
     *   - Customer's ShoppingCart is now empty
     *   - Customer contains a single Sale
     *   - The Sale contains only the Book that was previously in the Customer's Shopping Cart
     *   - The Book is no longer available for Sale
     *   - The Book is from the Bookstore that it was bought from
     *   - The Bookstore associated with the Sale is the same as the Bookstore that the Book is from
     * @throws Exception
     */
    @Test
    @Order(6)
    @WithMockUser(username = "user1", password = "pass1", roles="USER")
    public void newSale() throws Exception {
        this.mockMvc.perform(
                post("/api/newSale")
                        .param("customerId", this.testCustomerId.toString()))
                .andExpect(status().isOk());

        //Query repository for newly created test Customer
        Optional<Customer> optionalCustomer = this.customerRepository.findById(this.testCustomerId);
        Customer customer = optionalCustomer.get();

        //Get the Customer's ShoppingCart
        ShoppingCart shoppingCart = customer.getShoppingCart();

        //Test that the ShoppingCart is now empty
        assert(shoppingCart.getBooks().isEmpty());

        //Get the Sale from the Customer
        List<Sale> sales = new ArrayList<Sale>(customer.getSales());

        //Test that there is only a single sale
        assert(sales.size() == 1);

        //Get the Sale from the Customer
        Sale sale = sales.get(0);

        //Get the Books from the Sale
        List<Book> books = new ArrayList<Book>(sale.getBooks());

        //Test that the sale only has one book
        assert(books.size() == 1);

        Book book = books.get(0);
        //Test that the book is no longer available
        assert(!book.getAvailable());

        //Test that the Book belongs to TestBookStore
        assert(book.getBookstore().getId() == this.testBookstoreId);

        //Test that the Sale is associated with TestBookStore
        ArrayList<Bookstore> bookstores = new ArrayList<Bookstore>(sale.getBookstores());
        Bookstore bookstore = bookstores.get(0);
        assert(bookstore.getId() == this.testBookstoreId && bookstore.getName().equals(this.testBookstoreName));
    }

    /**
     * Queries for a BookstoreOwner by bookstoreOwnerId via REST, compares JSON returned to expected JSON value
     * Expected value:{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}
     * @throws Exception
     */
    @Test
    public void getBookstoreOwner() throws Exception {
        String expectedResult = String.format("{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}",
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBookstoreOwner").param("bookstoreOwnerId", this.testBookstoreOwnerId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for all BookstoreOwners via REST, compares JSON returned to expected JSON value
     * Expected value:[{"id": 2,"username": "user2","password": "pass2","role": "ADMIN","name": "Bookstore owner 1"},{"id": 3,"username": "user_owner","password": "password_owner","role": "ADMIN","name": "testBookStoreOwner"}]
     * @throws Exception
     */
    @Test
    public void getBookstoreOwners() throws Exception {
        String expectedResult = String.format("[{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}]",
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBookstoreOwners"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for all Bookstores via REST, compares JSON returned to expected JSON value
     * Expected value:[{"id":2,"name":"testBookstoreName","bookstoreOwner":{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}}]
     * @throws Exception
     */
    @Test
    public void getBookstores() throws Exception {
        String expectedResult = String.format("[{\"id\":%d,\"name\":\"%s\",\"bookstoreOwner\":{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}}]",
                this.testBookstoreId,
                this.testBookstoreName,
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBookstores"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for all Bookstores for a BookstoreOwner via REST, compares JSON returned to expected JSON value
     * Expected value:[{"id":2,"name":"testBookstoreName","bookstoreOwner":{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}}]
     * @throws Exception
     */
    @Test
    public void getBookstoresByBookstoreOwner() throws Exception {
        String expectedResult = String.format("[{\"id\":%d,\"name\":\"%s\",\"bookstoreOwner\":{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}}]",
                this.testBookstoreId,
                this.testBookstoreName,
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBookstoresByBookstoreOwner").param("bookstoreOwnerId", this.testBookstoreOwnerId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for all Books via REST, compares JSON returned to expected JSON value
     * Expected value:[{"id":3,"name":"testBookName","isbn":"0123456789","picture":"testPicture.jpeg","description":"This is a book for testing purposes.","author":"Test Author","publisher":"Test Publisher","available":false,"bookstore":{"id":2,"name":"testBookstoreName","bookstoreOwner":{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}}}]
     * @throws Exception
     */
    @Test
    public void getBooks() throws Exception {
        String expectedResult = String.format("[{\"id\":%d,\"name\":\"%s\",\"isbn\":\"%s\",\"picture\":\"%s\",\"description\":\"%s\",\"author\":\"%s\",\"publisher\":\"%s\",\"available\":%s,\"bookstore\":{\"id\":%d,\"name\":\"%s\",\"bookstoreOwner\":{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}}}]",
                this.testBookId,
                this.testBookName,
                this.testBookIsbn,
                this.testBookPicture,
                this.testBookDescription,
                this.testBookAuthor,
                this.testBookPublisher,
                false,
                this.testBookstoreId,
                this.testBookstoreName,
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBooks"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for a Books by bookId via REST, compares JSON returned to expected JSON value
     * Expected value:{"id":3,"name":"testBookName","isbn":"0123456789","picture":"testPicture.jpeg","description":"This is a book for testing purposes.","author":"Test Author","publisher":"Test Publisher","available":false,"bookstore":{"id":2,"name":"testBookstoreName","bookstoreOwner":{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}}}
     * @throws Exception
     */
    @Test
    public void getBook() throws Exception {
        String expectedResult = String.format("{\"id\":%d,\"name\":\"%s\",\"isbn\":\"%s\",\"picture\":\"%s\",\"description\":\"%s\",\"author\":\"%s\",\"publisher\":\"%s\",\"available\":%s,\"bookstore\":{\"id\":%d,\"name\":\"%s\",\"bookstoreOwner\":{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}}}",
                this.testBookId,
                this.testBookName,
                this.testBookIsbn,
                this.testBookPicture,
                this.testBookDescription,
                this.testBookAuthor,
                this.testBookPublisher,
                false,
                this.testBookstoreId,
                this.testBookstoreName,
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBook").param("bookId", this.testBookId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for a Books for a specific BookStore via REST, compares JSON returned to expected JSON value
     * Expected value:[{"id":3,"name":"testBookName","isbn":"0123456789","picture":"testPicture.jpeg","description":"This is a book for testing purposes.","author":"Test Author","publisher":"Test Publisher","available":false,"bookstore":{"id":2,"name":"testBookstoreName","bookstoreOwner":{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}}}]
     * @throws Exception
     */
    @Test
    public void getBooksByBookstore() throws Exception {
        String expectedResult = String.format("[{\"id\":%d,\"name\":\"%s\",\"isbn\":\"%s\",\"picture\":\"%s\",\"description\":\"%s\",\"author\":\"%s\",\"publisher\":\"%s\",\"available\":%s,\"bookstore\":{\"id\":%d,\"name\":\"%s\",\"bookstoreOwner\":{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}}}]",
                this.testBookId,
                this.testBookName,
                this.testBookIsbn,
                this.testBookPicture,
                this.testBookDescription,
                this.testBookAuthor,
                this.testBookPublisher,
                false,
                this.testBookstoreId,
                this.testBookstoreName,
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBooksByBookstore").param("bookstoreId", this.testBookstoreId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for a Books for a specific BookStore that are available for sale via REST, compares JSON returned to expected JSON value
     * Expected value:{"id":2,"name":"testBookstoreName","bookstoreOwner":{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}}
     * @throws Exception
     */
    @Test
    public void getBooksAvailableByBookstore() throws Exception {
        String expectedResult = "";
        this.mockMvc.perform(
                get("/api/getBooksAvailableByBookstore").param("bookstoreId", this.testBookstoreId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    /**
     * Queries for a Books for a specific BookStore that have been sold via REST, compares JSON returned to expected JSON value
     * Expected value:[{"id":3,"name":"testBookName","isbn":"0123456789","picture":"testPicture.jpeg","description":"This is a book for testing purposes.","author":"Test Author","publisher":"Test Publisher","available":false,"bookstore":{"id":2,"name":"testBookstoreName","bookstoreOwner":{"id":1,"username":"user_owner","password":"password_owner","role":"ADMIN","name":"testBookStoreOwner"}}}]
     * @throws Exception
     */
    @Test
    public void getBooksSoldByBookstore() throws Exception {
        String expectedResult = String.format("[{\"id\":%d,\"name\":\"%s\",\"isbn\":\"%s\",\"picture\":\"%s\",\"description\":\"%s\",\"author\":\"%s\",\"publisher\":\"%s\",\"available\":%s,\"bookstore\":{\"id\":%d,\"name\":\"%s\",\"bookstoreOwner\":{\"id\":%d,\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"name\":\"%s\"}}}]",
                this.testBookId,
                this.testBookName,
                this.testBookIsbn,
                this.testBookPicture,
                this.testBookDescription,
                this.testBookAuthor,
                this.testBookPublisher,
                false,
                this.testBookstoreId,
                this.testBookstoreName,
                this.testBookstoreOwnerId,
                this.testBookstoreOwnerUsername,
                this.testBookstoreOwnerPassword,
                "ADMIN",
                this.testBookstoreOwnerName);
        this.mockMvc.perform(
                get("/api/getBooksSoldByBookstore").param("bookstoreId", this.testBookstoreId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}