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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        handleLaugInfo();
        
        // Closes the primary stage
        Stage stage = (Stage) btnGodkend.getScene().getWindow();
        stage.close();
    }
    
    private void handleLaugInfo()
    {
        String LaugName = txtLaugName.getText();
        String LaugInformation = txtLaugInfo.getText();
        
        Guild guild = new Guild(999999, LaugName);
        CreateNewLaug(guild);
    }
    
    private void CreateNewLaug(Guild guild)
    {
        dp.CreateNewLaug(guild);
    }

    @FXML
    private void handleAddVolunteer(ActionEvent event)
    {
    }

    @FXML
    private void handleRemoveVolunteer(ActionEvent event)
    {
    }
}
