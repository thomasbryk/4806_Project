package controllers;

import models.*;
import repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookstoreRestController {

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

    @GetMapping("/api/getBookstoreOwner")
    public BookstoreOwner getBookstoreOwner(@RequestParam(value = "id") long id) {
        return bookstoreOwnerRepository.findById(id);
    }

    @GetMapping("/api/getBookstoreOwners")
    public Iterable<BookstoreOwner> getBookstoreOwner() {
        return bookstoreOwnerRepository.findAll();
    }

    @PostMapping("/api/newBookstoreOwner")
    public BookstoreOwner newBookstoreOwner(@RequestParam(value="name") String name) {
        BookstoreOwner bso = new BookstoreOwner(name);
        bookstoreOwnerRepository.save(bso);
        return bso;
    }

    @GetMapping("/api/getBookstore")
    public Bookstore getBookstore(@RequestParam(value = "id") long id) {
        return bookstoreRepository.findById(id);
    }

    @GetMapping("/api/getBookstores")
    public Iterable<Bookstore> getBookstores() {
        return bookstoreRepository.findAll();
    }

    @PostMapping("/api/newBookstore")
    public Bookstore newBookstore(@RequestParam(value="bookstoreOwnerId") long bookstoreOwnerId) {
        BookstoreOwner bso = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        Bookstore b = new Bookstore();
        b.setBookstoreOwner(bso);
        bso.addBookstore(b);
        bookstoreRepository.save(b);
        bookstoreOwnerRepository.save(bso);
        return b;
    }

    @GetMapping("/api/getBook")
    public Book getBook(@RequestParam(value = "id") long id) {
        return bookRepository.findById(id);
    }

    @GetMapping("/api/getBooks")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/api/newBook")
    public Book newBook(@RequestParam(value = "id") long id) {
        return bookRepository.findById(id);
    }
}