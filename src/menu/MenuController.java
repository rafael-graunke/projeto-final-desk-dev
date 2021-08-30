package menu;

import add.AddController;
import database.DaoBook;
import database.Book;
import detail.DetailController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Controller;
import resources.Utility;
import search.SearchController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController extends Controller implements Initializable {

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Long> isbnColumn;
    @FXML
    private TableColumn<Book, String> nameColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Integer> yearColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;

    private final ObservableList<Book> bookData = FXCollections.observableArrayList();

    //Construtor
    public MenuController() throws SQLException {
        List<Book> books = DaoBook.searchBooks();
        bookData.addAll(books);
    }

    //Inicialização
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isbnColumn.setCellValueFactory(bookData -> new SimpleObjectProperty<>(bookData.getValue().getIsbn()));
        nameColumn.setCellValueFactory(bookData -> new SimpleStringProperty(bookData.getValue().getName()));
        authorColumn.setCellValueFactory(bookData -> new SimpleStringProperty(bookData.getValue().getAuthor()));
        yearColumn.setCellValueFactory(bookData -> new SimpleObjectProperty<>(bookData.getValue().getYear()));
        publisherColumn.setCellValueFactory(bookData -> new SimpleStringProperty(bookData.getValue().getPublisher()));

        bookTable.setItems(bookData);
    }

    public void addBookWindow() {
        Utility.createNewWindow("../add/add.fxml", "Adicionar um Livro",this, 690, 360);
    }

    public void searchBookWindow() {
        Utility.createNewWindow("../search/search.fxml", "Pesquisar um Livro", this,690, 420);
    }

    public void detailBookWindow() {
        if (getBookFromTable() != null) {
            Controller controller = Utility.createNewWindow("../detail/detail.fxml", "Detalhes do Livro", this, 690, 360);
            ((DetailController) controller).setSelectedBook(getBookFromTable());
            ((DetailController) controller).showDetails();
        } else {
            Utility.showError("Nenhum livro selecionado", "Você deve selecionar um livro da lista e então selecionar a opção de detalhes.");
        }
    }

    public void deleteBook() throws SQLException {

        boolean confirm = Utility.showConfirmation("Confirmar remoção", "Deseja excluir o livro selecionado?", "Após a exclusão não será possível recuperar o livro.");

        if (confirm) {
            Book book = getBookFromTable();
            if (book != null) {
                DaoBook.removeBook(book);
                removeBookFromTable(book);
                File file = new File("images/" + book.getIsbn() + ".jpg");
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    public void addBookToTable(Book book){
        bookData.add(book);
    }

    public void removeBookFromTable(Book book){
        Book bookOnTable = null;
        for (Book b : bookData) {
            if (b.getIsbn() == book.getIsbn()) {
                bookOnTable = b;
            }
        }
        bookData.remove(bookOnTable);
    }

    public Book getBookFromTable() {
        return bookTable.getSelectionModel().getSelectedItem();
    }
}
