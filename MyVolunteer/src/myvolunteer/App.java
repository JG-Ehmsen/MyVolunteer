package myvolunteer;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import myvolunteer.DAL.ConnectionManager;

/**
 *
 * @author jeppe
 */
public class App extends Application
{

    public Window stage;

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/View/LaugView.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("View");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
