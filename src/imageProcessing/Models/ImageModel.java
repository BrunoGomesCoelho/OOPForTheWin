package imageProcessing.Models;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ImageModel {
	private BufferedImage image;

	private String lastFilter;

	public ImageModel(Image image) {
		this.image = (BufferedImage) image;
	}


	public ImageModel(BufferedImage img) {
		setBufferedImage(img);
	}


	public ImageModel(BufferedImage img, String filter) {
		setBufferedImage(img);
		this.lastFilter = filter;
	}


	public ImageModel(int width, int height, int imageType) {
		this.image = new BufferedImage(width, height, imageType);
	}

	public ImageModel(int width, int height) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public String getLastFilter() {
		return lastFilter;
	}

	public BufferedImage getBufferedImage() {
		return image;
	}

	public WritableRaster getWritableRaster() {
		 return this.image.getRaster();
	}

	private void setBufferedImage(BufferedImage image) {
		this.image = image;
	}

	public ImageModel copy() {
		 ColorModel cm = this.image.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = this.image.copyData(null);
		 BufferedImage img = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		 
		 return new ImageModel(img);
	}
}
