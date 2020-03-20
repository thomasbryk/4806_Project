package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

@Controller
public class BookstoreDevWebController {

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

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
    
    
    @PostMapping("/newBookstoreOwner")
    public @ResponseBody BookstoreOwner newBookstoreOwner(@RequestBody BookstoreOwner bookstoreOwner) {
        bookstoreOwnerRepository.save(bookstoreOwner);
        return bookstoreOwner;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewBookstoreOwner")
    public String viewBookStoreOwner(@RequestParam(value="bookstoreOwnerId") long bookstoreOwnerId,
                                     Model model) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        model.addAttribute("bookstoreOwner", bookstoreOwner);
        return "viewBookstoreOwner";
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newBookstore")
    public String newBookstore(@RequestParam(value="bookstoreOwnerId") long bookstoreOwnerId,
                               Model model) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        Bookstore bookstore = new Bookstore();
        bookstoreOwner.addBookstore(bookstore);
        bookstoreOwnerRepository.save(bookstoreOwner);
        model.addAttribute("bookstoreOwner", bookstoreOwner);
        return "viewBookstoreOwner";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editBookstore")
    public String editBookstore(@RequestParam(value="bookstoreId") long bookstoreId,
                                Model model) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        model.addAttribute("bookstore", bookstore);
        return "editBookstore";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeBookstore")
    public String removeBookstore(@RequestParam(value="bookstoreOwnerId") long bookstoreOwnerId,
                                  @RequestParam(value="bookstoreId") long bookstoreId,
                                  Model model ) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        bookstoreOwner.removeBookstore(bookstoreId);
        bookstoreOwnerRepository.save(bookstoreOwner);
        bookstoreRepository.deleteById(bookstoreId);
        model.addAttribute("bookstoreOwner", bookstoreOwner);
        return "viewBookstoreOwner";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addBook")
    public String addBook(@RequestParam(value="bookstoreId") long bookstoreId,
                          @RequestParam(value="name") String name,
                          @RequestParam(value="isbn") String isbn,
                          @RequestParam(value="picture") String picture,
                          @RequestParam(value="description") String description,
                          @RequestParam(value="author") String author,
                          @RequestParam(value="publisher") String publisher,
                          Model model) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        Book book = new Book(name, isbn, picture, description, author, publisher);
        bookstore.addBook(book);
        bookstoreRepository.save(bookstore);
        model.addAttribute("bookstore", bookstore);
        return "editBookstore";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeBook")
    public String removeBook(@RequestParam(value="bookstoreId") long bookstoreId,
                             @RequestParam(value="bookId") long bookId,
                             Model model) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        bookstore.removeBook(bookId);
        bookstoreRepository.save(bookstore);
        bookRepository.deleteById(bookId);
        model.addAttribute("bookstore", bookstore);
        return "editBookstore";
    }


    @PostMapping("/newCustomer")
    public @ResponseBody Customer newCustomer(@RequestBody Customer customer) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        customer.setShoppingCart(shoppingCart);
        customerRepository.save(customer);
        return customer;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viewCustomer")
    public String viewCustomer(@RequestParam(value="customerId") long customerId,
                               Model model){
        Customer customer = customerRepository.findById(customerId);
        Iterable<Bookstore> bookstores = bookstoreRepository.findAll();
        model.addAttribute("bookstores", bookstores);
        model.addAttribute("customer", customer);
        return "viewCustomer";

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shopBookstore")
    public String shopBookstore(@RequestParam(value="customerId") long customerId,
                                @RequestParam(value="bookstoreId") long bookstoreId,
                                Model model){
        Customer customer = customerRepository.findById(customerId);
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        model.addAttribute("bookstore", bookstore);
        model.addAttribute("customer", customer);
        return "shopBookstore";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addBookToCart")
    public String addBookToCart(@RequestParam(value="customerId") long customerId,
                                @RequestParam(value="bookId") long bookId,
                                @RequestParam(value="shoppingCartId") long shoppingCartId,
                                Model model){
        Customer customer = customerRepository.findById(customerId);
        Iterable<Bookstore> bookstores = bookstoreRepository.findAll();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        Book book = bookRepository.findById(bookId);
        shoppingCart.addBook(book);
        book.addShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        Iterable<Sale> sales = saleRepository.findByCustomer(customer);
        model.addAttribute("bookstores", bookstores);
        model.addAttribute("customer", customer);
        model.addAttribute("sales", sales);
        return "viewCustomer";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/newOrder")
    public String createOrder(@RequestParam(value="shoppingCartId") long shoppingCartId,
                              Model model){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        Sale sale = shoppingCart.checkout();
        saleRepository.save(sale);
        shoppingCartRepository.save(shoppingCart);
        Customer customer = shoppingCart.getCustomer();
        customer.addSale(sale);
        Iterable<Bookstore> bookstores = bookstoreRepository.findAll();
        model.addAttribute("bookstores", bookstores);
        model.addAttribute("customer", customer);
        return "viewCustomer";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
