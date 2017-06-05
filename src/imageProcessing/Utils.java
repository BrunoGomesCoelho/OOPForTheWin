package imageProcessing;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;

/**
 * Created by Bruno on 05/06/2017.
 */
public class Utils {

    /** Função que converte uma imagem do tipo BufferedImage (para edição) em Image (para ser colocada na tela)
     * @param img A imagem a ser convertida
     * @return Uma nova imagem, que pode ser usada para ser impreensa na tela
     */
    public Image bufferdToImage(BufferedImage img) {
        return SwingFXUtils.toFXImage(img, null);
    }

}
