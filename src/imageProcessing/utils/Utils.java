package imageProcessing.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class Utils {
	static public int truncate(double x){
		int res;
		
		if(x > 255) res = 255;
		else if(x < 0) res = 0;
		else res = (int) x;
		
		return res;
	}

	/** Função que converte uma imagem do tipo BufferedImage (para edição) em Image (para ser colocada na tela)
	 * @param img A imagem a ser convertida
	 * @return Uma nova imagem, que pode ser usada para ser impreensa na tela
	 */
	public Image bufferdToImage(BufferedImage img) {
		return SwingFXUtils.toFXImage(img, null);
	}
}
