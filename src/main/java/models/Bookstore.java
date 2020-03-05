package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;

@Entity
public class Bookstore {
    private Long id;
    private String name;
    @JsonIgnore
    private BookstoreOwner bookstoreOwner;
    private List<Book> books;

    public Bookstore(){ this.books = new ArrayList<Book>();	}

    /*public Bookstore(BookstoreOwner bookstoreOwner){
        this.bookstoreOwner = bookstoreOwner;
    }*/

    @ManyToOne
    public BookstoreOwner getBookstoreOwner() { return this.bookstoreOwner; }
    public void setBookstoreOwner(BookstoreOwner bookstoreOwner) { this.bookstoreOwner = bookstoreOwner; }
    public void removeBookstoreOwner() { this.bookstoreOwner = null; }

    @Id
    @GeneratedValue
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "bookstore")
    public List<Book> getBooks(){ return this.books; }
    public void setBooks(List<Book> books){ this.books = books; }

    public void addBook(Book book){
        book.setBookstore(this);
        this.books.add(book);
    }

    public void removeBook(long bookId){
        Book bookFound = null;
        for (Book book : this.books){
            if (book.getId() == bookId){
                bookFound = book;
                break;
            }
        }
        if (bookFound != null) {
            this.books.remove(bookFound);
            bookFound.removeBookstore();
        }
    }
}