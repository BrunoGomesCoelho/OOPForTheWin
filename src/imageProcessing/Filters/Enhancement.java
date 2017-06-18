package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import models.ImageModel;
import utils.Utils;

public class Enhancement {
	/**
	 * @param srcImage - Imagem original
	 * @return os valores m�ximos e m�nimos para cada um dos canais RGB da imagem original
	 */
	static public int[][] getMinMax(ImageModel srcImage){
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
	 * M�todo para o realce de imagens com baixa varia��o de cor
	 *
	 * @param srcImage - Imagem Original
	 * @return Imagem real�ada
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
	 * M�todo para a invers�o de valor dos pixel da imagem
	 *
	 * @param srcImage - Imagem original
	 * @return Imagem invertida produzida
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
	 * M�todo para a binariza��o da imagem, de acordo com o bit mais significativo
	 * da m�dia dos canais RGB
	 *
	 * @param srcImage - Imagem original
	 * @return Imagem bin�ria produzida
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
	 * M�todo para a cria��o de uma imagem preto e branco a partir de uma imagem RGB
	 *
	 * @param srcImage - Imagem original
	 * @return Imagem em escala de cinza
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
	 * M�todo para a discretiza��o da imagem atrav�s da remo��o dos bits menos significativos
	 * da imagem original
	 *
	 * @param srcImage - imagem original
	 * @param level - n�vel de discretiza��o, entre 0 (menor discretiza��o) e 8 (m�xima discretiza��o)
	 * @return imagem discretizada
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
	 * M�todo para a inclus�o de ru�do em imagens
	 *
	 * @param srcImage - imagem original
	 * @param level - n�vel de ruidez da imagem. valores entre 0 e 255
	 * @return imagem ruidosa
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
	 * M�todo para a inclus�o de vinheta negra em imagens,
	 * baseada no comprimento da diagonal
	 *
	 * @param srcImage - imagem original
	 * @return imagem com a nova vinheta
	 */
	static public ImageModel vignette(ImageModel srcImage){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, k, x, y;
		double dist, div;
		
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;
		
		div = Utils.getDistance(0, 0, img.getWidth(), img.getHeight()) / 1.5;

		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				dist = Utils.getDistance(x, y, j, i);
				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) - 255 * dist / div));
				}
			}
		}
		
		return new ImageModel(img, "Vignette");
	}
	
	/**
	 * M�todo para a inclus�o de vinheta negra em imagens
	 *
	 * @param srcImage - imagem original
	 * @param radius - tamanho do raio da vinheta, com esta centralizada. qualquer valor inteiro
	 * @return imagem com a nova vinheta
	 */
	static public ImageModel vignette(ImageModel srcImage, int radius){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, k, x, y;
		double dist;
		
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;

		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				dist = Utils.getDistance(x, y, j, i);
				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) - 255 * dist / radius));
				}
			}
		}
		
		return new ImageModel(img, "Vignete");
	}
	
	 /**
	  * M�todo para a inclus�o de vinheta colorida em imagens
	  *
	  * @param srcImage - imagem original
	  * @param radius - tamanho do raio da vinheta, com esta centralizada. qualquer valor inteiro
	  * @param color - cor da vinheta
	  * @return imagem com a nova vinheta
	  */
	static public ImageModel vignette(ImageModel srcImage, int radius, int[] color){
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, k, x, y;
		double dist, diag;
		
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;
		diag = Utils.getDistance(0, 0, img.getWidth(), img.getHeight()) / 1.5;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				dist = Utils.getDistance(x, y, j, i);
				
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, Utils.truncate(raster.getSample(j, i, k) - 255 * dist / radius) + color[k] * dist / diag);
				}
			}
		}
		
		return new ImageModel(img, "Vignete");
	}
	
	/**
	 * M�todo para a modifica��o dos pixel baseado na invers�o de canais do sistema HSB
	 *
	 * @param srcImage - imagem original
	 * @return imagem modificada
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
	 * M�todo para a manipula��o do Hue (colora��o) dos pixels de uma imagem
	 *
	 * @param srcImage - imagem original
	 * @param level - n�vel da modifica��o - valores entre -1.0 e 1.0
	 * @return imagem com nova colora��o
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
	 * M�todo para a manipula��o da satura��o dos pixels de uma imagem
	 *
	 * @param srcImage - imagem original
	 * @param level - n�vel da modifica��o - valores entre -1.0 e 1.0
	 * @return imagem com nova satura��o
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
	 * M�todo para a manipula��o da luminosidade dos pixels de uma imagem
	 *
	 * @param srcImage - imagem original
	 * @param level - n�vel da modifica��o - valores entre -1.0 e 1.0
	 * @return imagem com nova luminosidade
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
	 * M�todo que gera uma vers�o pixelada da imagem original
	 *
	 * @param srcImage - Imagem Original
	 * @param size - tamanho de cada "pixel" gerado. Valores entre 0 e a dimens�o m�xima da imagem
	 * @return a imagem pixelada
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
