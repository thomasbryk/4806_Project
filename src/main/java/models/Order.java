package models;

public class Order {
    private int id;
    private Collection<Book> books;
    private Customer customer;

    public Order(int id, Collection<Book> books, Customer customer) {
        this.id = id;
        this.books = books;
        this.customer = customer;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    
}