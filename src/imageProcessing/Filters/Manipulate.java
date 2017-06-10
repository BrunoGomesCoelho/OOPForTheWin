package imageProcessing.Filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import imageProcessing.Models.ImageModel;

public class Manipulate {


	// TODO
	static public void resize() {
		
	}


	//TODO
	static public void rotate() {
		
	}


	// TODO OBS: O parametro "size" não é usado. Pode deletar @Cyrillo?
	static public ImageModel strech(ImageModel originalImage, int size) {
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
		
		return new ImageModel(img, "Stretch");
	}


	static public ImageModel mirror(ImageModel originalImage, String dir) {
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		Raster srcRaster = originalImage.getBufferedImage().getRaster();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		int x, y;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {

				switch (dir) {
					case "vertical":
						x = j;
						y = img.getHeight() - 1 - i;
						break;
					case "horizontal":
						x = img.getWidth() - 1 - j;
						y = i;
						break;
					default:
						x = img.getWidth() - 1 - j;
						y = img.getHeight() - 1 - i;
						break;
				}
				
				for(k = 0; k < 3; k++) {					
					raster.setSample(x, y, k, srcRaster.getSample(j, i, k));
				}
			}
		}
		
		return new ImageModel(img, "Mirror");
	}
}
