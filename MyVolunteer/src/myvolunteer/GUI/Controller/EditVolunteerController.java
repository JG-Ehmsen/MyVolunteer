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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class EditVolunteerController implements Initializable
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
    private TextField txtFName;
    @FXML
    private RadioButton rdoMand;
    @FXML
    private RadioButton rdoKvinde;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNationalitet;
    @FXML
    private TextArea txtNote;
    @FXML
    private TextField txtLName;
    @FXML
    private ImageView imgPicture;
    final ToggleGroup tg = new ToggleGroup();

    Volunteer volunteer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        volunteer = mainViewModel.getLastSelectedUser();

        rdoMand.setToggleGroup(tg);
        rdoKvinde.setToggleGroup(tg);

        loadInfo();
    }

    private void loadInfo()
    {
        txtFName.setText(volunteer.getFirstName());
        txtLName.setText(volunteer.getLastName());
        txtEmail.setText(volunteer.getEmail());
        txtPhoneNumber.setText(volunteer.getPhoneNumber());
        txtNationalitet.setText(volunteer.getNationality());
        txtNote.setText(volunteer.getNote());

        if (volunteer.getGender().equals("Mand"))
        {
            tg.selectToggle(rdoMand);
        } else
        {
            tg.selectToggle(rdoKvinde);
        }
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Rediger frivillig");
        alert.setHeaderText("Du er ved at redigere en frivillig.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            editUser();
            goBack();
        } else
        {
            alert.close();
        }
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
    private void btnUploadPicture(ActionEvent event)
    {
    }

    @FXML
    private void handleDelete(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Slet frivillig");
        alert.setHeaderText("Du er ved at fjerne en frivillig.");
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

    private void editUser()
    {
        volunteer.setEmail(txtEmail.getText());
        volunteer.setFirstName(txtFName.getText());
        volunteer.setLastName(txtLName.getText());
        volunteer.setPhoneNumber(txtPhoneNumber.getText());
        volunteer.setNationality(txtNationalitet.getText());
        volunteer.setNote(txtNote.getText());

        if (tg.getSelectedToggle().equals(rdoMand))
        {
            volunteer.setGender("Mand");
        } else if (tg.getSelectedToggle().equals(rdoKvinde))
        {
            volunteer.setGender("Kvinde");
        }

        dp.UpdateUser(volunteer);
    }

}
