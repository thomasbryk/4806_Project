package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import models.Book;
import models.BookSpec;
import repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookRepository bookRepository;
    
    @GetMapping()
    public Iterable<Book> getBooks(BookSpec spec){
        return bookRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id){
        return bookRepository.findById(id);
    }

    @PostMapping()
    public Book createBook(@RequestBody Book book){
        Book b = bookRepository.save(book);
        return b;
    }


}


