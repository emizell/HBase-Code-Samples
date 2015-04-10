package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import util.DataGenerator;

public class HBaseLoadTable {
	private HBaseAdmin admin;
	private Configuration config;
	private Integer contactSize;
	//get instance of DataGenerator
	private DataGenerator dataGenerator;
	
	public HBaseLoadTable(HBaseAdmin hbaseAdmin, Configuration hbaseConfig, Integer numberOfContacts) {
		this.admin = hbaseAdmin;
		this.config = hbaseConfig;
		this.contactSize = numberOfContacts;
		dataGenerator = DataGenerator.getInstance();
	}

	public void createAndLoadTable () throws Exception {

		// create the table...
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("contacts"));
		// ... with two column families
		tableDescriptor.addFamily(new HColumnDescriptor("name"));
		tableDescriptor.addFamily(new HColumnDescriptor("contactinfo"));
		admin.createTable(tableDescriptor);

		HTable table = new HTable(config, "contacts");

		// Add each person to the table
		//   Use the `name` column family for the name
		//   Use the `contactinfo` column family for the email
		System.out.println("Beginning table load " + contactSize + " records");
		for (Integer i = 0; i< contactSize; i++) {
			Put person = new Put(Bytes.toBytes(i.toString()));
			
			String firstName = dataGenerator.getFirstName();
			person.add(Bytes.toBytes("name"), Bytes.toBytes("first"), Bytes.toBytes(firstName));
			
			String lastName = dataGenerator.getLastName();
			person.add(Bytes.toBytes("name"), Bytes.toBytes("last"), Bytes.toBytes(lastName));
			
			String emailAddress = generateEmailAddress(firstName, lastName);
			person.add(Bytes.toBytes("contactinfo"), Bytes.toBytes("email"), Bytes.toBytes(emailAddress));
			
			table.put(person);
			
			if (i%1000 == 0) {
				System.out.println("Number of rows inserted is " + i);
			} 
		}

		// flush commits and close the table
		table.flushCommits();
		table.close();
		System.out.println("Table load complete");
	}
	
	private String generateEmailAddress (String firstName, String lastName) throws Exception {

		String emailAddress = firstName.toLowerCase() + "." + lastName.toLowerCase() + dataGenerator.getEmailProvider();
		return emailAddress;
		
	}
}