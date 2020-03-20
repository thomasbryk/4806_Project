package models;

import static javax.persistence.CascadeType.ALL;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BookstoreOwner extends BookstoreUser{
    private String name;
    @JsonIgnore
    private Set<Bookstore> bookstores;

    public BookstoreOwner(){
        super("ADMIN");
        this.bookstores = new HashSet<Bookstore>(); 
    }
    public BookstoreOwner(String name){
        super();
        this.name = name;
        this.bookstores = new HashSet<Bookstore>();
    }

    public BookstoreOwner(String username, String password, String name){
        super(username, password, "ADMIN");
        this.name = name;
        this.bookstores = new HashSet<Bookstore>();
    }



    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "bookstoreOwner")
    public Set<Bookstore> getBookstores() { return this.bookstores; }
    public void setBookstores(Set<Bookstore> bookstores) { this.bookstores = bookstores; }

    public Bookstore getBookstoreById(long bookstoreId){
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

    public void removeBookstoreById(long bookstoreId){
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