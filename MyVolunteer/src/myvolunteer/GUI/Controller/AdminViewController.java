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
import myvolunteer.GUI.Model.ViewChangerModel;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class AdminViewController implements Initializable
{

    /**
     * Gets the singleton instance of the MainViewModel, DateParser and
     * ViewChangerModel.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

    @FXML
    private ComboBox<Guild> comboBoxGuild;
    @FXML
    private ListView<Volunteer> volunteerList;
    @FXML
    private TextField searchBar;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnCreateVolunteer;
    @FXML
    private Button btnCreateLaug;
    @FXML
    private Button btnEditVolunteer;
    @FXML
    private Button btnEditLaug;
    @FXML
    private Label lblManager;
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
    private Button btnEditManager;
    @FXML
    private Button btnInfo;
    @FXML
    private Button btnCreateManager;
    @FXML
    private Button btnAllLaug;
    @FXML
    private Label lblVolunteerPhoneNumber2;
    @FXML
    private Label lblVolunteerPhoneNumber3;
    @FXML
    private Label lblVolunteerAddress;
    @FXML
    private Label lblVolunteerAddress2;
    @FXML
    private Label lblAllVolunteers;
    @FXML
    private Label lblLastUpdated;

    private List<Guild> guildList = new ArrayList<>();
    private List<Volunteer> userList = new ArrayList<>();
    ObservableList<Volunteer> users = FXCollections.observableArrayList();

    private Guild lastSelectedGuild;
    private Volunteer lastSelectedVolunteer;
    private Manager lastManager;
    String allVolunteers = Integer.toString(dp.getAllUsers().size());

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        guildList = dp.getAllGuilds();
        userList = dp.getAllUsers();
        comboContent();
        populateList();
    }

    private void populateList()
    {
        lblAllVolunteers.setText("Total antal frivillige: " + allVolunteers);
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
        volunteerList.setItems(dp.filter(filter, userList));

    }

    private void comboContent()
    {
        ObservableList guilds = FXCollections.observableArrayList(guildList);
        comboBoxGuild.setItems(guilds);
    }

    @FXML
    private void handleGoBack(ActionEvent event) throws IOException
    {
        vcm.showLaugSelectionView((Stage) btnBack.getScene().getWindow());
    }

    @FXML
    private void handleGoToCreateVolunteer(ActionEvent event) throws IOException
    {
        vcm.showCreateVolunteerView((Stage) btnCreateVolunteer.getScene().getWindow());
    }

    @FXML
    private void handleGoToCreateLaug(ActionEvent event) throws IOException
    {
        vcm.showCreateLaugView((Stage) btnCreateLaug.getScene().getWindow());
    }

    @FXML
    private void handleGoToEditVolunteer(ActionEvent event) throws IOException
    {
        if (lastSelectedVolunteer != null)
        {
            mainViewModel.setLastSelectedUser(lastSelectedVolunteer);
            vcm.showEditVolunteerView((Stage) btnEditVolunteer.getScene().getWindow());
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
    private void handleGoToEditLaug(ActionEvent event) throws IOException
    {
        if (lastSelectedGuild != null)
        {
            mainViewModel.setLastSelectedGuild(lastSelectedGuild);
            vcm.showEditLaugView((Stage) btnEditLaug.getScene().getWindow());
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
        lblManager.setText("Tovholder: " + lastManager.getFirstName() + " " + lastManager.getLastName());
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
        lblVolunteerPhoneNumber2.setText("Telefon 2: ");
        lblVolunteerPhoneNumber3.setText("Telefon 3: ");
        lblVolunteerAddress.setText("Adresse: ");
        lblVolunteerAddress2.setText("Adresse 2: ");
        lblVolunteerEMail.setText("Email: ");
        lblVolunteerNationality.setText("Nationalitet: ");
        lblVolunteerHours.setText("Timer: ");
        lblVolunteerNote.setText("");
        lblLastUpdated.setText("Sidst opdateret: ");
    }

    private void clearGuildInfo()
    {
        lblGuildVolunteers.setText("Frivillige: ");
        lblManager.setText("Tovholder: ");
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
        String phone2;
        if (lastSelectedVolunteer.getPhoneNumber2() == null)
        {
            phone2 = "";
        } else
        {
            phone2 = lastSelectedVolunteer.getPhoneNumber2();
        }

        String phone3;
        if (lastSelectedVolunteer.getPhoneNumber3() == null)
        {
            phone3 = "";
        } else
        {
            phone3 = lastSelectedVolunteer.getPhoneNumber3();
        }

        String address;
        if (lastSelectedVolunteer.getAddress() == null)
        {
            address = "";
        } else
        {
            address = lastSelectedVolunteer.getAddress();
        }

        String address2;
        if (lastSelectedVolunteer.getAddress2() == null)
        {
            address2 = "";
        } else
        {
            address2 = lastSelectedVolunteer.getAddress2();
        }

        lblVolunteerName.setText("Fulde navn: " + lastSelectedVolunteer.getFirstName() + " " + lastSelectedVolunteer.getLastName());
        lblVolunteerGender.setText("Køn: " + lastSelectedVolunteer.getGender());
        lblVolunteerAge.setText("Alder: ");
        lblVolunteerPhoneNumber.setText("Telefon: " + lastSelectedVolunteer.getPhoneNumber());
        lblVolunteerPhoneNumber2.setText("Telefon 2: " + phone2);
        lblVolunteerPhoneNumber3.setText("Telefon 3: " + phone3);
        lblVolunteerAddress.setText("Adresse: " + address);
        lblVolunteerAddress2.setText("Adresse 2: " + address2);
        lblVolunteerEMail.setText("Email: " + lastSelectedVolunteer.getEmail());
        lblVolunteerNationality.setText("Nationalitet: " + lastSelectedVolunteer.getNationality());
        lblVolunteerNote.setText(lastSelectedVolunteer.getNote());
        lblVolunteerHours.setText("Timer: " + Integer.toString(dp.getHoursWorkedForVolunteer(lastSelectedVolunteer)));
        lblLastUpdated.setText("Sidst opdateret: " + lastSelectedVolunteer.getLastInputDate());
    }

    @FXML
    private void handleListKeyboard(KeyEvent event)
    {
        updateVolunteerInfo();
    }

    @FXML
    private void handleGoToInfo(ActionEvent event) throws IOException
    {
        vcm.showInformationView((Stage) btnInfo.getScene().getWindow());
    }

    @FXML
    private void handleGoToCreateManager(ActionEvent event) throws IOException
    {
        vcm.showCreateManagerView((Stage) btnCreateManager.getScene().getWindow());
    }

    @FXML
    private void handleGoToEditManager(ActionEvent event) throws IOException
    {
        vcm.showEditManagerView((Stage) btnEditManager.getScene().getWindow());
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
        clearVolunteerInfo();
        lastSelectedGuild = comboBoxGuild.getSelectionModel().getSelectedItem();

        if (lastSelectedGuild != null)
        {
            lastManager = dp.getManagerForGuild(lastSelectedGuild);
            showGuildInfo();
            populateList();
        }
    }
}
