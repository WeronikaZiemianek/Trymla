package checkers.gui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {
    FXMLLoader loader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/jfxView.fxml"));

        Controller controller = new Controller();
        loader.setController(controller);
        StackPane stackPane=loader.load();

        Scene scene = new Scene(stackPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("TRYLMA");

        primaryStage.show();
    }

    @Override
    public void init() {
    }

    @Override
    public void stop() {
        ((Controller)loader.getController()).stop();
     //   Platform.exit();
    }
}