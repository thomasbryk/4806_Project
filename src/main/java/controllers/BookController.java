package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import models.Book;
import models.BookSpec;
import repositories.BookRepository;
import repositories.BookstoreRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookRepository bookRepository;
    
    /**
     * Returns the list of all books, given a filter based on its attributes
     * @param spec filtering interface, see file for filters defined
     * @return list of filtered books
     */
    @GetMapping()
    public Iterable<Book> getBooks(BookSpec spec){
        return bookRepository.findAll(spec);
    }

    /**
     * Gets a book given an id in the path
     * @param id id of book
     * @return Book with the given ID
     */
    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id){
        return bookRepository.findById(id);
    }

    /**
     * Creates a book given the JSON representation as a request body
     * @param book Serialized book from JSON
     * @return book created from the database
     */
    @PostMapping()
    public Book createBook(@RequestBody Book book){
        Book b = bookRepository.save(book);
        return b;
    }

    /**
     * Deletes a book given the JSON representation as a request body
     * @param book Serialized book from JSON
     * @return book if present or null otherwise 
     */
    @DeleteMapping()
    public Book deleteBook(@RequestBody Book book){
        bookRepository.delete(book);
        return bookRepository.findById(book.getId()).isPresent() ? book : null;
    }


}


