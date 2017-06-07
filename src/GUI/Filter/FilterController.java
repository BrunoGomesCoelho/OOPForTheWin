package GUI.Filter;

import GUI.Main.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by marcello on 05/06/2017.
 */
public class FilterController implements Initializable {

    @FXML private Label message;
    @FXML private ImageView preview;

    private String effect = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = MainController.getImage();

        if (img != null) preview.setImage(img);
        else message.setText("Open an image to select a filter");
    }

    /**
     * Method that closes the window without applying the changes
     *
     */
    public void cancelButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().getWindow().hide();
    }
}
