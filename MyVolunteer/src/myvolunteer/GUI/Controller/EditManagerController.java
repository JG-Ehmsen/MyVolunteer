/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class EditManagerController implements Initializable
{

    MainViewModel mainViewModel = MainViewModel.getInstance();

    @FXML
    private Button btnBack;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtPNumber;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnUploadImage;
    @FXML
    private Button btnGodkend;
    @FXML
    private Button btnSletTovholder;
    @FXML
    private TextField txtPassword;

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
        goBack();
    }

    private void goBack() throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
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
    private void handleSletTovholder(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Slet tovholder");
        alert.setHeaderText("Du er ved at fjerne en tovholder.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            // IMPLEMENT DELETE
            goBack();
        } else
        {
            alert.close();
        }
    }

}
