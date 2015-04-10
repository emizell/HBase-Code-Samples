package image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseLoadImage {
	
	private String fileLocation;
	private Configuration config;
	private HBaseAdmin admin;
	private HTable table;
	
	public HBaseLoadImage(Configuration hbaseConfig, HBaseAdmin admin,
			String fileLocation) {
		this.fileLocation = fileLocation;
		this.config = hbaseConfig;
		this.admin = admin;
	}
	
	public void processImages() throws Exception {

		//check if table exists
		if(!admin.tableExists("image")){
			// create the table...
			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("image"));
			// ... with two column families
			tableDescriptor.addFamily(new HColumnDescriptor("image"));
			admin.createTable(tableDescriptor);
		}
		
		table = new HTable(config, "image");
		
		ArrayList<String> fileList = readFilesFromFolder(fileLocation);
		
		for (String fileName : fileList) {
			byte imagebyte[] = convertImage(fileLocation + fileName);
			byte checkSum[] = getCheckSum(imagebyte);
			String rowKey = fileName.substring(0, fileName.lastIndexOf('.'));
			
			if (!checkIfImagesExists(checkSum, fileName)) {
				Put image = new Put(Bytes.toBytes(rowKey));
				image.add(Bytes.toBytes("image"), Bytes.toBytes("image-name"), Bytes.toBytes(fileName));
				image.add(Bytes.toBytes("image"), Bytes.toBytes("checksum"), checkSum);
				image.add(Bytes.toBytes("image"), Bytes.toBytes("image-bytes"), imagebyte);
				table.put(image);
			}
			
		}
		// flush commits and close the table
		table.flushCommits();
		table.close();
		admin.close();
		
		System.out.println("Done");
	}
	
	private boolean checkIfImagesExists(byte checksum[], String checkImageName) throws Exception {
		boolean exists = false;
		
		// Define the family and qualifiers to be used
		byte[] imageColumn = Bytes.toBytes("image");
		byte[] checksumValue = Bytes.toBytes("checksum");
		byte[] imageName = Bytes.toBytes("image-name");
	
		// Attach the regex filter to a filter
		//   for the email column
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				imageColumn,
				checksumValue,
				CompareOp.EQUAL,
				checksum);

		// Create a scan and set the filter
		Scan scan = new Scan();
		scan.setFilter(filter);
		
		ResultScanner results = table.getScanner(scan);
		// Iterate over results and print  values
		for (Result result : results ) {
			String name = new String(result.getValue(imageColumn, imageName));
			
			//if the image names are the same, don't store them again. If the checksum is the same with a different name, write a message and store
			if (checkImageName == name) {
				exists = true;
			} else {
				System.out.println(checkImageName + " exists and is stored as image name " + name);
			}
			
		}
		
		return exists;
	}
	
	/*
	 * Read in a list of files
	 */
	private ArrayList<String> readFilesFromFolder (String folderLocation) throws Exception {
		ArrayList<String> fileNames = new ArrayList<String>();
		File folder = new File(folderLocation);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	fileNames.add(file.getName());
		    }
		}
		
		return fileNames;
	}
	
	/*
	 * Calculate the MD5 checksum
	 */
	private byte[] getCheckSum (byte input[]) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte checkSum[] = md.digest(input);
		return checkSum;
	}
	
	
	/*
	 * Convert an image to a byte array
	 */
	private byte[] convertImage (String ImageName)throws IOException {

		byte[] imageInByte;
		BufferedImage originalImage = ImageIO.read(new File(ImageName));

		// convert BufferedImage to byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "jpg", baos);
		imageInByte = baos.toByteArray();
		baos.close();

		return imageInByte;
	}
	
}