package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Class that mantains the current image being edited in memory.
 * Also keeps two ArrayLists that allow the user undo/redo their last edited image.
 */
public class CurrentImage {
    private Image image = null;
    private int maxMemory = 50;
    private boolean valid = false;
    private boolean undoDone = false;
    private ArrayList<Image> previous = new ArrayList<>();
    private ArrayList<Image> next =  new ArrayList<>();
    private ImageView imageView;


    public CurrentImage() {
        this.image = null;
        this.valid = false;
        this.previous.add(null);
    }


    public CurrentImage(Image image) {
        this.image = image;
        this.valid = true;
        this.previous.add(null);
        this.imageView = new ImageView(image);
    }


    /**
     * Gets the current image being displayed
     * @return Image: The image being displayed, null if there's no image.
     */
    public Image getImage() {
        if (!valid) {
            System.out.println("No valid image");
            return null;
        }
        return image;
    }


    /**
     * Sets the current Main image to be displayed.
     * @param image: the image to be displayed
     */
    public void setImage(Image image) {
        if (this.undoDone)   // If the user makes a change after undoing something, destroy the redo list
            this.next = new ArrayList<>();
        // Since we have a limited heap, we can not always store all the edits in risk of stack overflow.
        // In this case, we remove the last edit and store the new image in its place.
        if (this.previous.size() == this.maxMemory)
            this.previous.add(this.maxMemory - 1, this.image);
        else
            this.previous.add(this.image);
        this.image = image;
        this.valid = true;
    }


    /**
     * Undo the last edit done to a image, similar to using "CTRL z" in most programs.
     */
    public void undo() {
        Image temp;
        if (!valid || this.previous.size() == 1) // If the image isnt valid or theres only the "nulL" image stored
            return;
        try {
            temp = this.previous.remove(this.previous.size() - 1); // Removing it from the undo list
            next.add(this.image); // We add it to the redo list
            this.image = temp;
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e);
            this.valid = false;
        }
    }


    /**
     * Redo the last edit done to a image, similar to using "CTRL SHIFT z" in most programs.
     */
    public void redo() {
        Image temp;
        try {
            temp = this.next.remove(this.next.size() - 1);
            this.previous.add(this.image);
            this.image = temp;
            undoDone = true;
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e);
        }
    }


    /**
     * Check if the current image being displayed is valid (not null).
     */
    public boolean hasImage() {
        return this.valid;
    }


    /**
     * Gets a ImageView with the current image
     * @return: the ImageView
     */
    public ImageView getImageView() {
        return imageView;
    }

    private void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
