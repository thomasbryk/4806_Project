package models;

public class BookstoreOwner{

    private int id;
    private Bookstore bookstore;

    public BookstoreOwner(int id, Bookstore bookstore) {
        this.id = id;
        this.bookstore = bookstore;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bookstore getBookstore() {
        return this.bookstore;
    }

    public void setBookstore(Bookstore bookstore) {
        this.bookstore = bookstore;
    }
    
}