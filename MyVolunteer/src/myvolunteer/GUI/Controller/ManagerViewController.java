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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author Fjord82
 */
public class ManagerViewController implements Initializable
{

    @FXML
    private ComboBox<Guild> comboBoxGuild;
    @FXML
    private ListView<Volunteer> volunteerList;
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
    private TextField searchBar;
    @FXML
    private Button opretFrivillig;
    @FXML
    private Button redigerFrivillig;
    @FXML
    private Button redigerLaug;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblGuildNote;

    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    private List<Volunteer> managerForUserList = new ArrayList<>();
    ObservableList<Volunteer> users = FXCollections.observableArrayList();
    
    private Guild lastSelectedGuild;
    
    private List<Guild> managerGuildList = new ArrayList<>();
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        managerGuildList = dp.getGuildForManager(mainViewModel.getLoggedInManager());        
        comboContent(); 
        populateList();
    } 
    
    private void populateList()
    {
       
    }
    
    private void comboContent()
    {
        ObservableList guilds = FXCollections.observableArrayList(managerGuildList);
        comboBoxGuild.setItems(guilds);
    }

    @FXML
    private void handleComboClick(ActionEvent event)
    {
    }

    @FXML
    private void handleVolunteerlistClick(MouseEvent event)
    {
    }

    @FXML
    private void handleListKeyboard(KeyEvent event)
    {
    }

    @FXML
    private void searchFilter(KeyEvent event)
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

    @FXML
    private void handleOpretFrivillig(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Opret frivillig", "GUI/View/AddVolunteer.fxml");

        // Closes the primary stage
        Stage stage = (Stage) opretFrivillig.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleRedigerFrivillig(ActionEvent event)
    {
    }

    @FXML
    private void handleRedigerLaug(ActionEvent event)
    {
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
