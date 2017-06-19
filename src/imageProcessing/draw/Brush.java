package imageProcessing.draw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


import imageProcessing.Models.ImageModel;
import static imageProcessing.utils.Utils.getDistance;


/**
 * Classe para as operações de desenho atráves do mouse, comumente  conhecida como pincel (Brush)
 *
 */
public class Brush {
	/**
	 * 
	 * @param srcImg - Imagem original
	 * @param x - Coordenada do ponto
	 * @param y - Coordenada do ponto
	 * @param r - tamanho do raio do pincel circular
	 * @param c - Cor do pincel
	 * @return uma nova imagem com as modificaçõees feitas
	 */
	static public ImageModel paint(ImageModel srcImg, int x, int y, int r, Color c) {
		ImageModel newImg = srcImg.copy(); 
		BufferedImage bImg = newImg.getBufferedImage();
		WritableRaster raster = bImg.getRaster();
		
		int i, j;

		for(i = y - r; i < y + r && i < bImg.getHeight(); i++) {
			if(i > -1) {
				for(j = x - r; j < x + r && j < bImg.getWidth(); j++) {
					if(j > -1 && getDistance(x, y, j, i) <= r) {
						raster.setSample(j, i, 0, c.getRed());
						raster.setSample(j, i, 1, c.getGreen());
						raster.setSample(j, i, 2, c.getBlue());
					}
				}
			}
		}

		return new ImageModel(bImg);
	}
	
	/**
	 * 
	 * @param srcImg - Imagem original
	 * @param x - Coordenada do ponto
	 * @param y - Coordenada do ponto
	 * @param size - tamanho da lateral do pincel quadrado
	 * @param c - Cor do pincel
	 * @return uma nova imagem com as modificaçõees feitas
	 */
	
	static public ImageModel paintSquare(ImageModel srcImg, int x, int y, int size, Color c) {
		ImageModel newImg = srcImg.copy(); 
		BufferedImage bImg = newImg.getBufferedImage();
		WritableRaster raster = bImg.getRaster();
		
		int i, j;
		
		for(i = y - size; i < y + size && i < bImg.getHeight(); i++) {
			if(i > -1) {
				for(j = x - size; j < x + size && j < bImg.getWidth(); j++) {
					if(j > -1) {
						raster.setSample(j, i, 0, c.getRed());
						raster.setSample(j, i, 1, c.getGreen());
						raster.setSample(j, i, 2, c.getBlue());
					}
				}
			}
		}

		return new ImageModel(bImg);
	}
}