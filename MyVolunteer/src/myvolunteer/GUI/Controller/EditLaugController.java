/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
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

    @FXML
    private Button btnGodkend;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtLaugInformation;
    @FXML
    private ListView<Volunteer> listAvailableVolunteers;
    @FXML
    private TextField txtSearchAvailable;
    @FXML
    private ListView<Volunteer> listChosenVolunteers;
    @FXML
    private TextField txtSearchChosen;
    @FXML
    private ComboBox<?> comboManager;
    @FXML
    private TextField txtLaugName;

    Guild guild;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        this.guild = mainViewModel.getLastSelectedGuild();
        loadInformation();
    }

    private void loadInformation()
    {
        this.txtLaugInformation.setText(guild.getDescription());
        this.txtLaugName.setText(guild.getName());
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnGodkend.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Laug", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void searchFilterAvailable(KeyEvent event)
    {
    }

    @FXML
    private void searchFilterChosen(KeyEvent event)
    {
    }

    @FXML
    private void handleDeleteLaug(ActionEvent event)
    {
    }

}
