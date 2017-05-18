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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myvolunteer.BE.Manager;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class EditManagerController implements Initializable
{

    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    
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
    @FXML
    private ComboBox<Manager> comboTovholder;
    
    Manager manager;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        ObservableList manager = FXCollections.observableArrayList(dp.getManagers());
        comboTovholder.setItems(manager);
    }
    
    private void loadInfo()
    {
        txtFName.setText(manager.getFirstName());
        txtLName.setText(manager.getLastName());
        txtEmail.setText(manager.getEmail());
        txtPNumber.setText(manager.getPhoneNumber());
        
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Rediger frivillig");
            alert.setHeaderText("Du er ved at redigere en frivillig.");
            alert.setContentText("Tryk OK for at fortsætte.");

            ButtonType buttonTypeOK = new ButtonType("OK");
            ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOK)
            {
                editInfo();
                goBack();
            } else
            {
                alert.close();
            }
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

    @FXML
    private void handleComboClick(ActionEvent event)
    {
        manager = comboTovholder.getSelectionModel().getSelectedItem();
        
        loadInfo();
    }
    
    private void editInfo()
    {
        manager.setEmail(txtEmail.getText());
        manager.setFirstName(txtFName.getText());
        manager.setLastName(txtLName.getText());
        manager.setPhoneNumber(txtPNumber.getText());
        
        dp.UpdateManager(manager);
    }

}
