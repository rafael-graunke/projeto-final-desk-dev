package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoBook {

    public static List<Book> searchBooks() throws SQLException{
        List<Book> books = new ArrayList();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLConst.SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();

                book.setIsbn(rs.getLong("isbn"));
                book.setName(rs.getString("nome"));
                book.setAuthor(rs.getString("autor"));
                book.setYear(rs.getInt("ano"));
                book.setPublisher(rs.getString("editora"));
                books.add(book);
            }
        }
        return books;
    }

    public static List<Book> searchBooks(String value, String mode) throws SQLException{
        String query = "SELECT * FROM " + ConnectionFactory.getConnectionData()[1] + ".biblioteca WHERE " + mode + " = '" + value + "';";
        List<Book> books = new ArrayList();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();

                book.setIsbn(rs.getLong("isbn"));
                book.setName(rs.getString("nome"));
                book.setAuthor(rs.getString("autor"));
                book.setYear(rs.getInt("ano"));
                book.setPublisher(rs.getString("editora"));
                books.add(book);
            }
        }
        if (books.isEmpty()){
            return null;
        }
        return books;
    }

    public static void addBook(Book book) throws SQLException {
        String sql = SQLConst.INSERT;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setLong(1, book.getIsbn());
            stmt.setString(2, book.getName());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getYear());
            stmt.setString(5, book.getPublisher());
            stmt.execute();
        }
    }

    public static void removeBook(Book book) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLConst.DELETE)){
            stmt.setLong(1, book.getIsbn());
            stmt.execute();
        }
    }

    public static boolean checkDuplicate(Book book) throws SQLException {
        List<Book> books = searchBooks();
        for (Book b : books) {
            if (book.getIsbn() == b.getIsbn()){
                return true;
            }
        }
        return false;
    }
}
