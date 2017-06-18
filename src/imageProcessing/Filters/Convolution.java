package imageProcessing.Filters;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import imageProcessing.Models.ImageModel;
import imageProcessing.utils.Utils;


/**
 * Classe para a operação de convolução de matrizes
 */
public class Convolution {
	private static double[][] BLUR = {
		{0.04, 0.04, 0.04, 0.04, 0.04}, 
		{0.04, 0.04, 0.04, 0.04, 0.04}, 
		{0.04, 0.04, 0.04, 0.04, 0.04},  
		{0.04, 0.04, 0.04, 0.04, 0.04}, 
		{0.04, 0.04, 0.04, 0.04, 0.04}
	};

	private static double[][] SHARPEN = {
		{0, -0.111, 0}, 
		{-0.111, 1.5, -0.111},
		{0, -0.111, 0}
	};
	
	private static double[][] EMBOSS = {
		{-0.222, -0.111, 0}, 
		{-0.111, 1.111, 0.111},
		{0, 0.111, 0.222}
	};
	
	private static double[][] OUTLINE = {
		{-0.111, -0.111, -0.111}, 
		{-0.111, 2, -0.111},
		{-0.111, -0.111, -0.111}
	};
	
	static private void setPixel(WritableRaster raster, int iimg, int jimg, int w, int h, double[][] mask){
		int i, j, k;
		double[] rgb = new double[3];
		Arrays.fill(rgb, 0);
		
		for(i = mask[0].length - 2; i > -2; i--) {
			for(j = mask[0].length - 2; j > -2; j--) {
				if(iimg + i > -1 && iimg + i < h && jimg + j > -1 && jimg + j < w) {
					for(k  = 0; k < 3; k++) {
						rgb[k] += raster.getSample(jimg + j, iimg + i, k) * mask[j + 1][i + 1];
					}
				}
			}
		}
		
		for(k = 0; k < 3; k++) {
			raster.setSample(jimg, iimg, k, Utils.truncate(rgb[k]));
		}
	}


	static public ImageModel convolutionBlur(ImageModel model) {
		return new ImageModel(convolution(model, BLUR), "Blur");
	}


	static public ImageModel convolutionEmboss(ImageModel model) {
		return new ImageModel(convolution(model, EMBOSS), "Emboss");
	}


	static public ImageModel convolutionOutline(ImageModel model) {
		return new ImageModel(convolution(model, OUTLINE), "Outline");
	}


	static public ImageModel convolutionSharpen(ImageModel model) {
		return new ImageModel(convolution(model, SHARPEN), "Sharpen");
	}


	static private BufferedImage convolution(ImageModel originalImage, double[][] mask){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		int i, j;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				setPixel(raster, i, j, img.getWidth(), img.getHeight(), mask);
			}
		}
		
		return img;
	}
}
