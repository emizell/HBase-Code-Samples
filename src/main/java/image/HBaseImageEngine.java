package image;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseImageEngine {

	public static void main (String[] args) throws Exception {
		
		String folderLocation = args.length == 0 ? "<path to files>/HBase-Code-Samples/images/": args[0];
		
		//Create Hbase Configration
		Configuration config = createHBaseConnection();
		//Create a HBaseAdmin object to manage tables
		HBaseAdmin admin = new HBaseAdmin(config);
		
		processImages(admin, config, folderLocation);
	}
	
	private static void processImages(HBaseAdmin hbaseAdmin, Configuration hbaseConfig, String folderLocation) throws Exception {
		HBaseLoadImage imageProcessor = new HBaseLoadImage(hbaseConfig, hbaseAdmin, folderLocation);
		imageProcessor.processImages();
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
