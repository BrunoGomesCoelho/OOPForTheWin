package GUI.Filter;

import GUI.Main.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    @FXML private ListView<String> list;

    private String effect = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = MainController.getImage();

        if (img != null) {
            preview.setImage(img);

            ObservableList<String> items = FXCollections.observableArrayList("Single", "Double", "Suite", "etc"
            , "mais", "um", "outro", "quero", "o", "scroll", "muitos", "items", "pra", "mostrar", "");

            list.setOrientation(Orientation.HORIZONTAL);
            list.setItems(items);


            list.setCellFactory(param -> new ListCell<String>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(String name, boolean empty) {
                    super.updateItem(name, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        imageView.setImage(img);
                        setText(name);
                        imageView.setPreserveRatio(true);
                        // TODO: Só modificar esse valor que ele já mantem a proporção por causa da linha de cima
                        imageView.setFitWidth(100);
                        setGraphic(imageView);
                    }
                }
            });

        }

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
