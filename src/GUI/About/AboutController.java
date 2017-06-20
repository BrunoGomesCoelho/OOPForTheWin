package GUI.About;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that implements the controllers for the "About Page" window
 */
public class AboutController implements Initializable {

    @FXML Label text;
    @FXML Label copyright_text;

    // There are about 77 chars per line
    private String about = "A photo edit software done by Bruno Coelho, " +
            "Gabriel Cyrillo, Gabriel Cruz and\n" +
            "Marcello Pagano\n\n" +
            "Done for the discipline OOP at the " +
            "University of São Paulo in June 2017";

    private String copy = " Permission is granted to copy, distribute and/or modify this document\n" +
            "    under the terms of the GNU Free Documentation License, Version 1.3\n" +
            "    or any later version published by the Free Software Foundation;\n" +
            "    with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.\n" +
            "    A copy of the license is included in the section entitled \"GNU\n" +
            "    Free Documentation License\"";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setText(about);
            copyright_text.setText(copy);
    }

}
