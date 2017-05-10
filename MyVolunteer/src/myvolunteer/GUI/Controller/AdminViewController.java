/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class AdminViewController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();

    @FXML
    private ComboBox<Guild> comboBoxGuild;
    @FXML
    private ListView<Volunteer> volunteerList;
    @FXML
    private TextField searchBar;
    @FXML
    private Button btnBack;
    @FXML
    private Button opretFrivillig;
    @FXML
    private Button opretLaug;
    @FXML
    private Button redigerFrivillig;
    @FXML
    private Button redigerLaug;
    @FXML
    private Label lblTovholder;
    @FXML
    private Label lblTotalGuildHours;
    @FXML
    private Label lblGuildVolunteers;
    @FXML
    private Label lblVolunteerNationality;
    @FXML
    private Label lblVolunteerName;
    @FXML
    private Label lblVolunteerPhoneNumber;
    @FXML
    private Label lblVolunteerEMail;
    @FXML
    private Label lblVolunteerHours;
    @FXML
    private Label lblVolunteerGender;
    @FXML
    private Label lblVolunteerAge;
    @FXML
    private Label lblVolunteerNote;

    private List<Guild> guildList = new ArrayList<>();
    private List<Volunteer> userList = new ArrayList<>();

    private Guild lastSelectedGuild;
    private Volunteer lastSelectedVolunteer;
    private Manager lastManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        guildList = dp.getGuilds();
        userList = dp.getUsers();
        comboContent();
        populateList();
    }

    private void populateList()
    {
        ObservableList<Volunteer> users = FXCollections.observableArrayList();
        if (lastSelectedGuild == null)
        {
            users.setAll(userList);
            //ObservableList<Volunteer> users = FXCollections.observableArrayList(userList);

        } else
        {
            List<Volunteer> guildUsers = new ArrayList<>();
            for (Integer i : lastSelectedGuild.getMemberList())
            {
                for (Volunteer user : userList)
                {
                    if (user.getId() == i)
                    {
                        guildUsers.add(user);
                    }
                }
            }
            users.setAll(guildUsers);
        }
        volunteerList.setItems(users);
    }

    private void comboContent()
    {
        ObservableList guilds = FXCollections.observableArrayList(guildList);
        comboBoxGuild.setItems(guilds);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Laug", "GUI/View/LaugView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleOpretFrivillig(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Opret frivillig", "GUI/View/AddVolunteer.fxml");

        // Closes the primary stage
        Stage stage = (Stage) opretFrivillig.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleOpretLaug(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Opret laug", "GUI/View/AddLaug.fxml");

        // Closes the primary stage
        Stage stage = (Stage) opretLaug.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleRedigerFrivillig(ActionEvent event) throws IOException
    {
        if (lastSelectedVolunteer != null)
        {
            mainViewModel.changeView("Rediger frivillig", "GUI/View/EditVolunteer.fxml");

            // Closes the primary stage
            Stage stage = (Stage) redigerFrivillig.getScene().getWindow();
            stage.close();
        } else
        {
            // Displays an alertbox if the user haven't selected a laug.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl");
            alert.setHeaderText(null);
            alert.setContentText("Vælg en frivillig");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRedigerLaug(ActionEvent event) throws IOException
    {
        if (lastSelectedGuild != null)
        {
            mainViewModel.changeView("Rediger Laug", "GUI/View/EditLaug.fxml");

            // Closes the primary stage
            Stage stage = (Stage) redigerLaug.getScene().getWindow();
            stage.close();
        } else
        {
            // Displays an alertbox if the user haven't selected a laug.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl");
            alert.setHeaderText(null);
            alert.setContentText("Vælg laug");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleComboClick(ActionEvent event)
    {
        lastSelectedGuild = comboBoxGuild.getSelectionModel().getSelectedItem();
        lastManager = dp.getManagerForGuild(lastSelectedGuild);
        showGuildInfo();
        mainViewModel.setLastSelectedGuild(lastSelectedGuild);
        populateList();
    }

    private void showGuildInfo()
    {
        lblGuildVolunteers.setText("Frivillige: " + Integer.toString(lastSelectedGuild.getMemberList().size()));
        lblTovholder.setText("Tovholder: " + lastManager.getFirstName() + " " + lastManager.getLastName());
        lblTotalGuildHours.setText("Total antal timer: " + Integer.toString(dp.getHoursWorkedForGuild(lastSelectedGuild)));
    }

    @FXML
    private void handleVolunteerlistClick(MouseEvent event)
    {
        if (volunteerList.getSelectionModel().getSelectedItem() != null)
        {
            lastSelectedVolunteer = volunteerList.getSelectionModel().getSelectedItem();
            loadVolunteerInfo();
            mainViewModel.setLastSelectedUser(lastSelectedVolunteer);
        }
    }

    private void loadVolunteerInfo()
    {
        lblVolunteerName.setText("Fulde navn: " + lastSelectedVolunteer.getFirstName() + " " + lastSelectedVolunteer.getLastName());
        lblVolunteerGender.setText("Køn: " + lastSelectedVolunteer.getGender());
        lblVolunteerAge.setText("Alder: ");
        lblVolunteerPhoneNumber.setText("Telefon: " + lastSelectedVolunteer.getPhoneNumber());
        lblVolunteerEMail.setText("Email: " + lastSelectedVolunteer.getEmail());
        lblVolunteerNationality.setText("Nationalitet: " + lastSelectedVolunteer.getNationality());
        lblVolunteerHours.setText("Timer: ");
        lblVolunteerNote.setText(lastSelectedVolunteer.getNote());
        lblVolunteerHours.setText("Timer: " + Integer.toString(dp.getHoursWorkedForVolunteer(lastSelectedVolunteer)));
    }

}
