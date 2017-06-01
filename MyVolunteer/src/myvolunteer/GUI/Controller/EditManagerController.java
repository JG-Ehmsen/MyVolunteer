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
import myvolunteer.GUI.Model.ViewChangerModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class EditManagerController implements Initializable
{

    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

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
    private Button btnApprove;
    @FXML
    private TextField txtPassword;
    @FXML
    private ComboBox<Manager> cbBoxManager;

    Manager manager;
    Manager managers;
    @FXML
    private Button btnChangeStatus;
    @FXML
    private TextField txtPNumber2;
    @FXML
    private TextField txtPNumber3;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddress2;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        managers = mainViewModel.getLoggedInManager();
        ObservableList manager = FXCollections.observableArrayList(dp.getAllManagers());
        cbBoxManager.setItems(manager);

        if (managers.isIsActive())
        {
            btnChangeStatus.setText("Gør aktiv");
        } else if (!managers.isIsActive())
        {
            btnChangeStatus.setText("Gør inaktiv");
        }
    }

    private void loadInfo()
    {
        txtFName.setText(manager.getFirstName());
        txtLName.setText(manager.getLastName());
        txtEmail.setText(manager.getEmail());
        txtPNumber.setText(manager.getPhoneNumber());

        txtPNumber2.setText(manager.getPhoneNumber2());
        txtPNumber3.setText(manager.getPhoneNumber3());
        txtAddress.setText(manager.getAddress());
        txtAddress2.setText(manager.getAddress2());

    }

    @FXML
    private void handleGoBack(ActionEvent event) throws IOException
    {
        goBack();
    }

    private void goBack() throws IOException
    {
        vcm.showAdminView((Stage) btnBack.getScene().getWindow());
    }

    @FXML
    private void handleApproval(ActionEvent event) throws IOException
    {
        if (!txtEmail.getText().isEmpty() && !txtFName.getText().isEmpty() && !txtLName.getText().isEmpty() && !txtPassword.getText().isEmpty())
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
        } else if (txtEmail.getText().isEmpty() || txtFName.getText().isEmpty() || txtLName.getText().isEmpty() || txtPassword.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fejl");
            alert.setContentText("Udfyld venligst de markerede felter");

            alert.showAndWait();
        }
    }

    @FXML
    public void handleChangeStatus(ActionEvent event) throws IOException
    {
        if (manager.isIsActive())
        {
            deactivate();
        } else
        {
            activate();
        }
    }

    private void deactivate() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deaktiver tovholder");
        alert.setHeaderText("Du er ved at deaktiver en tovholder. De vil også "
                + "blive fjernet fra alle laug.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            dp.deactivateManager(manager);
            goBack();
        } else
        {
            alert.close();
        }
    }

    private void activate() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aktiver tovholder");
        alert.setHeaderText("Du er ved at aktiver en tovholder. De skal manuelt "
                + "tilføjes til alle laug igen.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            dp.setManagerStatus(manager, true);
            goBack();
        } else
        {
            alert.close();
        }

    }

    @FXML
    private void handleComboClick(ActionEvent event)
    {
        manager = cbBoxManager.getSelectionModel().getSelectedItem();

        loadInfo();
    }

    private void editInfo()
    {
        manager.setEmail(txtEmail.getText());
        manager.setFirstName(txtFName.getText());
        manager.setLastName(txtLName.getText());
        manager.setPhoneNumber(txtPNumber.getText());

        manager.setPhoneNumber2(txtPNumber2.getText());
        manager.setPhoneNumber3(txtPNumber3.getText());
        manager.setAddress(txtAddress.getText());
        manager.setAddress2(txtAddress2.getText());

        dp.UpdateManager(manager);
    }

}
