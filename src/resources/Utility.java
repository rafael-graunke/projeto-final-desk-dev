package resources;

import database.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.Controller;
import java.io.IOException;

public class Utility {

    public static void showError(String header,String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String title, String header,String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();

        return alert.getResult().getText().equals("OK");
    }

    public static long toLong(String input) {
        return Long.parseLong(input);
    }

    public static int toInt(String input) {
        return Integer.parseInt(input);
    }

    public static Controller createNewWindow(String resource, String title, Controller controller, Controller main_controller, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(resource));
            Parent root = loader.load();
            controller = loader.getController();
            controller.setMainController(main_controller);

            Scene scene = new Scene(root, width, height);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.getIcons().add(new Image("resources/book_database.png"));
            stage.show();

        } catch (IOException e) {
            showError("Erro ao abrir janela", "Um erro correu ao tentar abrir uma nova janela.");
        }
        return controller;
    }

    public static void closeWindow(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
