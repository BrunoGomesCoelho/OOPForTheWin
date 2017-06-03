package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
    * Get the current image
    * */
    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        this.image = img;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//            Esse metodo eh chamado antes de carregar a window
    }

    /*
    * Open the image and show it at the main window
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
}
