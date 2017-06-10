package GUI.Filter;

import GUI.Main.MainController;
import imageProcessing.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Class used to manipulate the image an apply Filters
 *
 *
 * Created by marcello on 05/06/2017.
 */
public class FilterController implements Initializable {

    @FXML private Label message;
    @FXML private ImageView preview;
    @FXML private ListView<VBox> list;

    // private String effect = null;

    /**
     *  Private method used to return a Vbox, containing the image
     *  with a filter and a label with the filter's name. Iterate
     *  over this method to get all the Filters.
     *
     *  @param img: The image to apply the filter
     *
     *  @return A VBox, with a filter applied to the image and the filter's name
     */
    private VBox populate(Image img){
        VBox cell = new VBox();

        cell.setPadding(new Insets(10, 10, 10, 10));
        cell.setSpacing(10);

        ImageView preview = new ImageView();
        preview.setPreserveRatio(true);
        preview.setFitHeight(100);
        preview.setImage(SwingFXUtils.toFXImage( (java.awt.image.BufferedImage) FilterInfo.nextFilter(SwingFXUtils.fromFXImage(img, null)), null )); // Calling the next filter

        Label filterName = new Label(FilterInfo.getFilterName());
        filterName.setTextAlignment(TextAlignment.CENTER); // Not working

        cell.getChildren().addAll(preview, filterName);

        return cell;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = MainController.getImage();
	    FilterInfo filters = new FilterInfo(SwingFXUtils.fromFXImage(img, null));

        if (img != null) {
            preview.setImage(img);

            ObservableList<VBox> items = FXCollections.observableArrayList();
            for (int i = 0; i < FilterInfo.getFilterCount(); i++) items.add(populate(img));

            list.setOrientation(Orientation.HORIZONTAL);
            list.setItems(items);
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
