package GUI.About;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by marcello on 06/06/17.
 */
public class AboutController implements Initializable {

    @FXML Label text;

    // There are about 77 chars per line
    private String about = "A photo edit software done by Bruno Coelho, " +
            "Gabriel Cyrillo, Gabriel Cruz and\n" +
            "Marcello Pagano\n\n" +
            "Done for the discipline OOP at the " +
            "University of São Paulo in June 2017";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setText(about);
    }

}
