//package models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static javax.persistence.CascadeType.ALL;
//
//@Entity
//public class Order {
//    private Long id;
//
//    @JsonIgnore
//    private List<Book> books;
//    @JsonIgnore
//    private Customer customer;
//
//    public Order() { }
//
//    @Id
//    @GeneratedValue
//    public Long getId() { return this.id; }
//    public void setId(Long id) { this.id = id; }
//
//    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "bookstore")
//    public List<Book> getBooks() { return this.books; }
//    public void setBooks(List<Book> books) { this.books = books; }
//
//    public Customer getCustomer() { return this.customer; }
//    public void setCustomer(Customer customer) { this.customer = customer; }
//}