import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BddManip {
	
	public static void addBook(Connection conn, Book book){
		String sql = "INSERT INTO book(title,author) VALUES (?,?);";
		try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setString(1, book.getTitle());
			pst.setString(2, book.getAuthor());
			pst.executeUpdate();
			ResultSet generatedKeys = pst.getGeneratedKeys();
			generatedKeys.next();
			book.setId(generatedKeys.getLong("id"));
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 System.out.println(e.getMessage());
		}
	}
	
	
	public static void addClient(Connection conn, Client client){
		String sql = "INSERT INTO client(lastName,firstName,gender) VALUES (?,?,?);";
		try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setString(1, client.getLastName());
			pst.setString(2, client.getFirstName());
			pst.setString(3, client.getGender().getName());
			pst.executeUpdate();
			ResultSet generatedKeys = pst.getGeneratedKeys();
			generatedKeys.next();
			client.setId(generatedKeys.getLong("id"));
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 System.out.println(e.getMessage());
		}
	}
	
	public static void achat(Connection conn, Client client,Book book) throws SQLException
	{
		PreparedStatement pst = conn.prepareStatement("INSERT INTO achat(client_id,book_id) VALUES (?,?);");
		pst.setLong(1, client.getId());
		pst.setLong(2, book.getId());
		pst.executeUpdate();
		pst.close();
	}
	
	public static void prefere(Connection conn, Client client,Book book) throws SQLException
	{
		PreparedStatement pst = conn.prepareStatement("UPDATE client SET prefere=? WHERE id=?;");
		pst.setLong(1, book.getId());
		pst.setLong(2, client.getId());
		pst.executeUpdate();
		pst.close();
	}
	
	public static List<Book> selectLivreAchat(Connection conn,Client client) throws SQLException{
		List<Book> books= new ArrayList<Book>();
		PreparedStatement pst = conn.prepareStatement("SELECT book.* FROM achat JOIN book ON achat.book_id = book.id WHERE "+
		"achat.client_id=?;");
		pst.setLong(1, client.getId());
		ResultSet res = pst.executeQuery();
		while(res.next()){
			books.add(new Book(res.getLong("id"),res.getString("title"),res.getString("author")));
		}
			
		pst.close();
		
		return books;
	}
	
	public static List<Client> selectClientAchat(Connection conn,Book book) throws SQLException{
		List<Client> clients = new ArrayList<Client>();
		PreparedStatement pst = conn.prepareStatement("SELECT client.* FROM achat JOIN client ON achat.client_id = client.id WHERE "+
		"achat.book_id=?;");
		pst.setLong(1, book.getId());
		ResultSet res = pst.executeQuery();
		while(res.next()){
			Client c = new Client(res.getLong(1),
					res.getString(2),
					res.getString(3),
					Gender.valueOf(res.getString(4))
					);
			clients.add(c);
		}
		
		pst.close();
		
		return clients;
	}
	
	public static void createTables(Connection conn, Statement stmt) throws SQLException
	{
		stmt.addBatch("DROP TABLE achat;");
		stmt.addBatch("DROP TABLE client;");
		stmt.addBatch("DROP TABLE book;");

		// table book
		stmt.addBatch("CREATE TABLE book(id serial,title character varying(255),author character varying(255),"+
		"CONSTRAINT pk_book PRIMARY KEY (id));");
		
		//table Client
		stmt.addBatch("CREATE TABLE client(id serial, lastName character varying(255),firstName character varying(255), gender character varying(255),prefere integer,"+
		"CONSTRAINT pk_client PRIMARY KEY (id),"+
		"CONSTRAINT fk_book FOREIGN KEY (prefere) REFERENCES public.book (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION);");

		//table achat
		stmt.addBatch("CREATE TABLE achat(id serial, client_id integer,book_id integer,CONSTRAINT pk_achat PRIMARY KEY (id),"+
		"CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES public.client (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,"+
		"CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES public.book (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION);");
		
		stmt.executeBatch();
	}
	
}
