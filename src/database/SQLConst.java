package database;

public class SQLConst {

    public static final String INSERT = "insert into " + ConnectionFactory.getConnectionData()[1] + ".biblioteca (isbn, nome, autor, ano, editora) values (?,?,?,?,?)";
    public static final String DELETE = "delete from "+ ConnectionFactory.getConnectionData()[1] +".biblioteca where isbn=?";
    public static final String SELECT_ALL = "select * from " + ConnectionFactory.getConnectionData()[1] + ".biblioteca";
    public static final String SEARCH = "select * from " + ConnectionFactory.getConnectionData()[1] + ".biblioteca WHERE teste = ?";

}
