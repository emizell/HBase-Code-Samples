package hbase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableMultiplexer;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import util.DataGenerator;
import util.Incrementor;

public class HBaseTreadedLoader {
	
	private int numberOfThreads = 0;
	private int numberOfRows = 0;
	private HBaseAdmin admin;
	private Configuration config;
	private HTableMultiplexer table;
	private DataGenerator dataGenerator;
	private Incrementor incrementor;
	
	public HBaseTreadedLoader(HBaseAdmin hbaseAdmin, Configuration hbaseConfig, int numberOfThreads, int numberOfRows) {
		this.admin = hbaseAdmin;
		this.config = hbaseConfig;
		this.numberOfThreads = numberOfThreads;
		this.numberOfRows = numberOfRows;
		dataGenerator = DataGenerator.getInstance();
		incrementor = Incrementor.getInstance();
		setupHBaseTable();
	}
	
	public void runMultiThreadedEngine() {
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Loader(this));
			threads[i].setName("THREAD-" + i);
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			logger("Load Complete");
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO: handle exception
			logger ("Failed to close the HBase table");
		}
		
	}
	
	private void setupHBaseTable() {
		// create the table...
		try {
			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("contacts"));
			// ... with two column families
			tableDescriptor.addFamily(new HColumnDescriptor("name"));
			tableDescriptor.addFamily(new HColumnDescriptor("contactinfo"));
			admin.createTable(tableDescriptor);

			table = new HTableMultiplexer(config, 10000);
		} catch (Exception e) {
			logger ("Failed to setup HBase table " + e.getStackTrace());
		}		
	}

	class Loader implements Runnable {
		HBaseTreadedLoader writer;

		Loader(HBaseTreadedLoader loadEngine) {
			this.writer = loadEngine;
		}

		public void run() {
			try {
				writer.loadData();
			} catch (Exception e) {
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				e.printStackTrace(pw);
				String errorDetail = writer.toString();
				logger("Failed to load data " + errorDetail);
			}
			
			logger("Finished loading table");
		}
		
	}
	
	private void loadData () throws Exception {
		logger("begin loading table");
		//number of threads divided by total rows is the count for each executor
		int count = Math.round((numberOfRows/numberOfThreads));
		logger(Thread.currentThread().getName() + " adding " + count + " rows to contacts table.....");
		ArrayList<Put> putList = new ArrayList<Put>();
		
		for (int i = 1; i < count + 1; i++) {
			Integer counter = incrementor.returnNextNumber();
			Put contact  = new Put(Bytes.toBytes(counter.toString()));
			
			String firstName = dataGenerator.getFirstName();
			contact.add(Bytes.toBytes("name"), Bytes.toBytes("first"), Bytes.toBytes(firstName));
			
			String lastName = dataGenerator.getLastName();
			contact.add(Bytes.toBytes("name"), Bytes.toBytes("last"), Bytes.toBytes(lastName));
			
			String emailAddress = firstName.toLowerCase() + "." + lastName.toLowerCase() + dataGenerator.getEmailProvider();
			contact.add(Bytes.toBytes("contactinfo"), Bytes.toBytes("email"), Bytes.toBytes(emailAddress));
			
			putList.add(contact);
			
			if (i%1000 == 0) {
				table.put(Bytes.toBytes("contacts"), putList);
				Thread.sleep(10000);		
				logger("Number of rows inserted so far for this tread is " + i);
				putList = new ArrayList<Put>();
			} 
		}	
		
	}
	
	private void logger(String msg, Object... p) {
		System.out.printf("[" + Thread.currentThread().getName() + "] "
				+ (new Date()) + " " + msg + "\n", p);
	}
}
