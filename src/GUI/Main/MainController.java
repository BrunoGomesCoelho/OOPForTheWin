package GUI.Main;

import GUI.CurrentImage;

import imageProcessing.Filters.Manipulate;
import imageProcessing.Models.ImageModel;

import static imageProcessing.utils.Utils.convertColorToAwt;
import static imageProcessing.utils.Utils.convertColorToScene;
import static imageProcessing.utils.Utils.colorSelect;
import static GUI.Main.Utils.resizePixel;
import static imageProcessing.draw.Brush.paint;
import static imageProcessing.draw.Brush.paintSquare;
import static imageProcessing.Filters.Manipulate.resize;

import imageProcessing.draw.Bucket;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;




/**
 * Class that implements the controllers for the Main window
 */
public class MainController implements Initializable{

    private static File currentFile = null;
    private static CurrentImage currentImage;

    @FXML private ImageView imageView;
    @FXML private javafx.scene.control.TextField rotateText;
    @FXML private ColorPicker colorPicker;
    @FXML private TextField largura;
	@FXML private TextField altura;


	private boolean brushCircleButtonOn;
	private boolean brushSquareButtonOn;
	private boolean bucketButtonOn;
	private boolean colorSelectorButtonOn;


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
        FileChooser fc = Utils.fileWindowOpen("Abrir imagen");

        // Search for an image
        File file = fc.showOpenDialog(null);

        // If the user select an image, show it
        if (file != null) {
            // For test purpose only TODO deletar
            System.out.println(file.getAbsolutePath());

            currentFile = file;
            currentImage.setImage(new Image(file.toURI().toString()));
            imageView.setImage(currentImage.getImage());
            imageView.setPickOnBounds(true); // Allows us to click on the borders of the image
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
        FileChooser fc = Utils.fileWindowSave("Salvar imagem");
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


    public static void addImage(Image image) {
        currentImage.setImage(image);
    }


    public void refresh() {
        if (currentImage.hasImage()) {
            imageView.setImage(currentImage.getImage());
        }
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



	public void resizeButton(ActionEvent event) {
    	if (!currentImage.hasImage()) // If we don't currently have a valid image, abort
    		return;

		ImageModel newModel, model = new ImageModel(SwingFXUtils.fromFXImage(currentImage.getImage(), null));
		int x, y;

		// Try reading the information from the user, if it fails we abort
		try {
			x = Integer.parseInt(largura.getText());
			y = Integer.parseInt(altura.getText());
		} catch (NumberFormatException e) {
			System.out.println("The user did not put in a valid angle, aborting");
			return;
		}

		newModel = resize(model, x, y);
		currentImage.setImage(SwingFXUtils.toFXImage(newModel.getBufferedImage(), null));
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

	public void undoButton(ActionEvent event) {
		currentImage.undo();
		refreshButton(event);
	}


	public void redoButton(ActionEvent event) {
		currentImage.redo();
		refreshButton(event);
	}


	public void rotateButton(ActionEvent event) {
		if (!currentImage.hasImage()) // If we don't currently have a valid image, abort
			return;

		ImageModel newModel, model = new ImageModel(SwingFXUtils.fromFXImage(currentImage.getImage(), null));
		String text = rotateText.getText();
		double angle, rad;

		// If the user has not writen a valid angle, abort
		try {
			angle = Double.parseDouble(text);
		} catch(NumberFormatException e) {
			System.out.println("The user did not put in a valid angle, aborting");
			return;
		}

		rad = (2*Math.PI * angle) / 360; // Converting the angle to radians

		newModel = Manipulate.rotate(model, rad);
		currentImage.setImage(SwingFXUtils.toFXImage(newModel.getBufferedImage(), null));
		refresh();
	}


    public void brushCircleButton() {
	    buttonsOff();
		brushCircleButtonOn = true;
	}

    public void brushSquareButton() {
	    buttonsOff();
		brushSquareButtonOn = true;
	}

	public void bucketButton() {
		buttonsOff();
		bucketButtonOn = true;
	}

	public void colorSelectorButton() {
		buttonsOff();
		colorSelectorButtonOn = true;
	}
	
	private void buttonsOff() {
		colorSelectorButtonOn = false;
		bucketButtonOn = false;
		brushSquareButtonOn = false;
		brushCircleButtonOn = false;
	}
	


    /*                          Image Clicked
    ====================================================================== */


	public void imageClicked(MouseEvent event) {
		if (! currentImage.hasImage()) // If theres no image on screen, abort
			return;

		else if (brushCircleButtonOn)
			brushCircle(event);
		else if (brushSquareButtonOn)
			brushSquare(event);
		else if (bucketButtonOn)
			bucket(event);
		else if (colorSelectorButtonOn)
			colorSelector(event);
	}

	private void colorSelector(MouseEvent event) {
		ImageModel model = new ImageModel(SwingFXUtils.fromFXImage(currentImage.getImage(), null));
		double[] imagePixels = resizePixel(imageView, event.getX(), event.getY());

		javafx.scene.paint.Color color = convertColorToAwt(colorSelect(model, (int) imagePixels[0], (int) imagePixels[1]));
		colorPicker.setValue(color);
	}


	private void brushSquare(MouseEvent event) {
		// Inicializing images and models
		ImageModel newModel, model = new ImageModel(SwingFXUtils.fromFXImage(currentImage.getImage(), null));

		// Brush information
		int brushSize = 10;
		Color color = convertColorToScene(colorPicker.getValue());

		double[] imagePixels = resizePixel(imageView, event.getX(), event.getY());

		newModel = paintSquare(model, (int) imagePixels[0], (int) imagePixels[1], brushSize, color);

		currentImage.setImage(SwingFXUtils.toFXImage(newModel.getBufferedImage(), null));
		refresh();
	}

	private void brushCircle(MouseEvent event) {
    	// Inicializing images and models
	    ImageModel newModel, model = new ImageModel(SwingFXUtils.fromFXImage(currentImage.getImage(), null));

	    // Brush information
	    int brushSize = 10;
		Color color = convertColorToScene(colorPicker.getValue());

		double[] imagePixels = resizePixel(imageView, event.getX(), event.getY());

	    newModel = paint(model, (int) imagePixels[0], (int) imagePixels[1], brushSize, color);
	    currentImage.setImage(SwingFXUtils.toFXImage(newModel.getBufferedImage(), null));
	    refresh();
    }

	private void bucket(MouseEvent event) {
		// Inicializing images and models
		ImageModel newModel, model = new ImageModel(SwingFXUtils.fromFXImage(currentImage.getImage(), null));

		// Bucket information
		int level = 6;
		Color color = convertColorToScene(colorPicker.getValue());

		double[] imagePixels = resizePixel(imageView, event.getX(), event.getY());

		newModel = Bucket.paint(model, (int) imagePixels[0], (int) imagePixels[1], color, level);
		currentImage.setImage(SwingFXUtils.toFXImage(newModel.getBufferedImage(), null));
		refresh();
	}

}
