package imageProcessing.Filters;

import java.awt.*;

/**
 * Created by 9791160 on 08/06/2017.
 */
public class FilterInfo {
    private String[] names = {"ColorScale", "Convolution", "Enhancement", "Manipulate"};
    private int filterCount = 4;
    private int count;


    public FilterInfo() {
        this.count = 0;
    }


    /*
    TODO: Chamar as funções do Cyrillo aqui. Antes preciso entender como funcionam seus parâmetros



    public Image nextFilter(Image image) {
        switch (count) {
            case 0:
                count++;
                return ColorScale();
            case 1:
                count++;
                return Convolution();
            case 2:
                count++;
                return Enhancement();
            case 3:
                count++;
                return Manipulate();
            default:
                throw new RuntimeException("Invalid filter couner called"); // TODO: add own type of exception
        }
    }


    @Override
    public String toString() {
        if ( count < filterCount)
            return names[count];
        throw new RuntimeException("Invalid filter couner called"); // TODO: add own type of exception
    }


*/


}
