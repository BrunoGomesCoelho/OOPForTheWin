package GUI;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class that mantains the current image being edited in memory.
 */
public class CurrentImage {
    private Image image = null;
    private boolean valid = false;
    private ArrayList<Image> previous = new ArrayList<>();

    public CurrentImage() {
        this.image = null;
        this.valid = false;
        previous.add(null);
    }

    public CurrentImage(Image image) {
        this.image = image;
        this.valid = true;
        previous.add(null);
    }

    public Image getImage() {
        if (!valid) {
            System.out.println("No valid image");
            return null;
        }
        return image;
    }

    public void setImage(Image image) {
        if (this.valid)
            previous.add(this.image);
        this.image = image;
        this.valid = true;
    }

    //TODO: Testar a função undo
    /**
     * Returns the last image edited, similar to using "CTRL z".
     */
    public void undo() {
        Image temp;
        if (!valid)
            return;
        try {
            temp = previous.remove(previous.size() - 1);
            this.image = temp;
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e);
        }
    }

    public boolean hasImage() {
        return this.valid;
    }
}
