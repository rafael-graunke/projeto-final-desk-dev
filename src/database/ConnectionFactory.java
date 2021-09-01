package database;

import resources.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static String[] connectionData = new String[4];

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://"+ connectionData[0] +":3306/"+ connectionData[1] +
                                            "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT",
                                            connectionData[2], connectionData[3]);
    }

    public static void setConnectionData(String[] connection_data) {
        connectionData = connection_data;
    }

    public static void main(String[] args) {
        try {
            getConnection();
        } catch (SQLException e) {
            Utility.showError("Erro de conexão com o banco","Não foi possível estabelecer uma conexão com o banco de dados.");
        }
    }

}
