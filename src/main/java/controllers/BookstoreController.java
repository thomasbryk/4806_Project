package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.jpa.domain.Specification;

import models.Book;
import models.Bookstore;
import models.BookstoreSpec;
import models.Sale;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import repositories.BookstoreRepository;

@RestController
@RequestMapping("/api/bookstores")
public class BookstoreController {

    @Autowired
    BookstoreRepository bookstoreRepository;
    
    @GetMapping()
    public Iterable<Bookstore> getBookstores(BookstoreSpec spec){
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHH"+spec.toString());
        return bookstoreRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public Bookstore getBookstore(@PathVariable long id){
        return bookstoreRepository.findById(id);
    }

    @PostMapping()
    public Bookstore createBookstore(@RequestBody Bookstore bookstore){
        Bookstore b = bookstoreRepository.save(bookstore);
        return b;
    }

    @GetMapping("/{id}/books")
    public Iterable<Book> getBooksByBookstore(@PathVariable long id){
        Bookstore b = bookstoreRepository.findById(id);
        return b.getBooks();
    }
    
    @GetMapping("/{id}/sales")
    public Iterable<Sale> getSalesByBookstore(@PathVariable long id){
        Bookstore b = bookstoreRepository.findById(id);
        return b.getSales();
    }
}


