package controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import models.Book;
import models.Bookstore;
import models.BookstoreOwner;
import models.Customer;
import models.Sale;
import models.ShoppingCart;
import repositories.BookRepository;
import repositories.BookstoreOwnerRepository;
import repositories.BookstoreRepository;
import repositories.CustomerRepository;
import repositories.SaleRepository;
import repositories.ShoppingCartRepository;


/**
 * Controller with endpoints used to create and access entities for the bookstore system
 */
@CrossOrigin(origins= "http://localhost:3000", allowedHeaders = "*")
@RestController
public class BookstoreRestController {

    /**
     * Repositories for each entity, used to store all entities in the system
     */
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


    //BookstoreOwner REST endpoints
    /**
     * Retrieve BookstoreOwner with given ID
     * @param bookstoreOwnerId ID of BookstoreOwner to be retrieved
     * @return BookstoreOwner found in repository from given ID
     */
    @GetMapping("/api/getBookstoreOwner")
    public BookstoreOwner getBookstoreOwner(@RequestParam(value = "bookstoreOwnerId") long bookstoreOwnerId) {
        return bookstoreOwnerRepository.findById(bookstoreOwnerId);
    }

    /**
     * Retrieve all BookstoreOwner entities in the system
     * @return List of all BookstoreOwner entities in the system
     */
    @GetMapping("/api/getBookstoreOwners")
    public Iterable<BookstoreOwner> getBookstoreOwner() {
        return bookstoreOwnerRepository.findAll();
    }

    /**
     * Create new BookstoreOwner with given name
     * @param bookstoreOwnerName Name for new BookstoreOwner
     * @param bookstoreOwnerUsername Username for new BookstoreOwner
     * @param bookstoreOwnerPassword Password for new BookstoreOwner
     * @return Newly created BookstoreOwner
     */
    @PostMapping("/api/newBookstoreOwner")
    public BookstoreOwner newBookstoreOwner(@RequestParam(value = "bookstoreOwnerName") String bookstoreOwnerName, @RequestParam(value = "bookstoreOwnerUsername") String bookstoreOwnerUsername, @RequestParam(value = "bookstoreOwnerPassword") String bookstoreOwnerPassword) {
        BookstoreOwner bookstoreOwner = new BookstoreOwner(bookstoreOwnerUsername, bookstoreOwnerPassword, bookstoreOwnerName);
        bookstoreOwnerRepository.save(bookstoreOwner);
        return bookstoreOwner;
    }


    //Bookstore REST endpoints
    /**
     * Retrieve all Bookstore entities in the system.
     * @return List of Bookstores in the system
     */
    @GetMapping("/api/getBookstores")
    public Iterable<Bookstore> getBookstores() {
        return bookstoreRepository.findAll();
    }

