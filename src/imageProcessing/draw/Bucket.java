package imageProcessing.draw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import imageProcessing.Models.ImageModel;

/**
 *  Classe para a operação de preenchimento de cor, conhecido como balde (Bucket)
 *
 */
public class Bucket {
	/**
	 *
	 * @param srcImg - Imagem original
	 * @param x - Coordenada do clique do mouse
	 * @param y - Coordenada do clique do mouse
	 * @param c - Cor de preenchimento
	 * @param level - nível de detalhe do preenchimento, indo de 0 (máximo detalhe) á 8 (imagem inteira)
	 * @return Uma nova imagem com as modificações feitas
	 */
	static public ImageModel paint(ImageModel srcImg, int x, int y, Color c, int level){
		ImageModel newImg = srcImg.copy(); 
		BufferedImage bImg = newImg.getBufferedImage();
		WritableRaster raster = bImg.getRaster();
		
		Color selected = new Color(
			raster.getSample(x, y, 0) >> level << level,
			raster.getSample(x, y, 1) >> level << level,
			raster.getSample(x, y, 2) >> level << level
		);
		
		int i, j;
		
		for(i = 0; i < raster.getHeight(); i++) {
			for(j = 0; j < raster.getWidth(); j++) {
				if(raster.getSample(j, i, 0) >> level << level == selected.getRed() &&
					raster.getSample(j, i, 1) >> level << level == selected.getGreen() &&
					raster.getSample(j, i, 2) >> level << level == selected.getBlue()) {
					
					raster.setSample(j, i, 0, c.getRed());
					raster.setSample(j, i, 1, c.getGreen());
					raster.setSample(j, i, 2, c.getBlue());
				}
			}
		}

		return new ImageModel(bImg);
	}
}
