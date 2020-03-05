public class ShoppingCart {
    private Collection<Book> books;
    private User user;

    public ShoppingCart(Collection<Book> books, User user){
        this.books = books;
        this.user = user;
    }



    public Collection<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}