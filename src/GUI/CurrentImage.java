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
    }

    public CurrentImage(Image image) {
        this.image = image;
        this.valid = true;
    }

    public Image getImage() {
        if (!valid)
            return null;
        return image;
    }

    public void setImage(Image image) {
        previous.add(this.image);
        this.image = image;
        this.valid = true;
    }

    //TODO: Testar a função undo
    /**
     * Returns the last image edited, similar to using "CTRL z".
     * @return Image: The last edited image if it exists, null otherwise
     */
    public Image undo() {
        Image temp;
        if (!valid)
            return null;
        try {
            temp = previous.remove(previous.size() - 1);
            this.image = temp;
            if (previous.size() == 0)
                valid = false;
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
        return temp;
    }

    public boolean hasImage() {
        return this.valid;
    }
}
