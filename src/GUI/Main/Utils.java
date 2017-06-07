package GUI.Main;

import javafx.stage.FileChooser;

import java.io.File;

/**
 * Class that implements basic behaviours for the GUI
 */
class Utils {

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
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        return fc;
    }
}
