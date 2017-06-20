package imageProcessing.Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;


import imageProcessing.Models.ImageModel;
import imageProcessing.utils.Utils;

public class Enhancement {
	/**
	 * @param srcImage - Original image
	 * @return The maximum and minimum values for each one of the RGB channels in the original image.
	 */
	private static int[][] getMinMax(ImageModel srcImage){
		BufferedImage img = srcImage.getBufferedImage();
		Raster raster = img.getData();
		int[][] minMax = new int[2][3];
		int i, j, k, channel;
		
		for(i = 0; i < 3; i++) {
			minMax[0][i] = raster.getSample(0, 0, i);
			minMax[1][i] = minMax[0][i];
		}
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				for(k = 0; k < 3; k++) {
					channel = raster.getSample(j, i, k);
					if(minMax[0][k] > channel)
						minMax[0][k] = channel;
					
					if(minMax[1][k] < channel)
						minMax[1][k] = channel;
				}
			}
		}
		
		return minMax;
	}
	
	static private double normalize(Raster raster, int i, int j, int k, int[][] minMax){
		return (raster.getSample(j, i, k) - minMax[0][k]) * 255 / (minMax[1][k] - minMax[0][k]);
	}
	
	/**
	 * Method for the highlighting of images with a low color variance.
	 *
	 * @param srcImage Original image
	 * @return the highlighted image
	 */
	static public ImageModel contrastModulation(ImageModel srcImage){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
	
		int[][] minMax = getMinMax(srcImage);
		int i, j, k;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, normalize(raster, i, j, k, minMax));
				}
			}
		}
		
		return new ImageModel(img, "Contrast modulation");
	}
	
	/**
	 * Methor for the inversion of the value of the pixels in a image
	 *
	 * @param srcImage Original image
	 * @return the inverted image
	 */
	static public ImageModel negative(ImageModel srcImage){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		int i, j;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				raster.setSample(j, i, 0, 255 - raster.getSample(j, i, 0));
				raster.setSample(j, i, 1, 255 - raster.getSample(j, i, 1));
				raster.setSample(j, i, 2, 255 - raster.getSample(j, i, 2));
			}
		}
		
		return new ImageModel(img, "Negative");
	}
	
	/**
	 * Method for the binarization of the image, using the most significant bit of the average of the RGB channels
	 *
	 * @param srcImage Original image
	 * @return the binary image produced
	 */
	static public ImageModel binary(ImageModel srcImage){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		double grayScale;
		int i, j, g;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				grayScale = raster.getSample(j, i, 0) + raster.getSample(j, i, 1) + raster.getSample(j, i, 2);
				grayScale /= 3;
				g = (grayScale > 128) ? 255 : 0;
				
				raster.setSample(j, i, 0, g);
				raster.setSample(j, i, 1, g);
				raster.setSample(j, i, 2, g);
			}
		}
		
		return new ImageModel(img, "Binary");
	}


	/**
	 * Method for creating a black and white image
	 *
	 * @param srcImage Original image
	 * @return the greyscale image produced
	 */
	static public ImageModel grayScale(ImageModel srcImage){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();

		double grayScale;
		
		int i, j;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				grayScale = raster.getSample(j, i, 0) + raster.getSample(j, i, 1) + raster.getSample(j, i, 2);
				grayScale /= 3;
				
				raster.setSample(j, i, 0, grayScale);
				raster.setSample(j, i, 1, grayScale);
				raster.setSample(j, i, 2, grayScale);
			}
		}
		
		return new ImageModel(img, "Gray Scale");
	}
	
	/**
	 * Method for the discretization of a image by removing the least significant bits in the original image
	 *
	 * @param srcImage Original image
	 * @param level - The level of discretization, between 0 (little discretization) and 8 (maximum discretization)
	 * @return the image produced
	 */
	static public ImageModel poster(ImageModel srcImage, int level){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				raster.setSample(j, i, 0, raster.getSample(j, i, 0) >> level << level);
				raster.setSample(j, i, 1, raster.getSample(j, i, 1) >> level << level);
				raster.setSample(j, i, 2, raster.getSample(j, i, 2) >> level << level);
			}
		}
		
		return new ImageModel(img, "Poster");
	}
	
	/**
	 * Method for adding noise to images
	 *
	 * @param srcImage Original image
	 * @param level - Level of noise in the image. Values between 0 and 255
	 * @return the produced image
	 */
	static public ImageModel noise(ImageModel srcImage, int level){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, k;
		double noise;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				noise = (Math.random() > 0.5) ? level * Math.random() : -level * Math.random();
				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) + noise));
				}
			}
		}
		
		return new ImageModel(img, "Noise");
	}
	
	/**
	 * Method for creating a black vignette in images based on the size of the image's diagonal.
	 *
	 * @param srcImage Original image
	 * @return the imagem with the new vignette
	 */
	static public ImageModel vignette(ImageModel srcImage){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int x, y;
		double div;
		
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;
		
		div = Utils.getDistance(0, 0, img.getWidth(), img.getHeight()) / 1.5;

		vigneteAux(raster, img, x, y, div);

		return new ImageModel(img, "Vignette");
	}


	/**
	 * Auxiliar function to the main vignette function above.
	 */
	private static void vigneteAux(WritableRaster raster, BufferedImage img, int x, int y, double div) {
		int i, j, k;
		double dist;

		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				dist = Utils.getDistance(x, y, j, i);

				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) - 255 * dist / div));
				}
			}
		}
	}


	/**
	 * Method for the modification of a images pixels, based on the inversion of its RGB channels.
	 *
	 * @param srcImage Original image
	 * @return the modified image
	 */
	static public ImageModel radioactive(ImageModel srcImage){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		Color c;
		
		int i, j;		
		float gray;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				gray = raster.getSample(j, i, 0) + raster.getSample(j, i, 1) + raster.getSample(j, i, 2);
				gray /= 3;
			
				c = Color.getHSBColor((1 - gray / 255), (float) 1.0, (float) 1.0);
				
				raster.setSample(j, i, 0, c.getRed());
				raster.setSample(j, i, 1, c.getGreen());
				raster.setSample(j, i, 2, c.getBlue());
			}
		}
		
		return new ImageModel(img, "Radioactive");
	}
	
	/**
	 * Method for the manipulation of the hue levels in a image
	 *
	 * @param srcImage Original image
	 * @param level - level of modification. Values between -1.0 and 1.0
	 * @return the image with its new hue values.
	 */
	static public ImageModel hue(ImageModel srcImage, double level){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		Color c;
		
		float[] hsb = new float[3];
		int i, j;
		float h;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {				
				Color.RGBtoHSB(
					raster.getSample(j, i, 0), 
					raster.getSample(j, i, 1), 
					raster.getSample(j, i, 2),
					hsb
				);
				
				h = hsb[1] + (float) level;
				if(h > 1) h = 1;
				if(h < 0) h = 0;
				
				c = Color.getHSBColor(h, hsb[1], hsb[2]);
				
				raster.setSample(j, i, 0, c.getRed());
				raster.setSample(j, i, 1, c.getGreen());
				raster.setSample(j, i, 2, c.getBlue());
			}
		}
		
		return new ImageModel(img, "Hue");
	}
	
	/**
	 * Method for the manipulation of the saturation of the pixels in a image
	 *
	 * @param srcImage Original image
	 * @param level - level of modification. Values between -1.0 and 1.0
	 * @return the saturated image
	 */
	static public ImageModel saturate(ImageModel srcImage, double level){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		Color c;
		
		float[] hsb = new float[3];
		int i, j;
		float s;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {				
				Color.RGBtoHSB(
					raster.getSample(j, i, 0), 
					raster.getSample(j, i, 1), 
					raster.getSample(j, i, 2),
					hsb
				);
				
				s = hsb[1] + (float) level;
				if(s > 1) s = 1;
				if(s < 0) s = 0;
				
				c = Color.getHSBColor(hsb[0], s, hsb[2]);
				
				raster.setSample(j, i, 0, c.getRed());
				raster.setSample(j, i, 1, c.getGreen());
				raster.setSample(j, i, 2, c.getBlue());
			}
		}
		
		return new ImageModel(img, "Saturate");
	}
	
	/**
	 * Method for the manipulation of the luminosity of the pixels in a image
	 *
	 * @param srcImage Original image
	 * @param level - level of modification. Values between -1.0 and 1.0
	 * @return the edited image
	 */
	static public ImageModel bright(ImageModel srcImage, double level){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		Color c;
		
		float[] hsb = new float[3];
		int i, j;
		float b;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {				
				Color.RGBtoHSB(
					raster.getSample(j, i, 0), 
					raster.getSample(j, i, 1), 
					raster.getSample(j, i, 2),
					hsb
				);
				
				b = hsb[2] + (float) level;
				if(b > 1) b = 1;
				if(b < 0) b = 0;
				
				c = Color.getHSBColor(hsb[0], hsb[1], b);
				
				raster.setSample(j, i, 0, c.getRed());
				raster.setSample(j, i, 1, c.getGreen());
				raster.setSample(j, i, 2, c.getBlue());
			}
		}
		
		return new ImageModel(img, "Bright");
	}
		
	/**
	 * Method that creates a pixelated version of the original image
	 *
	 * @param srcImage Original image
	 * @param size - The size of each new "pixel" created. Values between 0 and the dimension of the image.
	 * @return The pixelated image
	 */
	static public ImageModel pixelate(ImageModel srcImage, double size){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		Double di, dj;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				for(k = 0; k < 3; k++) {
					di = i - (i % size);
					dj = j - (j % size);
					raster.setSample(j, i, k, raster.getSample(dj.intValue(), di.intValue(), k));
				}
			}
		}
		
		return new ImageModel(img, "Pixelate");
	}
}
