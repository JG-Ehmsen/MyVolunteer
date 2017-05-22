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
import myvolunteer.BE.Manager;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

public class AddManagerController implements Initializable
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
    private TextField txtPassword;
    @FXML
    private TextField txtPNumber2;
    @FXML
    private TextField txtPNumber3;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddress2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        if (!txtEmail.getText().isEmpty() && !txtFName.getText().isEmpty() && !txtLName.getText().isEmpty() && !txtPassword.getText().isEmpty())
        {
            mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

            handleUserInfo();

            // Closes the primary stage
            Stage stage = (Stage) btnGodkend.getScene().getWindow();
            stage.close();
        } else if (txtEmail.getText().isEmpty() || txtFName.getText().isEmpty() || txtLName.getText().isEmpty() || txtPassword.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fejl");
            alert.setContentText("Udfyld venligst de markerede felter");

            alert.showAndWait();
        }
    }

    private void handleUserInfo()
    {
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String pNumber = txtPNumber.getText();
        String pNumber2 = txtPNumber2.getText();
        String pNumber3 = txtPNumber3.getText();
        String address = txtAddress.getText();
        String address2 = txtAddress2.getText();
        String eMail = txtEmail.getText();

        String password = txtPassword.getText();

        Manager manager = new Manager(999999);

        manager.setFirstName(fName);
        manager.setLastName(lName);
        manager.setPhoneNumber(pNumber);
        manager.setPhoneNumber2(pNumber2);
        manager.setPhoneNumber3(pNumber3);
        manager.setAddress(address);
        manager.setAddress2(address2);
        manager.setEmail(eMail);

        CreateNewManager(manager, password);
    }

    private void CreateNewManager(Manager manager, String password)
    {
        dp.CreateNewManager(manager, password);
    }

}
