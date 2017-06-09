package imageProcessing.Filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import imageProcessing.Models.ImageModel;

public class Manipulate {
	static public int VERTICAL = 0;
	static public int HORIZONTAL = 1;
	
	static public void resize() {
		
	}
	
	static public void rotate() {
		
	}
	
	static public ImageModel strech(ImageModel originalImage, int dir, int size) {
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		Raster srcRaster = originalImage.getBufferedImage().getRaster();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, srcRaster.getSample(j, i, k));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	static public ImageModel mirror(ImageModel originalImage, int dir) {
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		Raster srcRaster = originalImage.getBufferedImage().getRaster();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		int x, y;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				
				if(dir == VERTICAL) {
					x = j;
					y = img.getHeight() - 1 - i;
				} else if(dir == HORIZONTAL) {
					x = img.getWidth() - 1 - j;
					y = i;
				} else {
					x = img.getWidth() - 1 - j;
					y = img.getHeight() - 1- i;
				}
				
				for(k = 0; k < 3; k++) {					
					raster.setSample(x, y, k, srcRaster.getSample(j, i, k));
				}
			}
		}
		
		return new ImageModel(img);
	}
}
