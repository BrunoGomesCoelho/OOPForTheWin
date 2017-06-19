package GUI.Main;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Class that implements basic behaviours for the GUI.
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

	    return new double[]{x, y};
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


	/**
	 * Functions that opens a window to let us choose a image to be opened.
	 * We allow jpg, jpeg, and png file to be used.
	 *
	 * @param s: The title of the window
	 * @return: A new FileChooser object
	 */
	static FileChooser fileWindowOpen(String s) {
        FileChooser fc = new FileChooser();
        fc.setTitle(s);
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
	            new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        return fc;
    }


	/**
	 * Functions that opens a window to let us choose a image to be saved.
	 * We allow jpeg and png file to be used. Jpg files are excluded since they do not work well
	 * with image processing.
	 *
	 * @param s: The title of the window
	 * @return: A new FileChooser object
	 */
	static FileChooser fileWindowSave(String s) {
		FileChooser fc = new FileChooser();
		fc.setTitle(s);
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
		return fc;
	}
}
