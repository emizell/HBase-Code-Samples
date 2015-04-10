package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseEngine {

	public static void main (String[] args) throws Exception {
		
		Integer numberOfRows = Integer.parseInt(args.length == 0 ? "100000": args[0]);
		Integer numberOfThreads = Integer.parseInt(args.length == 0 ? "5": args[1]);
		String mode = args.length == 0 ? "multi": args[2];
		
		//Create Hbase Configration
		Configuration config = createHBaseConnection();
		//Create a HBaseAdmin object to manage tables
		HBaseAdmin admin = new HBaseAdmin(config);
		
		//delete the old table first
		HBaseDeleteTable deleteTable = new HBaseDeleteTable(admin);
		deleteTable.disableAndDeleteTable();
		
		if (mode.equalsIgnoreCase("multi")) {
			 startMulti(admin, config, numberOfThreads, numberOfRows);
		} 
	}
	
	private static void startMulti(HBaseAdmin hbaseAdmin, Configuration hbaseConfig, int numberOfThreads, int numberOfRows) {
		HBaseTreadedLoader loader = new HBaseTreadedLoader(hbaseAdmin, hbaseConfig, numberOfThreads, numberOfRows);
		loader.runMultiThreadedEngine();
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
