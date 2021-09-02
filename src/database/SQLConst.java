package database;

public class SQLConst {

    public static final String INSERT = "insert into deskdev1.biblioteca (isbn, nome, autor, ano, editora) values (?,?,?,?,?)";
    public static final String DELETE = "delete from deskdev1.biblioteca where isbn=?";
    public static final String SELECT_ALL = "select * from deskdev1.biblioteca";

}
