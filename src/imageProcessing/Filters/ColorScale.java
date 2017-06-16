package filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import utils.Utils;

import models.ImageModel;

/**
 * Classe para a modificação de cor em sistemas RGB
 */
public class ColorScale {
	static private int scaleFilter(Raster raster, int i, int j, int channel, double alpha){
		return Utils.truncate(raster.getSample(j, i, channel) * alpha);
	}
	
	/**
	 * Método para a modificação de um canal RGB atraves de um fator de multiplicação
	 * 
	 * Ex.: scale(img, 0, 1.25) - Acrecimo de 25% em vermelho
	 * Ex.: scale(img, 2, 0.75) - 75% da quantidade de azul da imagem original 
	 * 
	 * @param originalImage
	 * @param channel - canal RGB que será modificado (R - 0; G - 1; B - 2)
	 * @param alpha - fator multiplicativo do canal. Valores de 0 até 255 (No entanto, valores entre 0 e 5 são mais relevantes) 
	 * @return
	 */
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
		
		return new ImageModel(img);
	}
	
	static private int addFilter(Raster raster, int i, int j, int channel, int n){
		return Utils.truncate(raster.getSample(j, i, channel) + n);
	}
	
	/**
	 * Método para a modificação de um canal RGB atraves de um fator aditivo
	 * 
	 * Ex.: scale(img, 0, 25) - todos os valores de vermelho terão um acrescimo de 25
	 * 
	 * @param originalImage
	 * @param channel - canal RGB que será modificado (R - 0; G - 1; B - 2)
	 * @param alpha - fator aditivo do canal. Valores de 0 até 255
	 * @return imagem modificada
	 */
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
		
		return new ImageModel(img);
	}
}
