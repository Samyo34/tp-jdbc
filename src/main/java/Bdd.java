import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class Bdd {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/tp-jdbc";

		
		try (Connection conn = DriverManager.getConnection(url, "postgres", "admin"); Statement stmt = conn.createStatement()) {
			BddManip.createTables(conn,stmt);
			
			Client c = new Client("Samuel","Bricas",Gender.M);
			Client c1 = new Client("Pierre","Brochand",Gender.M);
			Client c2 = new Client("Matine","Secu",Gender.F);
			Book b = new Book("Le petit cheval de manège","Just Leblanc");
			Book b2 = new Book("Les films du plat pays","Fançois Pignon");
			Book b3 = new Book("un livre","Truc Machin");
			Book b4 = new Book("Un autre livre","qsdfqsf qsdsd");
			BddManip.addClient(conn, c);
			BddManip.addClient(conn, c1);
			BddManip.addClient(conn, c2);
			BddManip.addBook(conn,b);
			BddManip.addBook(conn,b2);
			BddManip.addBook(conn,b3);
			BddManip.addBook(conn,b4);
			BddManip.achat(conn,c,b);
			BddManip.achat(conn,c,b2);
			BddManip.achat(conn,c1,b3);
			BddManip.achat(conn,c1,b);
			BddManip.achat(conn,c2,b4);
			
			BddManip.prefere(conn,c,b2);
			BddManip.prefere(conn,c1,b);
			BddManip.prefere(conn,c2,b4);
			System.out.println("Livre achat :");
			List<Book> achats = BddManip.selectLivreAchat(conn, c);
			for (Book book : achats) {
				System.out.println(book);
			}
			
			System.out.println("client achat :");
			List<Client> clients = BddManip.selectClientAchat(conn, b);
			for (Client client : clients) {
				System.out.println(client);
			}
			
			stmt.close();
			//stmt.executeUpdate("CREATE TABLE book VALUES('What''s New in Java 8', 'Adam L. Davis')");

		} catch (Exception e) {
			  System.out.println(e.getMessage());
		}


	}
	
	

}
