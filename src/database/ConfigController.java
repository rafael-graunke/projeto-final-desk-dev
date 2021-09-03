package database;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Controller;
import resources.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController extends Controller implements Initializable {

    @FXML
    private TextField ip_field, name_field, port_field;
    @FXML
    private Button save_btn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void saveConnection() {
        ((DbController) main_controller).setIp(ip_field.getText());
        ((DbController) main_controller).setName(name_field.getText());

        Utility.closeWindow(save_btn);

        ((DbController) main_controller).show();
    }

    public void setFields(String ip, String port, String name) {
        ip_field.setText(ip);
        if (!port.equals("")) { port_field.setText(port); }
        name_field.setText(name);
    }
 }
