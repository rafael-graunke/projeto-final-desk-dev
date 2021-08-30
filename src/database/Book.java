package database;

public class Book {

    private String name, author, publisher;
    private long isbn;
    private int year;

    public Book(long isbn, String name, String author, int year, String publisher) {
        setIsbn(isbn);
        setName(name);
        setAuthor(author);
        setYear(year);
        setPublisher(publisher);
    }

    public Book() { }

    //Get-Set
    public long getIsbn() { return isbn; }
    public String getName() { return name; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getPublisher() { return publisher; }

    public void setIsbn(long isbn) { this.isbn = isbn; }
    public void setName(String name) { this.name = name; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setPublisher(String publisher) { this.publisher = publisher; }


}
