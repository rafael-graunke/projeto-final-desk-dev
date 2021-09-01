package database;

import resources.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static String ip, db_name, user, password;

    public static Connection getConnection() throws SQLException{

        return DriverManager.getConnection("jdbc:mysql://"+ ip +":3306/"+ db_name + "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT",
                user,
                password);
    }
    
    public static void setIp(String ip) {
        ConnectionFactory.ip = ip;
    }

    public static void setDb_name(String db_name) {
        ConnectionFactory.db_name = db_name;
    }

    public static void setUser(String user) {
        ConnectionFactory.user = user;
    }

    public static void setPassword(String password) {
        ConnectionFactory.password = password;
    }

    public static void main(String[] args) {
        try {
            getConnection();
        } catch (SQLException e) {
            Utility.showError("Erro de conexão com o banco","Não foi possível estabelecer uma conexão com o banco de dados.");
        }
    }

}
