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
import javafx.scene.input.KeyEvent;
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
    @FXML
    private Label lblGuildNote;
    @FXML
    private Button btnRedigerTovholder;
    @FXML
    private Button btnInfo;
    @FXML
    private Button btnOpretTovholder;
    @FXML
    private Button btnAllLaug;

    private List<Guild> guildList = new ArrayList<>();
    private List<Volunteer> userList = new ArrayList<>();
    ObservableList<Volunteer> users = FXCollections.observableArrayList();

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
        guildList = dp.getActiveGuilds();
        userList = dp.getAllUsers();
        comboContent();
        populateList();
    }

    private void populateList()
    {
        if (lastSelectedGuild == null)
        {
            users.setAll(userList);

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

    @FXML
    private void searchFilter()
    {
        String filter = searchBar.getText();
        ObservableList<Volunteer> filteredList = FXCollections.observableArrayList();
        if (filter.equals(""))
        {
            volunteerList.setItems(users);
        } else
        {
            for (Volunteer vol : users)
            {
                if (vol.toString().toLowerCase().contains(filter.toLowerCase()))
                {
                    filteredList.add(vol);
                }
            }
            volunteerList.setItems(filteredList);
        }
    }

    private void comboContent()
    {
        ObservableList guilds = FXCollections.observableArrayList(guildList);
        comboBoxGuild.setItems(guilds);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Laug", "GUI/View/LaugViewSpecial.fxml");

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
            mainViewModel.setLastSelectedUser(lastSelectedVolunteer);
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
            mainViewModel.setLastSelectedGuild(lastSelectedGuild);
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
            alert.setContentText("Vælg et laug");
            alert.showAndWait();
        }
    }

    private void showGuildInfo()
    {
        lblGuildVolunteers.setText("Frivillige: " + Integer.toString(lastSelectedGuild.getMemberList().size()));
        lblTovholder.setText("Tovholder: " + lastManager.getFirstName() + " " + lastManager.getLastName());
        lblTotalGuildHours.setText("Total antal timer: " + Integer.toString(dp.getHoursWorkedForGuild(lastSelectedGuild)));
        lblGuildNote.setText("Note: " + lastSelectedGuild.getDescription().toString());
    }

    @FXML
    private void handleVolunteerlistClick(MouseEvent event)
    {
        updateVolunteerInfo();
    }

    private void updateVolunteerInfo()
    {
        if (volunteerList.getSelectionModel().getSelectedItem() != null)
        {
            lastSelectedVolunteer = volunteerList.getSelectionModel().getSelectedItem();
            loadVolunteerInfo();
        }
    }

    private void clearVolunteerInfo()
    {
        lblVolunteerName.setText("Fulde navn: ");
        lblVolunteerGender.setText("Køn: ");
        lblVolunteerAge.setText("Alder: ");
        lblVolunteerPhoneNumber.setText("Telefon: ");
        lblVolunteerEMail.setText("Email: ");
        lblVolunteerNationality.setText("Nationalitet: ");
        lblVolunteerHours.setText("Timer: ");
        lblVolunteerNote.setText("");
    }

    private void clearGuildInfo()
    {
        lblGuildVolunteers.setText("Frivillige: ");
        lblTovholder.setText("Tovholder: ");
        lblTotalGuildHours.setText("Total antal timer: ");
        lblGuildNote.setText("Note: ");
    }

    private void clearInfo()
    {
        clearGuildInfo();
        clearVolunteerInfo();
    }

    private void loadVolunteerInfo()
    {
        lblVolunteerName.setText("Fulde navn: " + lastSelectedVolunteer.getFirstName() + " " + lastSelectedVolunteer.getLastName());
        lblVolunteerGender.setText("Køn: " + lastSelectedVolunteer.getGender());
        lblVolunteerAge.setText("Alder: ");
        lblVolunteerPhoneNumber.setText("Telefon: " + lastSelectedVolunteer.getPhoneNumber());
        lblVolunteerEMail.setText("Email: " + lastSelectedVolunteer.getEmail());
        lblVolunteerNationality.setText("Nationalitet: " + lastSelectedVolunteer.getNationality());
        lblVolunteerNote.setText(lastSelectedVolunteer.getNote());
        lblVolunteerHours.setText("Timer: " + Integer.toString(dp.getHoursWorkedForVolunteer(lastSelectedVolunteer)));
    }

    @FXML
    private void handleListKeyboard(KeyEvent event)
    {
        updateVolunteerInfo();
    }

    @FXML
    private void handleInfo(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Information", "GUI/View/AdminInfoView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnInfo.getScene().getWindow();
    }

    @FXML
    private void handleOpretTovholder(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Opret tovholder", "GUI/View/AddManager.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnOpretTovholder.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleRedigerTovholder(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Rediger tovholder", "GUI/View/EditManager.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnRedigerTovholder.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAllLaug(ActionEvent event)
    {
        comboBoxGuild.getItems().clear();
        comboContent();
        populateList();
        clearInfo();
    }

    @FXML
    private void handleComboClick(ActionEvent event)
    {
        lastSelectedGuild = comboBoxGuild.getSelectionModel().getSelectedItem();

        if (lastSelectedGuild != null)
        {
            lastManager = dp.getManagerForGuild(lastSelectedGuild);
            showGuildInfo();
            populateList();
        }

    }

}
