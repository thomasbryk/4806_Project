package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import models.Book;
import models.Bookstore;
import models.BookstoreSpec;
import models.Sale;
import repositories.BookstoreRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/bookstores")
public class BookstoreController {

    @Autowired
    BookstoreRepository bookstoreRepository;
    

    /**
     * Returns a list of bookstores, given filters as parameters
     * @param spec see class
     * @return List of filtered bookstores
     */
    @GetMapping()
    public Iterable<Bookstore> getBookstores(BookstoreSpec spec){
        return bookstoreRepository.findAll(spec);
    }

    /**
     * Returns a bookstore given its ID
     * @param id Bookstore's ID
     * @return bookstore with given ID
     */
    @GetMapping("/{id}")
    public Bookstore getBookstore(@PathVariable long id){
        return bookstoreRepository.findById(id);
    }

    /**
     * Creates a bookstore given the serialized JSON object
     * @param bookstore Bookstore object
     * @return created bookstore object
     */
    @PostMapping()
    public Bookstore createBookstore(@RequestBody Bookstore bookstore){
        Bookstore b = bookstoreRepository.save(bookstore);
        return b;
    }

    /**
     * Appends a book to the given bookstore with ID
     * @param id ID of bookstore to add book to
     * @param book Book object to add to bookstore
     * @return List of books in the bookstore
     */
    @PutMapping("/{id}/books")
    public Iterable<Book> addBookToBookstore(@PathVariable long id, @RequestBody Book book){
        Bookstore b = bookstoreRepository.findById(id);
        b.addBook(book);
        bookstoreRepository.save(b);
        return b.getBooks();
    }

    /**
     * Returns list of a bookstore's books
     * @param id Bookstore ID
     * @return list of books for the Bookstore
     */
    @GetMapping("/{id}/books")
    public Iterable<Book> getBooksByBookstore(@PathVariable long id){
        Bookstore b = bookstoreRepository.findById(id);
        return b.getBooks();
    }

    /**
     * Retrieve available Book entities that are in Bookstore with given ID
     * @param id ID of Bookstore that will be searched for all Book entities
     * @return List of available Book entities found in Bookstore with given ID
     */
    @GetMapping("/{id}/books/available")
    public Iterable<Book> getBooksAvailableByBookstore(@PathVariable long id) {
        Bookstore bookstore = bookstoreRepository.findById(id);
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
     * @param id ID of Bookstore that will be searched for all Book entities
     * @return List of sold Book entities found in Bookstore with given ID bookstoreOwnerId
     */
    @GetMapping("/{id}/books/sold")
    public Iterable<Book> getBooksSoldByBookstore(@PathVariable long id) {
        Bookstore bookstore = bookstoreRepository.findById(id);
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
     * Returns sales of a given bookstore
     * @param id ID of bookstore
     * @return list of sales for the bookstore
     */
    @GetMapping("/{id}/sales")
    public Iterable<Sale> getSalesByBookstore(@PathVariable long id){
        Bookstore b = bookstoreRepository.findById(id);
        return b.getSales();
    }

    @DeleteMapping()
    public Bookstore deleteBookStore(@RequestBody Bookstore bookstore) {
        bookstoreRepository.delete(bookstore);
        return  bookstoreRepository.findById(bookstore.getId()).isPresent() ? bookstore : null;
    }
}


