package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    @FXML
    ListView listView;

    public void pressButon(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = fc.showOpenDialog(null);

        if (file != null) {
            listView.getItems().addAll(file.getName());
            System.out.println(file.getAbsolutePath());
        }
        else
            System.out.println("Nao consegui abrir o arquivo");
    }
}
