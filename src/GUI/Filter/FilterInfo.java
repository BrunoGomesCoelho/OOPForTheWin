package GUI.Filter;

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
    static private String[] names = {"ColorScale", "Convolution", "Enhancement", "Manipulate"};

	static private int filterCount = 20;
    static private int count;
    static private ImageModel model;

    // TODO: Deletar depois que todos os filtros estejam funcioando
    static private ImageModel backup;

    public FilterInfo(Image image) {
        count = 0;
        model = new ImageModel(image);
        backup = new ImageModel(image);
    }

	public static int getFilterCount() {
		return filterCount;
	}

	public static void clearFilterCount() {
        count = 0;
    }

    public static String getFilterName() {
    	return model.getLastFilter();
    }

    // TODO: Chamar as funções do Cyrillo aqui. Usar uns parâmetros decentes porque não sei o que to passando (B)

	// TODO: não seria bom perguntar para o usuário escolher um valor para o(s) parametro(s) ?

     public static Image nextFilter(Image image) {
        switch (count++) {
            case 0: // TODO: paramêtro, valor de 0 a 255
                return ColorScale.add(model, "red", 123).getBufferedImage();
	            // return backup.getBufferedImage();

            case 1: // TODO: paramêtro
                return ColorScale.scale(model, "blue", 1.8).getBufferedImage();
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
                return Enhancement.bright(model, 0.5).getBufferedImage();
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

            case 13:    // TODO: parametro
                // TODO: Acho que se a imagem não for no mínimo 10 por 10 pixels vai dar ruim
                return Enhancement.pixelate(model, 10).getBufferedImage();
	            // return backup.getBufferedImage();

            case 14:    // TODO: parametro
                return Enhancement.poster(model, 4).getBufferedImage();
	            // return backup.getBufferedImage();

            case 15:
                return Enhancement.radioactive(model).getBufferedImage();

            case 16:    // TODO: parametro
                return Enhancement.saturate(model, 0.5).getBufferedImage();
	            // return backup.getBufferedImage();

            case 17:    // TODO: tem outros tipos de vignette, escolhi esse por enquanto
                return Enhancement.vignette(model).getBufferedImage();

            case 18:
                return Manipulate.mirror(model, "vertical").getBufferedImage();

            case 19:
                return Manipulate.mirror(model, "horizontal").getBufferedImage();

            // TODO: adicionar Manipulate.resize & Manipulate.rotate depois que prontos

            default:
                throw new RuntimeException("Invalid filter counter called"); // TODO: add own type of exception
        }
    }


    @Override
    public String toString() {
        if ( count <= filterCount) // TODO: verificar se menor ou igual funciona
            return model.getLastFilter();
        throw new RuntimeException("Invalid filter counter passed as argument"); // TODO: add own type of exception
    }

}
