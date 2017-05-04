package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Utility.PictureButton;

/**
 * FXML Controller class
 *
 * @author jeppe
 */
public class LaugViewController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();

    @FXML
    private Button btnLogin;
    @FXML
    private Button ingelise;
    @FXML
    private FlowPane MainFlowPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        for (Guild g : dp.getGuilds())
        {
            PictureButton b = new PictureButton(g);
            MainFlowPane.getChildren().add(b);
        }
    }

    @FXML
    private void handleIngelise(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Frivillig", "GUI/View/VolunteerView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) ingelise.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin login", "GUI/View/AdminLogin.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

}
