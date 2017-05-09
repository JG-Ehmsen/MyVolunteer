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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

public class AddLaugController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();

    @FXML
    private Button btnGodkend;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private TextField txtLaugName;
    @FXML
    private TextField txtLaugInfo;

    DataParserModel dp = DataParserModel.getInstance();
    @FXML
    private Button btnBack;
    private ListView<Volunteer> listAvailableVolunteers;
    @FXML
    private TextField txtSearchAvailable;
    @FXML
    private ListView<Volunteer> listChosenVolunteer;
    @FXML
    private TextField txtSeachChosen;

    List<Volunteer> allVolunteerList = new ArrayList<>();
    List<Manager> managerList = new ArrayList<>();
    ObservableList allUsers = FXCollections.observableArrayList();
    ObservableList chosenUsers = FXCollections.observableArrayList();
    ObservableList managers = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Manager> comboManager;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        allVolunteerList = dp.getUsers();
        allUsers.setAll(allVolunteerList);
        listAvailableVolunteers.setItems(allUsers);
        listChosenVolunteer.setItems(chosenUsers);
        managerList = dp.getManagers();
        managers.setAll(managerList);
        comboManager.setItems(managers);
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        handleLaugInfo();

        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnGodkend.getScene().getWindow();
        stage.close();
    }

    private void handleLaugInfo()
    {
        String LaugName = txtLaugName.getText();
        String LaugInformation = txtLaugInfo.getText();

        Guild guild = new Guild(999999, LaugName);
        List<Integer> userID = new ArrayList();
        for (Volunteer volunteer : listChosenVolunteer.getItems())
        {
            userID.add(volunteer.getId());
        }
        userID.add(comboManager.getSelectionModel().getSelectedItem().getId());
        guild.setMemberList(userID);
        guild.setDescription(txtLaugInfo.getText());
        CreateNewLaug(guild);
    }

    private void CreateNewLaug(Guild guild)
    {
        dp.CreateNewLaug(guild);
    }

    @FXML
    private void handleAddVolunteer(ActionEvent event)
    {
        if(listAvailableVolunteers.getSelectionModel().getSelectedItem()!= null)
        {
            Volunteer volunteer = listAvailableVolunteers.getSelectionModel().getSelectedItem();
            chosenUsers.add(volunteer);
            allUsers.remove(volunteer);
        }
    }

    @FXML
    private void handleRemoveVolunteer(ActionEvent event)
    {
        if(listChosenVolunteer.getSelectionModel().getSelectedItem()!= null)
        {
            Volunteer volunteer = listChosenVolunteer.getSelectionModel().getSelectedItem();
            allUsers.add(volunteer);
            chosenUsers.remove(volunteer);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Laug", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
}
