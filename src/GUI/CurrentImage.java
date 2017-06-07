package GUI;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class that mantains the current image being edited in memory.
 */
public class CurrentImage {
    Image image = null;
    boolean valid = false;
    ArrayList<Image> previous = null;
    int count = 0;

    public CurrentImage() {
        this.image = null;
        this.valid = false;
    }

    public CurrentImage(Image image) {
        this.image = image;
        valid = true;
    }

    public Image getImage() {
        if (!valid)
            return null;
        return image;
    }

    public void setImage(Image image) {
        previous.add(this.image);
        this.image = image;
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
