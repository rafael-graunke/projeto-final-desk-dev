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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbController extends Controller implements Initializable {

    @FXML
    private TextField ip_field, name_field, user_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Button cancel_btn;
    @FXML
    private ImageView image_field;

    private final Image connection_ok = new Image("/resources/connection_ok.png");
    private final Image connection_error = new Image("/resources/connection_error.png");
    private final Image connection_loading = new Image("/resources/connection_loading.gif");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean testConnection() {
        image_field.setImage(connection_loading);

        String[] connectionData = new String[] {ip_field.getText(), name_field.getText(), user_field.getText(), password_field.getText()};
        ConnectionFactory.setConnectionData(connectionData);

        try {
            ConnectionFactory.getConnection();
            image_field.setImage(connection_ok);
            return true;
        } catch (SQLException e) {
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
