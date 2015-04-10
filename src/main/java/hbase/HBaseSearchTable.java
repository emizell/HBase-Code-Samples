package hbase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseSearchTable {
	
	private String stringFilter;
	private Configuration config;
	
	public HBaseSearchTable(Configuration hbaseConfig, String searchString) {
		this.stringFilter = searchString;
		this.config = hbaseConfig;
	}

	public void searchTable() throws Exception {
	
		// Open the table
		HTable table = new HTable(config, "contacts");

		// Define the family and qualifiers to be used
		byte[] contactFamily = Bytes.toBytes("contactinfo");
		byte[] emailQualifier = Bytes.toBytes("email");
		byte[] nameFamily = Bytes.toBytes("name");
		byte[] firstNameQualifier = Bytes.toBytes("first");
		byte[] lastNameQualifier = Bytes.toBytes("last");

		// Create a new regex filter
		RegexStringComparator emailFilter = new RegexStringComparator(stringFilter);
		// Attach the regex filter to a filter
		//   for the email column
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				contactFamily,
				emailQualifier,
				CompareOp.EQUAL,
				emailFilter);

		// Create a scan and set the filter
		Scan scan = new Scan();
		scan.setFilter(filter);

		// Get the results
		ResultScanner results = table.getScanner(scan);
		// Iterate over results and print  values
		System.out.println("Scanned table for emails containing " + stringFilter + " and found " + results.iterator().next().size() + " results");
		for (Result result : results ) {
			String id = new String(result.getRow());
			String firstName = new String(result.getValue(nameFamily, firstNameQualifier));
			String lastName = new String(result.getValue(nameFamily, lastNameQualifier));
			String email = new String(result.getValue(contactFamily, emailQualifier));
			System.out.println("RowKey: " + id + " - " + firstName + " " + lastName + " - " + email);
		}
		results.close();
		table.close();
	}
}
