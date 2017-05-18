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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class EditLaugController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();

    @FXML
    private Button btnGodkend;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtLaugInformation;
    @FXML
    private ListView<Volunteer> listAvailableVolunteers;
    @FXML
    private ListView<Volunteer> listChosenVolunteers;
    @FXML
    private TextField txtSearchChosen;
    @FXML
    private ComboBox<Manager> comboManager;
    @FXML
    private TextField txtLaugName;
    @FXML
    private TextField txtSearchFilterAvailable;
    @FXML
    private Text lblAntalFrivillige;

    Guild guild;
    Manager manager;

    List<Volunteer> allVolunteerList = new ArrayList<>();
    List<Manager> managerList = new ArrayList<>();
    ObservableList<Volunteer> allUsers = FXCollections.observableArrayList();
    ObservableList<Volunteer> chosenUsers = FXCollections.observableArrayList();
    ObservableList<Manager> managers = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        guild = mainViewModel.getLastSelectedGuild();
        allVolunteerList = dp.getUsers();
        allUsers.setAll(allVolunteerList);
        listAvailableVolunteers.setItems(allUsers);
        listChosenVolunteers.setItems(chosenUsers);

        managerList = dp.getManagers();
        managers.setAll(managerList);
        comboManager.setItems(managers);
        setStartManager();

        loadInformation();
        initialSortLists();

        lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteers.getItems().size());
    }

    private void setStartManager()
    {
        manager = dp.getManagerForGuild(guild);
        guild.setOldManagerID(manager.getId());
        comboManager.getSelectionModel().select(manager);
    }

    private void loadInformation()
    {
        this.txtLaugInformation.setText(guild.getDescription());
        this.txtLaugName.setText(guild.getName());
    }

    private void initialSortLists()
    {
        for (Volunteer volunteer : allVolunteerList)
        {
            for (Integer i : guild.getMemberList())
            {
                if (i == volunteer.getId())
                {
                    chosenUsers.add(volunteer);
                    allUsers.remove(volunteer);
                }
            }
        }
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Rediger laug");
        alert.setHeaderText("Du er ved at redigere et laug.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            editGuild();
            goBack();
        } else
        {
            alert.close();
        }
    }

    private void editGuild()
    {
        guild.setDescription(txtLaugInformation.getText());
        guild.setName(txtLaugName.getText());

        dp.UpdateGuild(guild, manager);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        goBack();
    }

    @FXML
    private void searchFilterAvailable(KeyEvent event)
    {
        String filter = txtSearchFilterAvailable.getText();
        ObservableList<Volunteer> filteredList = FXCollections.observableArrayList();
        if (filter.equals(""))
        {
            listAvailableVolunteers.setItems(allUsers);
        } else
        {
            for (Volunteer vol : allUsers)
            {
                if (vol.toString().toLowerCase().contains(filter.toLowerCase()))
                {
                    filteredList.add(vol);
                }
            }
            listAvailableVolunteers.setItems(filteredList);
        }
    }

    @FXML
    private void searchFilterChosen(KeyEvent event)
    {
        String filter = txtSearchChosen.getText();
        ObservableList<Volunteer> filteredList = FXCollections.observableArrayList();
        if (filter.equals(""))
        {
            listChosenVolunteers.setItems(chosenUsers);
        } else
        {
            for (Volunteer vol : chosenUsers)
            {
                if (vol.toString().toLowerCase().contains(filter.toLowerCase()))
                {
                    filteredList.add(vol);
                }
            }
            listChosenVolunteers.setItems(filteredList);
        }
    }

    @FXML
    private void handleDeleteLaug(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Slet laug");
        alert.setHeaderText(null);
        alert.setContentText("Er du sikker på at du vil slette dette laug?");
        alert.showAndWait();
    }

    @FXML
    private void comboManager(ActionEvent event)
    {
        manager = comboManager.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void btnAddVolunteer(ActionEvent event)
    {
        addVolunteer();
    }

    private void addVolunteer()
    {
        if (listAvailableVolunteers.getSelectionModel().getSelectedItem() != null)
        {
            Volunteer volunteer = listAvailableVolunteers.getSelectionModel().getSelectedItem();
            chosenUsers.add(volunteer);
            allUsers.remove(volunteer);

            lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteers.getItems().size());
        }
    }

    @FXML
    private void btnRemoveVolunteer(ActionEvent event)
    {
        removeVolunteer();
    }

    private void removeVolunteer()
    {
        if (listChosenVolunteers.getSelectionModel().getSelectedItem() != null)
        {
            Volunteer volunteer = listChosenVolunteers.getSelectionModel().getSelectedItem();
            allUsers.add(volunteer);
            chosenUsers.remove(volunteer);

            lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteers.getItems().size());
        }
    }

    private void goBack() throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnGodkend.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void keyEventAvailable(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            addVolunteer();
        }
    }

    @FXML
    private void keyEventChosen(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER) || (event.getCode().equals(KeyCode.DELETE)))
        {
            removeVolunteer();
        }
    }

}
