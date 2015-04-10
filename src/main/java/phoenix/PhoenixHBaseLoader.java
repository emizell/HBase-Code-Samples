package phoenix;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PhoenixHBaseLoader {
	private static Connection con = null;
	private static String tableName = null;
	private static Integer numberOfRows = null;
	private static String connectionString = null;
	
	public static void main(String[] args) throws Exception {
		tableName = args.length == 0 ? "customer": args[0];
		numberOfRows = Integer.parseInt(args.length == 0 ? "2000000": args[1]);
		connectionString = args.length == 0 ? "<zookeeper location>:2181": args[2];
		
		createPhoenixConnection();
		createTable();
		
		PhoenixTableLoader loadTable = new PhoenixTableLoader();
		loadTable.buildCustomer(con, numberOfRows, tableName);
		
		closePhoenixConnection();
	}
	
	private static void createPhoenixConnection () throws Exception {
		con = DriverManager.getConnection("jdbc:phoenix:" + connectionString);	
	}
	
	private static void closePhoenixConnection () throws Exception {
		con.close();
	}
	
	private static void createTable() throws Exception {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("create table if not exists " + tableName + " (primaryKey integer not null primary key, firstName varchar, " +
				"lastName varchar, gender varchar, dob varchar, customerValue integer, streetAddress varchar, city varchar, state varchar, " +
				"zipCode integer, email varchar)");
		stmt.close();		
	}
	
}
