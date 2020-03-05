public class Order {
    private int id;
    private Collection<Book> books;
    private User user;

    public Order(int id, Collection<Book> books, User user) {
        this.id = id;
        this.books = books;
        this.user = user;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}