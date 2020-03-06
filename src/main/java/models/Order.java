package models;

import javax.persistence.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private List<Book> books;
    private Customer customer;
    private Set<Bookstore> bookstores;

    public Order(List<Book> books, Customer customer) {
        this.books = books;
        this.customer = customer;
        this.bookstores = Collections.emptySet();
    }


    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    @OneToMany
    public List<Book> getBooks() { return this.books; }
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @ManyToOne
    public Customer getCustomer() {
        return this.customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "orders")
    public Set<Bookstore> getBookstores() { return this.bookstores; }
    public void setBookstores(Set<Bookstore> bookstores){ this.bookstores = bookstores; }
    public void addBookstore(Bookstore bookstore){ this.bookstores.add(bookstore); }
}