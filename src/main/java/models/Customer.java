package models;

public class Customer{

    private int id;
    private String name;
    private String address;
    private String email;
    private String number;

    public Customer(int id, String name, String address, String email, String number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.number = number;
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

    

}