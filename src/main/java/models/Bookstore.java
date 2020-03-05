public class Bookstore {
    private int id;
    private BookstoreOwner bookstoreOwner;


    public Bookstore(BookstoreOwner bookstoreOwner){
        this.bookstoreOwner = bookstoreOwner;
    }

    public BookstoreOwner getBookstoreOwner() {
        return this.bookstoreOwner;
    }

    public void setBookstoreOwner(BookstoreOwner bookstoreOwner) {
        this.bookstoreOwner = bookstoreOwner;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

}