package controllers;

import models.*;
import repositories.AddressBookModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repositories.BookRepository;
import repositories.BookstoreOwnerRepository;
import repositories.BookstoreRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddressBookWebController {

    @Autowired
    private AddressBookModelRepository addressBookModelRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookstoreRepository bookstoreRepository;
    @Autowired
    private BookstoreOwnerRepository bookstoreOwnerRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/viewBookstoreOwner")
    public String viewBookStoreOwner(@RequestParam(value="bookstoreOwnerId") long bookstoreOwnerId,
                                     Model model) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        System.out.println(bookstoreOwner.getBookstores());
        model.addAttribute("bookstoreOwner", bookstoreOwner);
        return "viewBookstoreOwner";
    }

    @PostMapping("/newBookstoreOwner")
    public String index(@RequestParam(value="name") String name,
                        Model model) {
        BookstoreOwner bookstoreOwner = new BookstoreOwner(name);
        bookstoreOwnerRepository.save(bookstoreOwner);
        model.addAttribute("bookstoreOwner", bookstoreOwner);
        return "viewBookstoreOwner";
    }

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

    @PostMapping("/addBook")
    public String addBook(@RequestParam(value="bookstoreOwnerId") long bookstoreOwnerId,
                          @RequestParam(value="bookstoreId") long bookstoreId,
                          @RequestParam(value="name") String name,
                          @RequestParam(value="isbn") String isbn,
                          @RequestParam(value="picture") String picture,
                          @RequestParam(value="description") String description,
                          @RequestParam(value="author") String author,
                          @RequestParam(value="publisher") String publisher,
                          Model model) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        Bookstore bookstore = bookstoreOwner.getBookstore(bookstoreId);
        Book book = new Book(name, isbn, picture, description, author, publisher);
        bookstore.addBook(book);
        bookstoreOwnerRepository.save(bookstoreOwner);
        model.addAttribute("bookstoreOwner", bookstoreOwner);
        model.addAttribute("bookstore", bookstore);
        return "viewBookstore";
    }

    @PostMapping("/removeBook")
    public String removeBook(@RequestParam(value="bookstoreId") long bookstoreId,
                             @RequestParam(value="bookId") long bookId,
                             Model model ) {
        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
        bookstore.removeBook(bookId);
        bookstoreRepository.save(bookstore);
        model.addAttribute("bookstore", bookstore);
        return "viewBookstore";
    }

    @GetMapping("/viewBookstore")
    public String viewBookStore(@RequestParam(value="bookstoreOwnerId") long bookstoreOwnerId,
                                @RequestParam(value="bookstoreId") long bookstoreId,
                                Model model) {
        BookstoreOwner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
        Bookstore bookstore = bookstoreOwner.getBookstore(bookstoreId);
        model.addAttribute("bookstoreOwner", bookstoreOwner);
        model.addAttribute("bookstore", bookstore);
        return "viewBookstore";
    }
}
