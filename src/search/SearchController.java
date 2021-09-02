package search;

import database.DaoBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    //Arrays to iterate later on showBook()
    private TextField[] text_fields_reset;
    private Node[] field_reset = new Node[8];

    private List<Book> foundBooks;
    private int currentPosition = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        text_fields_reset = new TextField[]{isbn_field, name_field, author_field, year_field, publisher_field};
        field_reset = new Node[] {isbn_field, name_field, author_field, year_field, publisher_field, delete_btn, previous_btn, next_btn};

        search_options = FXCollections.observableArrayList();
        search_options.addAll("ISBN", "Nome", "Autor", "Ano", "Editora");
        search_choice.setItems(search_options);
        search_choice.getSelectionModel().select(0);

        resetConfigs();
    }

    public void searchBook() throws SQLException {
        Book book;
        String choice = search_choice.getSelectionModel().getSelectedItem();

        try {
            List<Book> checkIfNotNull = DaoBook.searchBooks(search_field.getText(), choice);
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
            Book book = DaoBook.searchBooks(isbn_field.getText(), "ISBN").get(0);
            if (book != null) {
                main_controller.removeBookFromTable(book);
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
            for (Node nd : field_reset) { nd.setDisable(false); }

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
        for (Node nd : field_reset) { nd.setDisable(true); }

        for (TextField tf : text_fields_reset) {
            tf.setEditable(false);
            tf.setText("");
        }
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
