package myvolunteer.GUI.Model;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myvolunteer.App;

/**
 *
 * @author jeppe
 */
public class MainViewModel
{

    /**
     * Ensures that the class can be used as a singleton, by making a static
     * instance of it, ensuring that the constructor is private, and having a
     * method that either returns the static instance if it exists, or makes a
     * new one.
     */
    private static MainViewModel instance;

    public static MainViewModel getInstance()
    {

        if (instance == null)
        {
            instance = new MainViewModel();
        }
        return instance;
    }

    private MainViewModel()
    {

    }

    public void changeView(String title, String path) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource(path));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
//        dialogStage.initOwner(stage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setTitle(title);

        //dialogStage.setOnCloseRequest(value);
        
        dialogStage.show();
    }

}
