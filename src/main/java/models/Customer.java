package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Customer{
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private ShoppingCart shoppingCart;
    private Set<Sale> sales;

    public Customer(){ }
    public Customer( String name, String address, String email, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Id
    @GeneratedValue
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

    @OneToOne(fetch = FetchType.EAGER, cascade=ALL)
    public ShoppingCart getShoppingCart(){
        return this.shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy="customer")
    public Set<Sale> getSales(){ return this.sales; }
    public void setSales(Set<Sale> sales){
        this.sales = sales;
    }
    public void addSale(Sale sale){this.sales.add(sale);}
}