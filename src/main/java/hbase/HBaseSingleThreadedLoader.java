package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import hbase.HBaseLoadTable;

public class HBaseSingleThreadedLoader {

	public static void main (String[] args) throws Exception {
		
		String searchString = args.length == 0 ? "gmail.com": args[0];
		Integer numberOfRows = Integer.parseInt(args.length == 0 ? "10000": args[1]);
		
		Configuration config = createHBaseConnection();
		
		//Create a HBaseAdmin object to manage tables
		HBaseAdmin admin = new HBaseAdmin(config);
		
		//call the table create/load
		HBaseLoadTable loadTable = new HBaseLoadTable(admin, config, numberOfRows);
		loadTable.createAndLoadTable();
		
		//call the table scan/filter
		HBaseSearchTable searchTable = new HBaseSearchTable(config, searchString);
		searchTable.searchTable();
		
		//call the table disable/delete
		HBaseDeleteTable deleteTable = new HBaseDeleteTable(admin);
		deleteTable.disableAndDeleteTable();
	}

	/*
	 * Put your hbase-site.xml file in the conf folder and it SHOULD get picked up automatically
	 * If your hbase-site.xml does not get picked up, then provide the zookeeper locations
	 */
	private static Configuration createHBaseConnection() throws Exception {
		Configuration config = HBaseConfiguration.create();

		config.set("hbase.zookeeper.quorum",
				"<quorum1>, <quorum2>, <quorum3>");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.cluster.distributed", "true");
		
		return config;
	}
	
}
