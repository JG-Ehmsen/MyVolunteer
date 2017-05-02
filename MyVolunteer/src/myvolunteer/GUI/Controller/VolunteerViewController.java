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
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Utility.PictureButton;

public class VolunteerViewController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();

    @FXML
    private Button btnTest;
    @FXML
    private Button btnBack;
    @FXML
    private FlowPane MainFlowPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Volunteer user = new Volunteer(10, "Jonas", "11111111");
        PictureButton button = new PictureButton(user);

        MainFlowPane.getChildren().add(button);

    }

    @FXML
    private void handleUserImage(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Indtast timer", "GUI/View/HoursView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Laug", "GUI/View/LaugView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

}
