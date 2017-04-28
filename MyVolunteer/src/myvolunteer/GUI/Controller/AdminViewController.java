/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    @FXML
    private ComboBox<?> comboBoxGuild;
    @FXML
    private ListView<?> volunteerList;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
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
    }

    @FXML
    private void handleOpretLaug(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Opret laug", "GUI/View/AddLaug.fxml");
    }

    @FXML
    private void handleRedigerFrivillig(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Rediger frivillig", "GUI/View/EditVolunteer.fxml");
    }

    @FXML
    private void handleRedigerLaug(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Rediger Laug", "GUI/View/EditLaug.fxml");
    }

}
