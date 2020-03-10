package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class BookstoreOwner{
    private Long id;
    private String name;
    @JsonIgnore
    private Set<Bookstore> bookstores;

    public BookstoreOwner(){ this.bookstores = new HashSet<Bookstore>(); }
    public BookstoreOwner(String name){
        this.name = name;
        this.bookstores = new HashSet<Bookstore>();
    }

    @Id
    @GeneratedValue
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "bookstoreOwner")
    public Set<Bookstore> getBookstores() { return this.bookstores; }
    public void setBookstores(Set<Bookstore> bookstores) { this.bookstores = bookstores; }

    public Bookstore getBookstore(long bookstoreId){
        for (Bookstore bookstore: this.bookstores){
            if (bookstore.getId() == bookstoreId){
                return bookstore;
            }
        }
        return null;
    }

    public void addBookstore(Bookstore bookstore){
        bookstore.setBookstoreOwner(this);
        this.bookstores.add(bookstore);
    }

    public void removeBookstore(long bookstoreId){
        Bookstore bookstoreFound = null;
        for (Bookstore bookstore : this.bookstores){
            if (bookstore.getId() == bookstoreId){
                bookstoreFound = bookstore;
                break;
            }
        }
        if (bookstoreFound != null) {
            this.bookstores.remove(bookstoreFound);
            bookstoreFound.removeBookstoreOwner();
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}