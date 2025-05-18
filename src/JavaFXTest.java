// 不使用 package，請直接放在 src 目錄下
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class JavaFXTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Hello JavaFX!");
        Scene scene = new Scene(button, 300, 200);

        primaryStage.setTitle("JavaFX Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

