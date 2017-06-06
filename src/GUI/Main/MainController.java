package GUI.Main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by marcello on 02/06/17.
 */

public class MainController implements Initializable{

    private File file;
    private Image image = null;

    @FXML private ImageView imageview;

    /**
     *
     *  Get the image beeing displayed
     *
     *  @return The current image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Set the image to a new one
     *
     * @param img A new image to be displayed
     */
    public void setImage(Image img) {
        this.image = img;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This method is called before the window show up.
        // Use when it's necessary
    }


    /*                          Menu
    ======================================================================== */

    // File ==================================================================

    /**
     * Opens a new window to select a blank image, choosing the color and size
     * Linked to File -> New button
     *
     * @param event: the button being pressed
     */
    public void newButton(ActionEvent event) {
        //  TODO Create a window when press the "new" button
    }

    /**
     * Open the image and show it at the main window.
     * Linked to File -> Open button at menu bar
     *
     * @param event: the button being pressed
     */
    public void openButton(ActionEvent event) {
        // Set up the file chooser
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        // Search for an image
        this.file = fc.showOpenDialog(null);

        // If the user select an image, show it
        if (this.file != null) {
            // For test purpose only
            System.out.println(file.getAbsolutePath());

            this.image = new Image(this.file.toURI().toString());

            imageview.setImage(this.image);
        }
    }

    /**
     * Saves the image with it's current name
     * Linked to File -> Save button at the menu bar
     *
     * @param event: the "save" button being pressed
     */
    public void saveButton(ActionEvent event) {
        // TODO Implement the save action
    }

    /**
     * Opens a window to save the image with the file manager
     * Linked to File -> Save As button at the menu bar
     *
     * @param event: the "save as" button being pressed
     */
    public void saveAsButton(ActionEvent event) {
        // TODO Implement the saveas action
    }

    /**
     * Closes the application completely.
     * Linked to File -> Close button at the menu bar
     *
     * @param event: the close button being pressed
     */
    public void closeAplication(ActionEvent event) {
        Platform.exit();
    }


    // Edit ==================================================================

    /**
     * Method to refresh the image after some filter/tool has modified it
     * Linked to Edit -> Refresh button at the menu bar
     *
     * @param event: the "refresh" button being pressed
     */
    public void refreshButton(ActionEvent event) {
        if (this.image != null) {
            imageview.setImage(this.image);
        }
    }



    // Filter =================================================================

    /**
     * Method that opens a new window to apply a filter at the image.
     * It's linked to (Filter -> Select a Filter) button at the menu bar
     *
     * @param event: the button being pressed
     * @throws Exception: TODO
     */
    public void selectFilterButton(ActionEvent event) throws Exception{
        Stage filterStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Filter/Filter.fxml"));
        filterStage.setTitle("Select a filter");
        filterStage.setScene(new Scene(root, 800,  600));
        filterStage.show();
    }



    // Help =================================================================

    /**
     * Method that opens the about window. It's linked to (Help -> About) button at the menu bar
     * @param event: the "About" button being pressed
     *
     * @throws IOException: TODO
     */
    public void aboutButton(ActionEvent event) throws IOException {
        Stage aboutStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../About/AboutPage.fxml"));
        aboutStage.setTitle("About");
        aboutStage.setResizable(false);
        aboutStage.setScene(new Scene(root, 600,  400));
        aboutStage.show();
    }



    /*                          Buttons
    ====================================================================== */

    public void negative(ActionEvent event) {
        // Use this method to call the negative function
    }

}
