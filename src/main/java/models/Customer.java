package models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer{

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String address;
    private String email;
    private String number;
    @OneToOne
    private ShoppingCart shoppingCart;
    private List<Order> orders;

    public Customer(){ }

    public Customer(int id, String name, String address, String email, String number, ShoppingCart shoppingCart, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.number = number;
        this.shoppingCart = shoppingCart;
        this.orders = orders;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
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

    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public ShoppingCart getShoppingCart(){
        return this.shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    @OneToMany
    public List<Order> getOrders(){ return this.orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
    public void addOrder(Order order){ this.orders.add(order); }

}