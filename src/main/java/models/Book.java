package models;

public class Book{
    
    private String ISBN;


    private String picture;
    private String description;
    private String author;
    private String publisher;

    public Book(String ISBN, String picture, String description, String author, String publisher){
        this.ISBN = ISBN;
        this.picture = picture;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
    }

    
    public String getISBN() {
        return this.ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}