package main;

import database.DbController;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.Utility;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Controller controller = Utility.createNewWindow("../database/db.fxml", "Conexão com Banco", new Controller(), 300, 286);
        ((DbController) controller).readConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
