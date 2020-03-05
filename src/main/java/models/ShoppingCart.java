package models;

public class ShoppingCart {
    private Collection<Book> books;
    private Customer customer;

    public ShoppingCart(Collection<Book> books, Customer customer){
        this.books = books;
        this.customer = customer;
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