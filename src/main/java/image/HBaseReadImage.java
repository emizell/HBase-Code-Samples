package image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseReadImage {
	
	//fine a specific file and write it out with a different name to test Byte to image conversion
	public static void main(String[] args) throws IOException {
		Configuration config = HBaseConfiguration.create();

		config.set("hbase.zookeeper.quorum",
				"<quorum1>, <quorum2>, <quorum3>");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.cluster.distributed", "true");

		// create an admin object using the config
		HBaseAdmin admin = new HBaseAdmin(config);
		System.out.println("Connection successful");

		HTable table = new HTable(config, "image");

		Get g = new Get(Bytes.toBytes("1"));
		Result r = table.get(g);
		byte[] value = r.getValue(Bytes.toBytes("image"), Bytes
				.toBytes("test"));

		convertToImage(value);

		table.close();
		System.out.println("Done");
	}

	//write the file out
	private static void convertToImage (byte[] imageInByte) throws IOException {     

		// convert byte array back to BufferedImage
		InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, "jpg", new File(
				"<path to images>/HBase-Code-Samples/images/image2.jpeg"));
	}

}