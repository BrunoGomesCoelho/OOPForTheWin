package GUI.Main.Filter;

import GUI.Main.MainController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Class used to manipulate the image and apply filters inside the "Filter" window
 */
public class FilterController implements Initializable {

    @FXML private Label message;
    @FXML private ImageView preview = null;
    @FXML private ListView<VBox> list;


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

        // Set the space inside the Box
        cell.setPadding(new Insets(10, 10, 10, 10));
        cell.setSpacing(10);

        // Create a new imageView
        ImageView prv = new ImageView();
        prv.setPreserveRatio(true);
        prv.setFitHeight(100);

        // Apply a filter to the image from FilterInfo.nextFilter
        prv.setImage(SwingFXUtils.toFXImage(
                (java.awt.image.BufferedImage)
                        FilterInfo.nextFilter(
                                SwingFXUtils.fromFXImage(img, null)),
                null )); // Calling the next filter

        // TODO Colocar label nos filtros
        Label filterName = new Label(FilterInfo.getFilterName());
        filterName.setTextAlignment(TextAlignment.CENTER); // Not working

        // Put the imagePreview and the Label in the cell
        cell.getChildren().addAll(prv, filterName);

        // Set function to do when clicked
        cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> preview.setImage(prv.getImage()));

        return cell;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the image from the main window
        Image img = MainController.getImage();

        // If there's an image at the main window
        if (img != null) {
            // Set the large image
            preview.setImage(img);

            FilterInfo filters = new FilterInfo(SwingFXUtils.fromFXImage(img,
                    null));
            ObservableList<VBox> items = FXCollections.observableArrayList();

            for(int i = 0; i<FilterInfo.getFilterCount(); i++)
                items.add(populate(img));

            list.setOrientation(Orientation.HORIZONTAL);
            list.setItems(items);
        }

        // Set the text asking to open an image
        else message.setText("Abra uma imagem para selecionar um filtro");

        FilterInfo.clearFilterCount();
    }

    /**
     * Method that closes the window without applying the changes
     *
     */
    public void cancelButton(ActionEvent event) {
        // Close this window
        Node node = (Node) event.getSource();
        node.getScene().getWindow().hide();
    }

    /**
     * Method that moves the select filter to the main window
     *
     * @param event The button preesed
     */
    public void okButton(ActionEvent event) {

        // If there's a img
        if (preview != null && preview.getImage() != null) {
            // Pass the image to the main window
            MainController.addImage(preview.getImage());

            // Close this window
            Node node = (Node) event.getSource();
            node.getScene().getWindow().hide();
        }
    }
}
