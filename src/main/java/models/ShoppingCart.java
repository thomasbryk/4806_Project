package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import static javax.persistence.CascadeType.ALL;

@Entity
public class ShoppingCart {
    private Long id;
    private Set<Book> books;
    @JsonIgnore
    private Customer customer;

    public ShoppingCart(){ }

    @Id
    @GeneratedValue
    public Long getId(){ return this.id; }
    public void setId(Long id){ this.id = id; }

    @ManyToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "shoppingCarts")
    public Set<Book> getBooks() { return this.books; }
    public void setBooks(Set<Book> books) { this.books = books; }
    public void addBook(Book book){ this.books.add(book); }
    public void removeBooks() { this.books = null; }

    @OneToOne(fetch = FetchType.EAGER, cascade=ALL)
    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Sale checkout() {
        Sale sale = new Sale(this.books, this.customer);
        for (Book book : this.books){
            book.setAvailable(false);
            sale.addBookstore(book.getBookstore());
            book.removeShoppingCart();
            book.setSale(sale);
        }
        this.removeBooks();

        return sale;
    }
}