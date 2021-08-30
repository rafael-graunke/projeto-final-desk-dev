package search;

import database.DaoBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import database.Book;

import main.Controller;
import menu.MenuController;
import resources.Utility;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController extends Controller implements Initializable {

    @FXML
    private ChoiceBox<String> search_choice;
    private ObservableList<String> search_options;

    @FXML
    private Button delete_btn, back_btn, previous_btn, next_btn;

    @FXML
    private TextField isbn_field, name_field, author_field, year_field, publisher_field, search_field;

    @FXML
    private ImageView image_field;

    private List<Book> foundBooks;
    private int currentPosition = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Cria Opções ChoiceBox
        search_options = FXCollections.observableArrayList();
        search_options.add("ISBN");
        search_options.add("Nome");
        search_options.add("Autor");
        search_options.add("Ano");
        search_options.add("Editora");
        search_choice.setItems(search_options);
        search_choice.getSelectionModel().select(0);

        resetConfigs();
    }

    public void searchBook() throws SQLException {
        Book book;
        String choice = search_choice.getSelectionModel().getSelectedItem();

        try {
            List<Book> checkIfNotNull = DaoBook.searchBook(search_field.getText(), choice);
            if (checkIfNotNull != null) {
                currentPosition = 0;
                foundBooks = checkIfNotNull;
            } else {
                throw new NullPointerException();
            }
            book = foundBooks.get(currentPosition);
            showBook(book);

            if (foundBooks.size() > 1) {
                previous_btn.setDisable(false);
                next_btn.setDisable(false);
            } else {
                previous_btn.setDisable(true);
                next_btn.setDisable(true);
            }

        } catch (NullPointerException e) {
            Utility.showError("Não foi possível encontrar nenhum livro com o dado informado.","\nDado: " + choice + "\nValor: " + search_field.getText());
        }
    }

    public void deleteBook() throws SQLException {
        boolean confirm = Utility.showConfirmation("Confirmar Remoção", "Deseja excluir o livro selecionado?", "Após a exclusão não será possível recuperar o livro.");

        if (confirm) {
            Book book = DaoBook.searchBook(isbn_field.getText(), "ISBN").get(0);
            if (book != null) {
                ((MenuController) main_controller).removeBookFromTable(book);
                DaoBook.removeBook(book);
                File file = new File("images/" + book.getIsbn() + ".jpg");
                if (file.exists()) {
                    file.delete();
                }
            }
            resetConfigs();
        }
    }

    // Exibir o livro encontrado
    public void showBook(Book book) {
        if (book != null) {
            delete_btn.setDisable(false);
            isbn_field.setDisable(false);
            name_field.setDisable(false);
            author_field.setDisable(false);
            year_field.setDisable(false);
            publisher_field.setDisable(false);
            image_field.setDisable(false);

            File file = new File("images/" + book.getIsbn() + ".jpg");

            isbn_field.setText(Long.toString(book.getIsbn()));
            name_field.setText(book.getName());
            author_field.setText(book.getAuthor());
            year_field.setText(Integer.toString(book.getYear()));
            publisher_field.setText(book.getPublisher());

            if (file.exists()) {
                image_field.setImage(new Image(file.toURI().toString()));
            } else {
                image_field.setImage(new Image("resources/missing.png"));
            }
        }
    }

    // Método para percorrer a lista de livros encontrados
    public void cycleBooks(ActionEvent event) {
        if (event.getSource() == previous_btn) {
            if (currentPosition == 0) {
                currentPosition = foundBooks.size() - 1;
            } else {
                currentPosition --;
            }
        } else {
            if (currentPosition == foundBooks.size() - 1) {
                currentPosition = 0;
            } else {
                currentPosition ++;
            }
        }

        showBook(foundBooks.get(currentPosition));
    }

    public void resetConfigs() {

        //Desabilita Botões
        delete_btn.setDisable(true);
        previous_btn.setDisable(true);
        next_btn.setDisable(true);

        //Desabilita Interação com TextField
        isbn_field.setDisable(true);
        isbn_field.setEditable(false);
        isbn_field.setText("");
        name_field.setDisable(true);
        name_field.setEditable(false);
        name_field.setText("");
        author_field.setDisable(true);
        author_field.setEditable(false);
        author_field.setText("");
        year_field.setDisable(true);
        year_field.setEditable(false);
        year_field.setText("");
        publisher_field.setDisable(true);
        publisher_field.setEditable(false);
        publisher_field.setText("");
        search_field.setText("");

        //Seta Imagem ImageView
        Image image = new Image("resources/missing.png");
        image_field.setImage(image);
        image_field.setDisable(true);
    }

    public void closeWindow() {
        Utility.closeWindow(back_btn);
    }

}
