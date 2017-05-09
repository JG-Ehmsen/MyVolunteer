package myvolunteer;

import java.io.IOException;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import myvolunteer.DAL.ConnectionManager;
import myvolunteer.DAL.DALFacade;

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

        primaryStage.setTitle("Laug");

        primaryStage.setScene(scene);
        primaryStage.show();

        Date date = new Date();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
