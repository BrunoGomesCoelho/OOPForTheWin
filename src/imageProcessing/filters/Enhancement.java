package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import models.ImageModel;
import utils.Utils;

public class Enhancement {
	static public int[][] getMinMax(ImageModel originalImage){		
		BufferedImage img = originalImage.getBufferedImage();
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
	
	static public ImageModel contrastModulation(ImageModel originalImage){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
	
		int[][] minMax = getMinMax(originalImage);
		int i, j, k;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, normalize(raster, i, j, k, minMax));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	static public ImageModel negative(ImageModel originalImage){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	static public ImageModel binary(ImageModel originalImage){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	static public ImageModel grayScale(ImageModel originalImage){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	static public ImageModel poster(ImageModel originalImage, int level){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	static public ImageModel noise(ImageModel originalImage, int level){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	static private double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	static public ImageModel vignette(ImageModel originalImage){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, k, x, y;
		double dist, div;
		
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;
		
		div = getDistance(0, 0, img.getWidth(), img.getHeight()) / 1.5;

		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				dist = getDistance(x, y, j, i);
				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) - 255 * dist / div));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	static public ImageModel vignette(ImageModel originalImage, int radius){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, k, x, y;
		double dist;
		
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;

		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				dist = getDistance(x, y, j, i);
				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) - 255 * dist / radius));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	static public ImageModel vignette(ImageModel originalImage, int radius, int[] color){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, k, x, y;
		double dist, diag;
		
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;
		diag = getDistance(0, 0, img.getWidth(), img.getHeight()) / 1.5;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				dist = getDistance(x, y, j, i);
				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) - 255 * dist / radius) + color[k] * dist / diag);
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	static public ImageModel radioactive(ImageModel originalImage){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	static public ImageModel hue(ImageModel originalImage, double level){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	
	static public ImageModel saturate(ImageModel originalImage, double level){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
	
	static public ImageModel bright(ImageModel originalImage, double level){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
		
	static public ImageModel pixelate(ImageModel originalImage, double size){
		ImageModel cnvImage = originalImage.copy();
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
		
		return new ImageModel(img);
	}
}
