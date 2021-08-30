package database;

public class SQLConst {

    public static final String INSERT = "insert into meubanco.livro (isbn, nome, autor, ano, editora) values (?,?,?,?,?)";
    public static final String UPDATE = "update meubanco.livro set nome=?, autor=?, ano=?, editora=? where isbn=?";
    public static final String DELETE = "delete from meubanco.livro where isbn=?";
    public static final String SEARCH = "select * from meubanco.livro";
    public static final String SEARCH_SPECIFIC = "select * from meubanco.livro where isbn;?";

}
