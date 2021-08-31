package main;

import javafx.application.Application;
import javafx.stage.Stage;
import resources.Utility;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Utility.createNewWindow("../menu/menu.fxml", "Banco de Livros", new Controller(), 805, 420);
        Utility.createNewWindow("../database/db.fxml", "Conexão com Banco", new Controller(), 300, 400);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
