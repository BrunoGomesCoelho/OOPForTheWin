package imageProcessing.Models;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

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

	public Raster getRaster() {
		 return this.image.getData();
	}

	public WritableRaster getWritableRaster() {
		 return this.image.getRaster();
	}

	public void setBufferedImage(BufferedImage image) {
		this.image = image;
	}

	public ImageModel copy() {
		 ColorModel cm = this.image.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = this.image.copyData(null);
		 BufferedImage img = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		 
		 return new ImageModel(img);
	}

	
	private static boolean isInside(Raster r, int i, int j){
		return (i > -1 && j > -1 && i < r.getHeight() && j < r.getWidth());
	}


	public static ImageModel blend(ImageModel img1, ImageModel img2, int x, int y, double p) {
		ImageModel cnvImage = img1.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		
		Raster r1 = img1.getBufferedImage().getData();
		Raster r2 = img2.getBufferedImage().getData();
		WritableRaster finalRaster = img.getRaster();
		
		int i, j, k;
		
		for(i = x; i < img.getHeight(); i++) {
			for(j = y; j < img.getWidth(); j++) {
				for(k = 0; k < 3; k++) {
					if(isInside(r1, i, j) && isInside(r2, i - y, j - x)){
						finalRaster.setSample(j, i, k, (1 - p) * r1.getSample(j, i, k) + p * r2.getSample(j - x, i - y, k));
					} else if(isInside(r1, i, j)){
						finalRaster.setSample(j, i, k, r1.getSample(j, i, k));
					} else {
						finalRaster.setSample(j, i, k, r2.getSample(j - x, i - y, k));
					}
				}
			}
		}
		
		return new ImageModel(img, "Blend");
	}
	
	public static ImageModel concat(ImageModel img1, ImageModel img2, int x, int y) {
		ImageModel cnvImage = img1.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		
		Raster r1 = img1.getBufferedImage().getData();
		Raster r2 = img2.getBufferedImage().getData();
		WritableRaster finalRaster = img.getRaster();
		
		int i, j, k;
		
		for(i = x; i < img.getHeight(); i++) {
			for(j = y; j < img.getWidth(); j++) {
				for(k = 0; k < 3; k++) {
					if(isInside(r2, i - y, j - x)){
						finalRaster.setSample(j, i, k, r2.getSample(j - x, i - y, k));
					} else {
						finalRaster.setSample(j, i, k, r1.getSample(j, i, k));
					}
				}
			}
		}
		
		return new ImageModel(img);
	}

}
