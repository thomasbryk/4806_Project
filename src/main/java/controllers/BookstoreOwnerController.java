package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import models.Bookstore;
import models.BookstoreOwner;
import models.BookstoreOwnerSpec;
import repositories.BookstoreOwnerRepository;

@RestController
@RequestMapping("/api/bookstoreowners")
public class BookstoreOwnerController {

    @Autowired
    BookstoreOwnerRepository bookstoreOwnerRepository;
    
    @GetMapping()
    public Iterable<BookstoreOwner> getBookstoreOwners(BookstoreOwnerSpec spec){
        return bookstoreOwnerRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public BookstoreOwner getBookstoreOwner(@PathVariable long id){
        return bookstoreOwnerRepository.findById(id);
    }

    @PostMapping()
    public BookstoreOwner createBookstoreOwner(@RequestBody BookstoreOwner bookstoreOwner){
        BookstoreOwner b = bookstoreOwnerRepository.save(bookstoreOwner);
        return b;
    }

    @GetMapping("/{id}/bookstores")
    public Iterable<Bookstore> getBookstoresByBookstoreOwner(@PathVariable long id){
        BookstoreOwner b = bookstoreOwnerRepository.findById(id);
        return b.getBookstores();
    }

    @PutMapping("/{id}/bookstores")
    public Iterable<Bookstore> addBookstoreToOwner(@PathVariable long id, @RequestBody Bookstore bookstore){
        BookstoreOwner b = bookstoreOwnerRepository.findById(id);
        b.addBookstore(bookstore);
        bookstoreOwnerRepository.save(b);
        return b.getBookstores();
    }
}


