package models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import static javax.persistence.CascadeType.ALL;

@Entity
public class ShoppingCart {
    private Long id;
    private Set<Book> books;
    private Customer customer;

    public ShoppingCart(){ this.books = new HashSet<Book>();}

    @Id
    @GeneratedValue
    public Long getId(){ return this.id; }
    public void setId(Long id){ this.id = id; }

    @ManyToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "shoppingCarts")
    public Set<Book> getBooks() { return this.books; }
    public void setBooks(Set<Book> books) { this.books = books; }
    public void addBook(Book book){
        this.books.add(book);
        book.addShoppingCart(this);
    }
    private void removeBooks() {
        //The reason this does not call .clear() and changes the reference to a new Set is because of the
        //Hibernate layer, when a checkout occurs the books collection is moved to the Sale object,
        //if it is cleared here, it causes issues when Hibernate tries to persist.
        this.books = new HashSet<Book>();
    }

    @OneToOne(fetch = FetchType.EAGER, cascade=ALL)
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

            return sale;
        } else {
            return null;
        }
    }
}