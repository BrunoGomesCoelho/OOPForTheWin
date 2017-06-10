package imageProcessing.Filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import imageProcessing.Models.ImageModel;
import imageProcessing.utils.Utils;


public class ColorScale {
	static private int scaleFilter(Raster raster, int i, int j, int channel, double alpha){
		return Utils.truncate(raster.getSample(j, i, channel) * alpha);
	}
	
	static public ImageModel scale(ImageModel originalImage, int channel, double alpha){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = cnvImage.getWritableRaster();
		int i, j;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				raster.setSample(j, i, channel, scaleFilter(raster, i, j, channel, alpha));
			}
		}
		
		return new ImageModel(img, "Color scale scale");
	}
	
	static private int addFilter(Raster raster, int i, int j, int channel, int n){
		return Utils.truncate(raster.getSample(j, i, channel) + n);
	}
	
	static public ImageModel add(ImageModel originalImage, int channel, int n){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				raster.setSample(j, i, channel, addFilter(raster, i, j, channel, n));
			}
		}
		
		return new ImageModel(img, "Color scale add");
	}
}
