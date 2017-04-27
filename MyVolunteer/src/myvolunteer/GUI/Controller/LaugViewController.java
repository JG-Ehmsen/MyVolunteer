package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import myvolunteer.GUI.Model.MainViewModel;

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

    @FXML
    private Button btnLogin;
    @FXML
    private Button ingelise;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void handleIngelise(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Volunteer", "GUI/View/VolunteerView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) ingelise.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminLogin.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

}
