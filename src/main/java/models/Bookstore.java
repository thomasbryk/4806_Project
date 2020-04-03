package models;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Bookstore {
    private Long id;
    private String name;
    private BookstoreOwner bookstoreOwner;
    @JsonIgnore
    private List<Book> books;
    @JsonIgnore
    private List<Sale> sales;

    public Bookstore(){ this.books = new ArrayList<Book>();	}
    public Bookstore(String name) {
        this.name = name;
        this.books = new ArrayList<Book>();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    @ManyToOne
    public BookstoreOwner getBookstoreOwner() { return this.bookstoreOwner; }
    public void setBookstoreOwner(BookstoreOwner bookstoreOwner) { this.bookstoreOwner = bookstoreOwner; }
    public void removeBookstoreOwner() { this.bookstoreOwner = null; }

    @ManyToMany( cascade=ALL)
    public List<Sale> getSales() { return this.sales; }
    public void setSales(List<Sale> sales) { this.sales = sales; }
    public void addSale(Sale sale) { this.sales.add(sale); }
    public void removeSale(Sale sale) { this.sales.remove(sale); }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @OneToMany( cascade=ALL, mappedBy = "bookstore")
    public List<Book> getBooks(){ return this.books; }
    public void setBooks(List<Book> books){ this.books = books; }

    public void addBook(Book book){
        book.setBookstore(this);
        this.books.add(book);
    }

    public void removeBookById(long bookId){
        Book bookFound = null;
        for (Book book : this.books){
            if (book.getId() == bookId){
                bookFound = book;
                break;
            }
        }
        if (bookFound != null) {
            if (bookFound.getAvailable()) {
                this.books.remove(bookFound);
                bookFound.removeBookstore();
            }
        }
    }
}