package GUI.Main;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Class that implements basic behaviours for the GUI
 */
class Utils {

    /**
     * Method that converts the pixels read from imageview to the actual pixels in the image
     *
     * @param imageview: The imageview object the user cliced on.
     * @param oldX: The associated x position
     * @param oldY: The associated y position
     * @return: A array of doubles, with the [0] position representing the new x position and the [1] position the
     * new y position.
     */
    static double[] resizePixel(ImageView imageview, double oldX, double oldY) {
	    // Transforming the pixel read from the user to a image pixel
        Bounds bounds = imageview.getLayoutBounds();
        double xScale = bounds.getWidth() / imageview.getImage().getWidth();
        double yScale = bounds.getHeight() / imageview.getImage().getHeight();

        double x = oldX / xScale;
        double y = oldY / yScale;

        double[] newPixels = {x, y};
        return newPixels;
    }



    /**
     * Gets the extension of a given file and returns it as a string
     * @param file: The file to be analized
     * @return String: a string with the given file type, with the dot(".") already in it
     */
    static String fileExtension(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.indexOf(".") + 1, file.getName().length());

    }

    static FileChooser fileWindow(String s) {
        FileChooser fc = new FileChooser();
        fc.setTitle(s);
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        return fc;
    }
}
