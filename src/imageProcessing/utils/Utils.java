package imageProcessing.utils;

import imageProcessing.Models.ImageModel;
import javafx.scene.paint.*;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class Utils {


	/**
	 * Método que retorna a cor de um pixel na posição (x, y) da imagem
	 * @param originalImage: a imagem original
	 * @param x - coordenadas do pixel
	 * @param y - coordenadas do pixel
	 * @return objeto Color contendo a cor selecionada
	 */
	static public Color colorSelect(ImageModel originalImage, int x, int y){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		Raster raster = img.getRaster();

		return new Color(
				raster.getSample(x, y, 0),
				raster.getSample(x, y, 1),
				raster.getSample(x, y, 2)
		);
	}


	static public int truncate(double x){
		int res;
		
		if(x > 255) res = 255;
		else if(x < 0) res = 0;
		else res = (int) x;
		
		return res;
	}
	
	static public double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	/**
	 * Method that converts a javafx.scene.paint.Color to java.awt.Color.
	 *
	 *
	 * @param oldColor: the old color, in a javafx.scene.paint.Color format
	 * @return: The new color, as a java.awt.Color
	 */
	public static Color convertColorToScene(javafx.scene.paint.Color oldColor) {
	    return new Color((float) oldColor.getRed(),
			    (float) oldColor.getGreen(),
			    (float) oldColor.getBlue(),
			    (float) oldColor.getOpacity());
    }


	/**
	 * Method that converts a java.awt.Color to a javafx.scene.paint.Color
	 *
	 *
	 * @param oldColor: the old color, in a java.awt.Color format
	 * @return: The new color, as a javafx.scene.paint.Color
	 */
    public static javafx.scene.paint.Color convertColorToAwt(Color oldColor) {
	    int r = oldColor.getRed();
	    int g = oldColor.getGreen();
	    int b = oldColor.getBlue();
	    int a = oldColor.getAlpha();
	    double opacity = a / 255.0 ;
	    return javafx.scene.paint.Color.rgb(r, g, b, opacity);
    }
}
