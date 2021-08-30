package database;

import resources.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/deskdev1?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT",
                    "root",
                    "");
            return con;
        } catch (SQLException e) {
            Utility.showError("Erro de conexão com o banco","Não foi possível estabelecer uma conexão com o banco de dados.");
        }
        return null;
    }

    public static void main(String[] args) {
        getConnection();
    }

}
