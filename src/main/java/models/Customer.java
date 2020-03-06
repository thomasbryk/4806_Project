package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Customer{

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String address;
    private String email;
    private String number;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id",referencedColumnName = "id")
    private ShoppingCart shoppingCart;

    public Customer(int id, String name, String address, String email, String number, ShoppingCart shoppingCart) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.number = number;
        this.shoppingCart = shoppingCart;
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

}