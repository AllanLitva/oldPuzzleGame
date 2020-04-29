import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SliderApp extends Application {
    public void start(Stage primaryStage) {
        Time myPanel =new Time();
        primaryStage.setTitle("Slider Puzzle Game");
        primaryStage.setScene(new Scene(myPanel,968,771));
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}
