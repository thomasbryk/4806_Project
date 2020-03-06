package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Sale {
    private Long id;

    @JsonIgnore
    private Set<Book> books;
    @JsonIgnore
    private Customer customer;
    @JsonIgnore
    private Set<Bookstore> bookstores;

    public Sale() { }

    @Id
    @GeneratedValue
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "sale")
    public Set<Book> getBooks() { return this.books; }
    public void setBooks(Set<Book> books) { this.books = books; }

    @ManyToOne
    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    @ManyToMany
    public Set<Bookstore> getBookstores() { return this.bookstores; }
    public void setBookstores(Set<Bookstore> bookstores) { this.bookstores = bookstores; }
    public void addBookstore(Bookstore bookstore) {this.bookstores.add(bookstore);}
}