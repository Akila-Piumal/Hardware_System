import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/LoginForm.fxml"))));
        //primaryStage.setMaximized(true);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println("Height: " + screenBounds.getHeight() + " Width: " + screenBounds.getWidth());
        primaryStage.setTitle("Jinasiri Hardware");
        primaryStage.show();
    }
}
