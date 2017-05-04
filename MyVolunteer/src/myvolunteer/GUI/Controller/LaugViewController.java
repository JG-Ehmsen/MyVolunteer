package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
            b.setOnAction(new EventHandler()
            {
                @Override
                public void handle(Event event)
                {
                    mainViewModel.setLastSelectedGuild(b.getGuild());
                    handleGuildClick();
                }
            }
            );
            MainFlowPane.getChildren().add(b);
        }
    }

    @FXML
    private void handleGuildClick()
    {
        try
        {

            mainViewModel.changeView("Frivillig", "GUI/View/VolunteerView.fxml");

            // Closes the primary stage
            Stage stage = (Stage) MainFlowPane.getScene().getWindow();
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(LaugViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin login", "GUI/View/AdminLogin.fxml");

        // Closes the primary stage
        Stage stage = (Stage) MainFlowPane.getScene().getWindow();
        stage.close();
    }

}
