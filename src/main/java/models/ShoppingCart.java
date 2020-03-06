package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import static javax.persistence.CascadeType.ALL;

@Entity
public class ShoppingCart {
    private Long id;
    @JsonIgnore
    private List<Book> books;
    @JsonIgnore
    private Customer customer;

    public ShoppingCart(){ }

    @Id
    @GeneratedValue
    public Long getId(){ return this.id; }
    public void setId(Long id){ this.id = id; }

    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "shoppingCart")
    public List<Book> getBooks() { return this.books; }
    public void setBooks(List<Book> books) { this.books = books; }

    @OneToOne(fetch = FetchType.EAGER, cascade=ALL)
    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

}