package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    
    /**
     * Returns a list of bookstore owners, given filtering parameters defined in @BookstoreOwnerSpec
     * @param spec Interface defining the filtering parameters
     * @return list of BookstoreOwners with filters applied
     */
    @GetMapping()
    public Iterable<BookstoreOwner> getBookstoreOwners(BookstoreOwnerSpec spec){
        return bookstoreOwnerRepository.findAll(spec);
    }

    /**
     * Returns a bookstore owner given an ID in the path
     * @param id ID of the bookstore owner
     */
    @GetMapping("/{id}")
    public BookstoreOwner getBookstoreOwner(@PathVariable long id){
        return bookstoreOwnerRepository.findById(id);
    }

    /**
     * Creates a bookstore owner given a JSON representation of the owner. Ex: {"name":"bookstore_owner"}
     * @param bookstoreOwner Owner to create
     * @return Created owner with an ID
     */
    @PostMapping()
    public BookstoreOwner createBookstoreOwner(@RequestBody BookstoreOwner bookstoreOwner){
        BookstoreOwner b = bookstoreOwnerRepository.save(bookstoreOwner);
        return b;
    }

    /**
     * Returns the list of bookstores for a given bookstore owner's ID
     * @param id Bookstore Owner ID
     * @return list of bookstores the owner has
     */
    @GetMapping("/{id}/bookstores")
    public Iterable<Bookstore> getBookstoresByBookstoreOwner(@PathVariable long id){
        BookstoreOwner b = bookstoreOwnerRepository.findById(id);
        return b.getBookstores();
    }

    /**
     * Appends a bookstore to the owner's list of bookstores
     * @param id Bookstore owner 
     * @param bookstore Bookstore to add 
     * @return List of bookstores the owner has
     */
    @PutMapping("/{id}/bookstores")
    public Iterable<Bookstore> addBookstoreToOwner(@PathVariable long id, @RequestBody Bookstore bookstore){
        BookstoreOwner b = bookstoreOwnerRepository.findById(id);
        b.addBookstore(bookstore);
        bookstoreOwnerRepository.save(b);
        return b.getBookstores();
    }
    
    /**
     * Deletes a bookstore owner given the JSON representation as a request body
     * @param bookstoreOwner Serialized bookstoreowner from JSON
     * @return bookstoreOwner if present or null otherwise 
     */
    @DeleteMapping()
    public BookstoreOwner deleteBookStoreOwner(@RequestBody BookstoreOwner bookstoreowner) {
      bookstoreOwnerRepository.delete(bookstoreowner);
      return bookstoreOwnerRepository.findById(bookstoreowner.getId()).isPresent() ? bookstoreowner : null;
    }
}


