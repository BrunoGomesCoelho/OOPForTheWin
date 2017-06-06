package GUI.Main;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Photocyll");
        primaryStage.setScene(new Scene(root, 1280,  720));
        // primaryStage.setMaximized(true); inicializa a janela maximizada
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
