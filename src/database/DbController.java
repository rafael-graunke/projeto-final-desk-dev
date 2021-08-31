package database;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Controller;
import resources.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class DbController extends Controller implements Initializable {

    @FXML
    private TextField ip_field, name_field, user_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Button test_btn, cancel_btn, save_btn;
    @FXML
    private ImageView image_field;

    private final Image connection_ok = new Image("/resources/connection_ok.png");
    private final Image connection_error = new Image("/resources/connection_error.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean testConnection() {
        ConnectionFactory.setIp(ip_field.getText());
        ConnectionFactory.setDb_name(name_field.getText());
        ConnectionFactory.setUser(user_field.getText());
        ConnectionFactory.setPassword(password_field.getText());

        if (ConnectionFactory.testConnection()) {
            image_field.setImage(connection_ok);
            return true;
        } else {
            image_field.setImage(connection_error);
        }
        return false;
    }

    public void saveConnection() {
        if(testConnection()) {
            //TODO: Salvar os dados de conexão ao banco em um arquivo
            Utility.closeWindow(cancel_btn);
            Utility.createNewWindow("../menu/menu.fxml", "Banco de Livros", this, 805, 420);
        } else {
            Utility.showError("Erro de conexão ao banco de dados", "Verifique os dados informado para a conexão.");
        }
    }

    public void cancel() {
        Utility.closeWindow(cancel_btn);
    }
}
