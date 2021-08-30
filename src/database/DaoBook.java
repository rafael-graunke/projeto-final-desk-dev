package database;

import resources.Utility;

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
             PreparedStatement stmt = connection.prepareStatement(SQLConst.SEARCH);
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

    //Pesquisa Strings
    public static List<Book> searchBook(String search, String mode) throws SQLException{
        List<Book> books = searchBooks();
        List<Book> foundBooks = new ArrayList<>();

        switch (mode) {
            case "ISBN":
                try {
                    for (Book b : books) {
                        if (b.getIsbn() == Utility.toLong(search))
                            foundBooks.add(b);
                    }
                } catch (NumberFormatException e) {
                    Utility.showError("Erro de pesquisa","O ISBN deve conter apenas números.");
                }
                break;

            case "Nome":
                for (Book b : books) {
                    if (b.getName().equals(search))
                        foundBooks.add(b);
                }
                break;

            case "Autor":
                for (Book b : books) {
                    if (b.getAuthor().equals(search))
                        foundBooks.add(b);
                }
                break;

            case "Ano":
                try {
                    for (Book b : books) {
                        if (b.getYear() == Utility.toInt(search))
                            foundBooks.add(b);
                    }
                } catch (NumberFormatException e) {
                    Utility.showError("Erro de pesquisa","O ano deve conter apenas números.");
                }
                break;

            default:
                for (Book b : books) {
                    if (b.getPublisher().equals(search))
                        foundBooks.add(b);
                }

        }

        if (foundBooks.isEmpty()) {
            return null;
        } else {
            return foundBooks;
        }
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
