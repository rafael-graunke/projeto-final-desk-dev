package database;

public class SQLConst {

    public static final String INSERT = "insert into deskdev1.biblioteca (isbn, nome, autor, ano, editora) values (?,?,?,?,?)";
    public static final String DELETE = "delete from deskdev1.biblioteca where isbn=?";
    public static final String SELECT_ALL = "select * from deskdev1.biblioteca";
    public static final String SEARCH_ISBN = "select * from " + ConnectionFactory.getConnectionData()[1] + ".biblioteca WHERE ISBN = ?";
    public static final String SEARCH_NAME = "select * from " + ConnectionFactory.getConnectionData()[1] + ".biblioteca WHERE Nome = ?";
    public static final String SEARCH_AUTHOR = "select * from " + ConnectionFactory.getConnectionData()[1] + ".biblioteca WHERE Autor = ?";
    public static final String SEARCH_YEAR = "select * from " + ConnectionFactory.getConnectionData()[1] + ".biblioteca WHERE Ano = ?";
    public static final String SEARCH_PUBLISHER = "select * from " + ConnectionFactory.getConnectionData()[1] + ".biblioteca WHERE Editora = ?";

}
