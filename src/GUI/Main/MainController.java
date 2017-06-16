package GUI.Main;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import GUI.CurrentImage;

import javax.imageio.ImageIO;

/**
 * Class that implements the controllers for the Main window
 */
public class MainController implements Initializable{

    private static File currentFile = null;
    private static CurrentImage currentImage;

    @FXML private ImageView imageview;


    /**
     *  Gets the image being displayed
     *
     *  @return The current image
     */
    public static Image getImage() {
        return currentImage.getImage();
    }


    /**
     * Set the image to a new one
     *
     * @param img A new image to be displayed
     */
    public static void setImage(Image img) {
        currentImage.setImage(img);
    }


    public static void addImage(Image image) {
        currentImage.setImage(image);
    }


    public void refresh() {
        if (currentImage.hasImage()) {
            imageview.setImage(currentImage.getImage());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This method is called before the window show up.
        // Use when it's necessary
        currentImage = new CurrentImage();
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
     * Open the image and show it at the Main window.
     * Linked to File -> Open button at menu bar
     *
     * @param event: the button being pressed
     */
    public void openButton(ActionEvent event) {
        // Set up the file chooser
        FileChooser fc = Utils.fileWindow("Abrir imagen");

        // Search for an image
        File file = fc.showOpenDialog(null);

        // If the user select an image, show it
        if (file != null) {
            // For test purpose only TODO deletar
            System.out.println(file.getAbsolutePath());

            currentFile = file;
            currentImage.setImage(new Image(file.toURI().toString()));
            imageview.setImage(currentImage.getImage());
        }
    }

    /**
     * Saves the image with it's current name
     * Linked to File -> Save button at the menu bar
     *
     * @param event: the "save" button being pressed
     */
    public void saveButton(ActionEvent event) {
        if (currentFile == null || currentImage.getImage() == null)
            return;
        String extension = Utils.fileExtension(currentFile);

        // TODO: Testar isso depois de termos algo que edite a imagem
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(currentImage.getImage(),
                    null), extension, currentFile);
        } catch (IOException ex) {
            // TODO; essa é a melhor maneira de ignorar isso...?
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Opens a window to save the image with the file manager
     * Linked to File -> Save As button at the menu bar
     *
     * @param event: the "save as" button being pressed
     */
    public void saveAsButton(ActionEvent event) {
        FileChooser fc = Utils.fileWindow("Salvar imagem");
        File file = fc.showSaveDialog(null);

        if (file != null && currentImage.getImage() != null) {
            String extension = Utils.fileExtension(file);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(currentImage.getImage(),
                        null), extension, file);
            } catch (IOException ex) {
                // TODO; essa é a melhor maneira de ignorar isso...?
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Closes the application completely.
     * Linked to File -> Close button at the menu bar
     *
     * @param event: the close button being pressed
     */
    public void closeAplication(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }


    // Edit ==================================================================

    public void undoButton(ActionEvent event) {
        currentImage.undo();
        refreshButton(event);
    }


    public void redoButton(ActionEvent event) {
        currentImage.redo();
        refreshButton(event);
    }
    /**
     * Method to refresh the image after some filter/tool has modified it
     * Linked to Edit -> Refresh button at the menu bar
     *
     * @param event: the "refresh" button being pressed
     */
    public void refreshButton(ActionEvent event) {
        refresh();
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
        filterStage.showAndWait();
        System.out.println("hmmmm");
        refresh();
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

    public void ColorPicker(MouseEvent event) {
        /* TODO: Fazer algo com isso.
         Por enquanto só imprime no console. Importante resaltar que ele pega a posição mesmo se nenhuma imagem
            estiver na tela, contanto que esteja dentro do quadrado do imageView
          */
        System.out.println("["+event.getX()+", "+event.getY()+"]");
    }


    // Image ==================================================================


}
