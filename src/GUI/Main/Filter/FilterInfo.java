package GUI.Main.Filter;

import imageProcessing.Filters.ColorScale;
import imageProcessing.Filters.Convolution;
import imageProcessing.Filters.Enhancement;
import imageProcessing.Filters.Manipulate;
import imageProcessing.Models.ImageModel;

import java.awt.*;

/**
 *
 * Used to call the image processing functions
 *
 * Created by Bruno on 08/06/2017.
 */
public class FilterInfo {

	static private int filterCount = 20;
    static private int count;
    static private ImageModel model;

    FilterInfo(Image image) {
        count = 0;
        model = new ImageModel(image);
    }

	static int getFilterCount() {
		return filterCount;
	}

	static void clearFilterCount() {
        count = 0;
    }

    static String getFilterName() {
    	return model.getLastFilter();
    }

     public static Image nextFilter(Image image) {
        switch (count++) {
            case 0: // TODO: paramêtro, valor de 0 a 255
                return ColorScale.add(model, "red", 123).getBufferedImage();
	            // return backup.getBufferedImage();

            case 1: // TODO: paramêtro
                return ColorScale.scale(model, "blue", 4).getBufferedImage();
	            // return backup.getBufferedImage();

            case 2:
                return Convolution.convolutionBlur(model).getBufferedImage();

            case 3:
                return Convolution.convolutionEmboss(model).getBufferedImage();

            case 4:
                return Convolution.convolutionOutline(model).getBufferedImage();

            case 5:
                return Convolution.convolutionSharpen(model).getBufferedImage();

            case 6:
                return Enhancement.binary(model).getBufferedImage();

            case 7: // TODO: paramêtro entre -1 e 1
                return Enhancement.bright(model, 0.8).getBufferedImage();
	            // return backup.getBufferedImage();

            case 8:
                return Enhancement.contrastModulation(model).getBufferedImage();

            case 9:
                return Enhancement.grayScale(model).getBufferedImage();

            case 10: // TODO: parametro
                return Enhancement.hue(model, -0.8).getBufferedImage();
	            // return backup.getBufferedImage();

            case 11:
                return Enhancement.negative(model).getBufferedImage();

            case 12:    // TODO: parametro
                return Enhancement.noise(model, 122).getBufferedImage();
	            // return backup.getBufferedImage();

            case 13:
            	// TODO: parametro
	            if (model.getBufferedImage().getHeight() > 15 && model.getBufferedImage().getWidth() > 15)
                    return Enhancement.pixelate(model, 15).getBufferedImage();
	            return model.getBufferedImage();

            case 14:    // TODO: parametro
                return Enhancement.poster(model, 6).getBufferedImage();
	            // return backup.getBufferedImage();

            case 15:
                return Enhancement.radioactive(model).getBufferedImage();

            case 16:    // TODO: parametro
                return Enhancement.saturate(model, 0.8).getBufferedImage();
	            // return backup.getBufferedImage();

            case 17:    // TODO: tem outros tipos de vignette, escolhi esse por enquanto
                return Enhancement.vignette(model).getBufferedImage();

            case 18:
                return Manipulate.mirror(model, "vertical").getBufferedImage();

            case 19:
                return Manipulate.mirror(model, "horizontal").getBufferedImage();

            default:
                throw new RuntimeException("Invalid filter counter called");
        }
    }


    @Override
    public String toString() {
        if ( count <= filterCount)
            return model.getLastFilter();
        throw new RuntimeException("Invalid filter counter passed as argument");
    }

}
