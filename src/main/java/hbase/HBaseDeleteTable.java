package hbase;

import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseDeleteTable {

	private HBaseAdmin admin;
	
	public HBaseDeleteTable(HBaseAdmin hbaseAdmin) {
		this.admin = hbaseAdmin;
	}
	
	public void disableAndDeleteTable () throws Exception {

		// Disable, and then delete the table
		admin.disableTable("contacts");
		admin.deleteTable("contacts");
	}

}
