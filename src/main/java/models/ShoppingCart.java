package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ShoppingCart {
    
    
    @Id
    private int id;
    @OneToMany(fetch=FetchType.EAGER, targetEntity = Book.class)
    private List<Book> books;
    @OneToOne(mappedBy = "shoppingCart")
    private Customer customer;

    public ShoppingCart(List<Book> books, Customer customer){
        this.books = books;
        this.customer = customer;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }


    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}