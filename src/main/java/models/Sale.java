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

@Entity
public class Sale {
    private Long id;
    private List<Book> books;
    private Customer customer;
    private List<Bookstore> bookstores;

    public Sale() {
        this.bookstores = new ArrayList<>();
     }

    public Sale(List<Book> books, Customer customer) {
        this.books = books;
        this.customer = customer;
        this.bookstores = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    @OneToMany( cascade=ALL, mappedBy = "sale")
    public List<Book> getBooks() { return this.books; }
    public void setBooks(List<Book> books) { this.books = books; }

    @ManyToOne
    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    @ManyToMany( cascade=ALL, mappedBy = "sales")
    public List<Bookstore> getBookstores() { return this.bookstores; }
    public void setBookstores(List<Bookstore> bookstores) { this.bookstores = bookstores; }
    public void addBookstore(Bookstore bookstore) { this.bookstores.add(bookstore);}
}