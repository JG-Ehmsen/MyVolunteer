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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class AdminLoginController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dataParserModel = DataParserModel.getInstance();

    private Stage previousStage;

    @FXML
    private TextField UsernameField;
    @FXML
    private TextField codeField;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        //checkFieldsContent();
        
        if(!UsernameField.getText().isEmpty())
        {
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            
            dataParserModel.tryLogin(UsernameField.getText(), codeField.getText(), stage);
        
        }
    }

    private void checkFieldsContent() throws IOException
    {
        if (UsernameField.getText().isEmpty() || codeField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl");
            alert.setHeaderText(null);
            alert.setContentText("Indtast brugernavn og kode");

            alert.showAndWait();
        } else
        {
            mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

            //previousStage.close();
            // Closes the primary stage
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();
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
