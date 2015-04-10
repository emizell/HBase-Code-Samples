package util;

import java.awt.Image;
import java.awt.image.PixelGrabber;

public class ImageComparator {
	
	private volatile static ImageComparator instance = null;
	
	protected ImageComparator() {
		// Exists only to defeat instantiation.
	}
	
	public static ImageComparator getInstance() {
		if (instance == null) {
			synchronized (ImageComparator.class) {
				if (instance == null) {
					instance = new ImageComparator();
				}
			}
			
		}
		return instance;
	}

	public boolean compareImages(Image image1, Image image2) {
		boolean imageSame = false;

		try {

			PixelGrabber grab1 =new PixelGrabber(image1, 0, 0, -1, -1, false);
			PixelGrabber grab2 =new PixelGrabber(image2, 0, 0, -1, -1, false);

			int[] data1 = null;

			if (grab1.grabPixels()) {
				int width = grab1.getWidth();
				int height = grab1.getHeight();
				data1 = new int[width * height];
				data1 = (int[]) grab1.getPixels();
			}

			int[] data2 = null;

			if (grab2.grabPixels()) {
				int width = grab2.getWidth();
				int height = grab2.getHeight();
				data2 = new int[width * height];
				data2 = (int[]) grab2.getPixels();
			}

			if (java.util.Arrays.equals(data1, data2)) {
				imageSame = true;
			}
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		return imageSame;
	}
}
