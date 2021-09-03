package database;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.Controller;
import resources.Utility;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbController extends Controller implements Initializable {

    @FXML
    private TextField user_field, password_field;
    @FXML
    private Button cancel_btn, save_btn, config_btn;
    @FXML
    private ImageView image_field;

    private String ip = "", name = "", port = "";

    private final Image connection_ok = new Image("/resources/connection_ok.png");
    private final Image connection_error = new Image("/resources/connection_error.png");
    private final Image connection_loading = new Image("/resources/connection_loading.gif");
    private final Image config = new Image("/resources/db_config.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        save_btn.setDisable(true);
        ImageView config_db = new ImageView(config);
        config_db.setFitWidth(30);
        config_db.setFitHeight(30);
        config_btn.setGraphic(config_db);
    }

    public void testConnection() {
        save_btn.setDisable(true);
        image_field.setImage(connection_loading);

       String[] connectionData = new String[] {ip, name, user_field.getText(), password_field.getText()};
       ConnectionFactory.setConnectionData(connectionData);

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() {
                try {
                    ConnectionFactory.getConnection();
                    image_field.setImage(connection_ok);
                    save_btn.setDisable(false);
                    return true;
                } catch (SQLException e) {
                    image_field.setImage(connection_error);
                }
                return false;
            }
        };

        Thread th = new Thread(task);
        th.start();
    }

    public void configConnection() {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.hide();
        Controller controller = Utility.createNewWindow("../database/db_config.fxml", "Config. DB", this, 290, 235);
        ((ConfigController) controller).setFields(ip, port, name);
    }

    public void saveConnection() {
        //TODO: Salvar os dados de conexão ao banco em um arquivo
        Utility.closeWindow(cancel_btn);
        Utility.createNewWindow("../menu/menu.fxml", "Banco de Livros", this, 805, 420);
    }

    public void setIp(String ip) {this.ip = ip;}
    public void setName(String name) {this.name = name;}
    public void setPort(String port) {this.port = port;}

    public void show() {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.show();
    }

    public void cancel() {
        Utility.closeWindow(cancel_btn);
    }
}
