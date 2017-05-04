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
import myvolunteer.BE.User;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Utility.PictureButton;

public class VolunteerViewController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();

    @FXML
    private Button btnTest;
    @FXML
    private Button btnBack;
    @FXML
    private FlowPane MainFlowPane;

    Guild guild;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        guild = mainViewModel.getLastSelectedGuild();

        generateButtons();
    }

    private void generateButtons()
    {
        for (User u : dp.getUsers())
        {
            for (Integer i : guild.getMemberList())
            {
                if (u.getId() == i)
                {
                    if (u.getClass().equals(Volunteer.class))
                    {
                        PictureButton button = new PictureButton(u);
                        button.setOnAction(new EventHandler()
                        {
                            @Override
                            public void handle(Event event)
                            {
                                mainViewModel.setLastSelectedUser(button.getUser());
                                handleUserImage();

                            }
                        }
                        );
                        MainFlowPane.getChildren().add(button);
                    }
                }
            }

        }
    }

    @FXML
    private void handleUserImage()
    {
        try
        {
            mainViewModel.changeView("Indtast timer", "GUI/View/HoursView.fxml");

            // Closes the primary stage
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(VolunteerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
