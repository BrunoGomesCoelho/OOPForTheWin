package GUI;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class that mantains the current image being edited in memory.
 */
public class CurrentImage {
    Image image = null;
    boolean set = false;
    ArrayList<Image> previous = null;
    int count = 0;

    public CurrentImage(Image image) {
        this.image = image;
        set = true;
    }

    public Image getImage() {
        if (!set)
            throw new RuntimeException("Deu ruim, imagem não setada\n");
            // TODO: Colocar aqui um tipo de exceção criada por nós
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
        if (!set)
            return null;
        try {
            temp = previous.remove(previous.size() - 1);
            if (previous.size() == 0)
                set = false;
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
        return temp;
    }

}
