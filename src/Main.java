import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage; // **Declare static Stage**

    private static Parent root;
    private static FXMLLoader load = new FXMLLoader();

    static public FXMLLoader getFXMLLoader() {
        return Main.load;
    }

    private static void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        Parent root = load.load(getClass().getResource("StartMenu.fxml"));

        primaryStage.setTitle("Blip-Blop");
        primaryStage.setScene(new Scene(root,900, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
