package detail;

import database.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Controller;
import resources.Utility;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailController extends Controller implements Initializable {

    @FXML
    private ImageView image_field;

    @FXML
    private TextField isbn_field, name_field, author_field, year_field, publisher_field;

    @FXML
    private Button back_btn;

    private Book selectedBook;
    private TextField[] text_field_reset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text_field_reset = new TextField[] {isbn_field, name_field, author_field, year_field, publisher_field};
        for (TextField tf : text_field_reset) {tf.setEditable(false);}
        }

    public void showDetails() {
        isbn_field.setText(Long.toString(selectedBook.getIsbn()));
        name_field.setText(selectedBook.getName());
        author_field.setText(selectedBook.getAuthor());
        year_field.setText(Integer.toString(selectedBook.getYear()));
        publisher_field.setText(selectedBook.getPublisher());

        File file = new File("images/" + selectedBook.getIsbn() + ".jpg");

        if (file.exists()) {
            image_field.setImage(new Image(file.toURI().toString()));
        } else {
            image_field.setImage(new Image("resources/missing.png"));
        }
    }

    public void closeWindow() {
        Utility.closeWindow(back_btn);
    }

    public void setSelectedBook(Book book) {
        selectedBook = book;
    }
}
