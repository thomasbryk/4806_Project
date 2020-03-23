package models;

import static javax.persistence.CascadeType.ALL;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer extends BookstoreUser{
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private ShoppingCart shoppingCart;
    @JsonIgnore
    private List<Sale> sales;

    public Customer(){
        super("USER");
        this.shoppingCart = new ShoppingCart(this);
    }

    public Customer(String name, String address, String email, String phoneNumber) {
        super("USER");
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shoppingCart = new ShoppingCart(this);
    }

    public Customer(String username, String password, String name, String address, String email, String phoneNumber ){
        super(username, password, "USER");
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shoppingCart = new ShoppingCart(this);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToOne( cascade=ALL)
    public ShoppingCart getShoppingCart(){
        return this.shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    @OneToMany( cascade=ALL, mappedBy="customer")
    public List<Sale> getSales(){ return this.sales; }
    public void setSales(List<Sale> sales){
        this.sales = sales;
    }
    public void addSale(Sale sale){this.sales.add(sale);}
}