package models;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class ShoppingCart {
    private Long id;
    private List<Book> books;
    private Customer customer;

    public ShoppingCart(){ this.books = new ArrayList<Book>();}
    public ShoppingCart(Customer customer){
        this.customer = customer;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId(){ return this.id; }
    public void setId(Long id){ this.id = id; }

    @ManyToMany( cascade=ALL, mappedBy = "shoppingCarts")
    public List<Book> getBooks() { return this.books; }
    public void setBooks(List<Book> books) { this.books = books; }
    public void addBook(Book book){
        this.books.add(book);
    }
    private void removeBooks() {
        //The reason this does not call .clear() and changes the reference to a new Set is because of the
        //Hibernate layer, when a checkout occurs the books collection is moved to the Sale object,
        //if it is cleared here, it causes issues when Hibernate tries to persist.
        this.books = new ArrayList<Book>();
    }

    @OneToOne
    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Sale checkout() {
        if (!this.books.isEmpty()) {
            Sale sale = new Sale(this.books, this.customer);
            for (Book book : this.books) {
                book.setAvailable(false);
                sale.addBookstore(book.getBookstore());
                book.removeShoppingCarts();
                book.setSale(sale);
            }
            this.removeBooks();
            this.customer.addSale(sale);
            return sale;
        } else {
            return null;
        }
    }
}