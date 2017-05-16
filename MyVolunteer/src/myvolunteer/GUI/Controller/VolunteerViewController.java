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
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Utility.PictureButton;

public class VolunteerViewController implements Initializable
{

    /**
     * Gets the singleton instance of the model and dateParser.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();

    @FXML
    private Button btnBack;
    @FXML
    private FlowPane MainFlowPane;

    Guild guild;
    Manager manager;
    @FXML
    private Label guildNameLbl;
    @FXML
    private Label contactNameLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        guild = mainViewModel.getLastSelectedGuild();
        manager = dp.getManagerForGuild(guild);
        
        
        guildNameLbl.setText(guild.getName());
        contactNameLbl.setText("Kontakt Person \n" + manager.getFirstName() + " " + manager.getLastName()
        + "\n" + manager.getPhoneNumber() + "\n" + manager.getEmail());
        generateButtons();
    }

    private void generateButtons()
    {
        MainFlowPane.setVgap(20);
        MainFlowPane.setHgap(30);
        for (Volunteer u : dp.getUsers())
        {
            for (Integer i : guild.getMemberList())
            {
                if (u.getId() == i)
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
        mainViewModel.changeView("Laug", "GUI/View/LaugViewSpecial.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

}
