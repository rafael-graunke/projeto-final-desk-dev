package add;

import database.DaoBook;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.Book;
import main.Controller;
import menu.MenuController;
import resources.Utility;

public class AddController extends Controller implements Initializable {

    @FXML
    private ImageView image_field;
    private Image image;

    @FXML
    private Button back_btn;

    @FXML
    private TextField isbn_field, name_field, author_field, year_field, publisher_field;

    private File chosenFile, newFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image = new Image("resources/default.png");
        image_field.setImage(image);

    }

    public void closeWindow() {
        Utility.closeWindow(back_btn);
    }

    public void imagePicker() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha a capa do Livro");
        chosenFile = fileChooser.showOpenDialog(image_field.getScene().getWindow());
        if (chosenFile != null) {
            image_field.setImage(new Image(chosenFile.toURI().toString()));
        }
    }

    public void saveChanges() throws SQLException {
        newFile = new File("images/" + isbn_field.getText() + ".jpg");
        if (chosenFile != null) {
            try {
                Files.copy(chosenFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Utility.showError("Erro no salvamento do arquivo","Não foi possível salvar a imagem selecionada.");
            }
        }

        if (isbn_field.getText().equals("") || name_field.getText().equals("") ||
                author_field.getText().equals("") || year_field.getText().equals("") || publisher_field.getText().equals("")) {

            Utility.showError("Erro ao cadastrar livro","Algum campo não foi preenchido.");

        } else { //Caso todos os campos estejam preenchidos:

            try {
                long isbn = Utility.toLong(isbn_field.getText());
                String name = name_field.getText();
                String author = author_field.getText();
                int year = Utility.toInt(year_field.getText());
                String publisher = publisher_field.getText();

                Book book = new Book(isbn, name, author, year, publisher);

                if (DaoBook.checkDuplicate(book)) { //Verifica se o ISBN informado já não está cadastrado
                    Utility.showError("Erro ao cadastrar livro","O ISBN informado já está registrado.");

                } else { //Adiciona um livro não repetido
                    DaoBook.addBook(book);
                    ((MenuController) main_controller).addBookToTable(book);
                    Utility.closeWindow(back_btn);
                }
            } catch (NumberFormatException e) {
                Utility.showError("Erro ao cadastrar livro","Algum valor informado não condiz com o formato necessário.\n Verifique os valores informados.");
            }
        }
    }
}
