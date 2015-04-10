
package phoenix;

import java.sql.Connection;
import java.sql.Statement;
import util.DataGenerator;
import util.Incrementor;

public class PhoenixTableLoader {
	Incrementor incrementor = Incrementor.getInstance();
	DataGenerator dataGenerator = DataGenerator.getInstance();
	
	public void buildCustomer(Connection con, Integer numberRows, String tableName) throws Exception {
		Statement stmt = con.createStatement();

		for (Integer i = 1; i <= numberRows ; i++) {
			String firstName = dataGenerator.getFirstName();
			String lastName = dataGenerator.getLastName();
			stmt.executeUpdate("upsert into " + tableName + " values (" + i.toString() + "," +
					"'" + firstName +"','" + lastName + "','" + dataGenerator.getGender() + "','" +
					dataGenerator.getDOB() + "'," + dataGenerator.getRandomCustomerValue() + ",'" + dataGenerator.getStreetAddress() + "','" +
					dataGenerator.getCity() + "','" + dataGenerator.getState() + "'," + dataGenerator.getZipCode() + ",'" +
					firstName.toLowerCase() + "." + lastName.toLowerCase() + dataGenerator.getEmailProvider() + "'" + ")");
			
			
			if (i%1000 == 0) {	
				System.out.println("Number of rows inserted is " + i);
				con.commit();
			}
		}
		con.commit();
	}
}
