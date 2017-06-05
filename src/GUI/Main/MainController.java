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
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by marcello on 02/06/17.
 */

public class MainController implements Initializable{

    private File file;
    private Image image = null;

    @FXML private ImageView imageview;

    /*
    *  Get the current image
    *
    */
    public Image getImage() {
        return this.image;
    }

    /*
    * Set the image to a new one
    */
    public void setImage(Image img) {
        this.image = img;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Esse metodo eh chamado antes de carregar a window
    }


    /*                          Menu
    ======================================================================== */

    // File ==================================================================
    public void newButton(ActionEvent event) {

    }

    /*
    * Open the image and show it at the main window
    *
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

    public void saveButton(ActionEvent event) {

    }

    public void saveAsButton(ActionEvent event) {

    }

    public void closeAplication(ActionEvent event) {
        Platform.exit();
    }



    // Edit ==================================================================

    /*
    * Method to refresh the image after some filter/tool
    *
    */
    public void refreshButton(ActionEvent event) {
        if (this.image != null) {
            imageview.setImage(this.image);
        }
    }



    // Filter =================================================================

    /*
    * Method that opens a new window to apply a filter at the image
    *
    */
    public void selectFilterButton(ActionEvent event) throws Exception{
        Stage filterSatge = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Filter/Filter.fxml"));
        filterSatge.setTitle("Select a filter");
        filterSatge.setScene(new Scene(root, 600,  400));
        filterSatge.show();
    }

    /*                          Buttons
    ====================================================================== */

    public void negative(ActionEvent event) {
        // Use this method to call the negative function
    }

}