    /**
     * Retrieve Bookstore entities that are owned by given BookstoreOwner
     * @param bookstoreOwnerId ID of BookstoreOwner
     * @return List of Bookstores that are owned by given BookstoreOwner
     */
    @GetMapping("/api/getBookstoresByBookstoreOwner")
    public Iterable<Bookstore> getBookstoreByBookstoreOwner(@RequestParam(value = "bookstoreOwnerId") long bookstoreOwnerId) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        return bookstoreOwner.getBookstores();
    }

    /**
     * Create new Bookstore with given name and owned by given BookstoreOwner
     * @param bookstoreName Name for new Bookstore
     * @param bookstoreOwnerId ID of BookstoreOwner to be set as owner of new Bookstore
     * @return Newly created Bookstore
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/newBookstore")
    public Bookstore newBookstore(@RequestParam(value = "bookstoreName") String bookstoreName, @RequestParam(value = "bookstoreOwnerId") long bookstoreOwnerId) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        if (bookstoreOwner == null)
            return null;
        Bookstore bookstore = new Bookstore(bookstoreName);
        bookstore.setBookstoreOwner(bookstoreOwner);
        bookstoreOwner.addBookstore(bookstore);
        bookstoreOwnerRepository.save(bookstoreOwner);
        return bookstore;
    }


    //Book REST endpoints
    /**
     * Retrieve Book with given ID
     * @param bookId ID of Book to retrieve
     * @return Retrieved Book with given ID bookId
     */
    @GetMapping("/api/getBook")
    public Book getBook(@RequestParam(value = "bookId") long bookId) {
        return bookRepository.findById(bookId);
    }

    /**
     * Retrieve all Book entities in the system
     * @return List of all Book entities in the system
     */
    @GetMapping("/api/getBooks")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieve Book entities that are in Bookstore with given ID
     * @param bookstoreId ID of Bookstore that will be searched for all Book entities
     * @return List of Book entities found in Bookstore with given ID
     */
    @GetMapping("/api/getBooksByBookstore")
    public Iterable<Book> getBooksByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        if (bookstore == null)
            return null;
        return bookRepository.findByBookstore(bookstore);
    }

    /**
     * Retrieve available Book entities that are in Bookstore with given ID
     * @param bookstoreId ID of Bookstore that will be searched for all Book entities
     * @return List of available Book entities found in Bookstore with given ID
     */
    @GetMapping("/api/getBooksAvailableByBookstore")
    public Iterable<Book> getBooksAvailableByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        if (bookstore == null)
            return null;
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : bookstore.getBooks()){
            if (book.getAvailable())
                books.add(book);
        }
        if (books.size() == 0)
            return null;
        return books;
    }

    /**
     * Retrieve sold Book entities that are in Bookstore with given ID bookstoreOwnerId
     * @param bookstoreId ID of Bookstore that will be searched for all Book entities
     * @return List of sold Book entities found in Bookstore with given ID bookstoreOwnerId
     */
    @GetMapping("/api/getBooksSoldByBookstore")
    public Iterable<Book> getBooksSoldByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        if (bookstore == null)
            return null;
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : bookstore.getBooks()){
            if (!book.getAvailable())
                books.add(book);
        }
        if (books.size() == 0)
            return null;
        return books;
    }

    /**
     * Create new Book with given parameters
     * @param bookName Name of new Book
     * @param isbn ISBN of new Book
     * @param picture Picture of new Book
     * @param description Description of new Book
     * @param author Author of new Book
     * @param publisher Publisher of new Book
     * @param bookstoreId ID of Bookstore that new Book will be located in
     * @return Newly created Book
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/newBook")
    public Book newBook(@RequestParam(value = "bookName") String bookName, @RequestParam(value = "isbn") String isbn, @RequestParam(value = "picture") String picture, @RequestParam(value = "description") String description, @RequestParam(value = "author") String author, @RequestParam(value = "publisher") String publisher, @RequestParam(value = "bookstoreId") long bookstoreId) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        if (bookstore == null)
            return null;
        Book book = new Book(bookName, isbn, picture, description, author, publisher);
        bookstore.addBook(book);
        book.setBookstore(bookstore);
        bookstoreRepository.save(bookstore);
        return book;
    }


    //Customer REST endpoints
    /**
     * Retrieve customer with given ID
     * @param customerId ID of customer to retrieve
     * @return Retrieved Customer from given ID
     */
    @GetMapping("/api/getCustomer")
    public Customer getCustomer(@RequestParam(value = "customerId") long customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * Retrieve all Customer entities in the system
     * @return List of Customer entities in the system
     */
    @GetMapping("/api/getCustomers")
    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Create new Customer with given parameters
     * @param customerName Name of new Customer
     * @param address Address of new Customer
     * @param email Email of new Customer
     * @param phoneNumber Phone Number of new Customer
     * @param username Username for new Customer
     * @param password Password for new Customer
     * @return Newly created Customer
     */
    @PostMapping("/api/newCustomer")
    public Customer newCustomer(@RequestParam(value = "customerName") String customerName, @RequestParam(value = "address") String address, @RequestParam(value = "email") String email, @RequestParam(value = "phoneNumber") String phoneNumber, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        Customer customer = new Customer(username, password, customerName, address, email, phoneNumber);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        customer.setShoppingCart(shoppingCart);
        customerRepository.save(customer);
        return customer;
    }


    //Shopping Cart REST endpoints
    /**
     * Retrieve ShoppingCart linked to given Customer
     * @param customerId ID of Customer used to find linked ShoppingCart
     * @return ShoppingCart linked to given Customer
     */
    @GetMapping("/api/getShoppingCartByCustomer")
    public ShoppingCart getShoppingCartByCustomer(@RequestParam(value = "customerId") long customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null)
            return null;
        return shoppingCartRepository.findByCustomer(customer);
    }

    /**
     * Add given Book to ShoppingCart linked to given Customer
     * @param customerId ID of Customer used to find linked ShoppingCart
     * @param bookId ID of Book to add to ShoppingCart
     * @return ShoppingCart of given Customer, containing given Book
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/addBookToCustomersShoppingCart")
    public ShoppingCart addBookToShoppingCart(@RequestParam(value = "customerId") long customerId , @RequestParam(value = "bookId") long bookId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null)
            return null;
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Book book = bookRepository.findById(bookId);
        if (book == null)
            return null;
        shoppingCart.addBook(book);
        book.addShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }


    //Sale REST endpoints
    /**
     * Retrieve all Sale entities linked to given Customer
     * @param customerId ID of Customer
     * @return List of Sale entities linked to given Customer
     */
    @GetMapping("/api/getSalesByCustomer")
    public Iterable<Sale> getSalesByCustomer(@RequestParam(value = "customerId") long customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null)
            return null;
        return saleRepository.findByCustomer(customer);
    }

    /**
     * Retrieve all Sale entities linked to given Bookstore
     * @param bookstoreId ID of Bookstore
     * @return List of Sale entities linked to given Bookstore
     */
    @GetMapping("/api/getSalesByBookstore")
    public Iterable<Sale> getSalesByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        if (bookstore == null)
            return null;
        return bookstore.getSales();
    }

    /**
     * Create new Sale linked to given Customer
     * @param customerId ID of Customer
     * @return Newly create Sale linked to given Customer
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/newSale")
    public Sale newSale(@RequestParam(value = "customerId") long customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null)
            return null;
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Sale sale = shoppingCart.checkout();
        saleRepository.save(sale);
        return sale;
    }

    // /**
    //  * Endpoint for logging in, doesn't do anything significant other than start a session
    //  */
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    // @GetMapping("/login")
    // public String login(){
    //     return "successfully authenticated";
    // }
    
}