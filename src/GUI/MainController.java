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

    @FXML private ImageView imageview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//            Esse metodo eh chamado antes de carregar a window
    }

    public void openButton(ActionEvent event) {
        // Set up the file chooser
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        // Search for an image
        File file = fc.showOpenDialog(null);

        // If the user select an image, show it
        if (file != null) {
            // For test purpose only
            System.out.println(file.getAbsolutePath());

            Image image = new Image(file.toURI().toString());
            imageview.setImage(image);
        }
    }
}
