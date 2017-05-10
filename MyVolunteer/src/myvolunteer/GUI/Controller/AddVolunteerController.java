/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class AddVolunteerController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();

    @FXML
    private Button btnGodkend;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtBday;
    @FXML
    private RadioButton rbMale;
    @FXML
    private RadioButton rbFemale;
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

    DataParserModel dp = DataParserModel.getInstance();

    final ToggleGroup tg = new ToggleGroup();
    @FXML
    private Button btnUploadImage;
    @FXML
    private Button btnBack;
    @FXML
    private Label xFirstName;
    @FXML
    private Label xLastName;
    @FXML
    private Label xTelephone;
    @FXML
    private Label lblUdfyldVenligst;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        xFirstName.setVisible(false);
        xLastName.setVisible(false);
        xTelephone.setVisible(false);
        lblUdfyldVenligst.setVisible(false);

        rbMale.setToggleGroup(tg);
        rbFemale.setToggleGroup(tg);
        tg.selectToggle(rbMale);
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        if (txtFName.getText().isEmpty())
        {
            xFirstName.setVisible(true);
            lblUdfyldVenligst.setVisible(true);
        }
        if (txtLName.getText().isEmpty())
        {
            xLastName.setVisible(true);
            lblUdfyldVenligst.setVisible(true);
        }
        if (txtPhoneNumber.getText().isEmpty())
        {
            xTelephone.setVisible(true);
            lblUdfyldVenligst.setVisible(true);
        }
        if (xFirstName.isVisible() && !txtFName.getText().isEmpty())
        {
            xFirstName.setVisible(false);
        }
        if (xLastName.isVisible() && !txtLName.getText().isEmpty())
        {
            xLastName.setVisible(false);
        }
        if (xTelephone.isVisible() && !txtPhoneNumber.getText().isEmpty())
        {
            xTelephone.setVisible(false);
        }
        if (!txtFName.getText().isEmpty() && !txtLName.getText().isEmpty() && !txtPhoneNumber.getText().isEmpty())
        {
            handleUserInfo();

            mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

            // Closes the primary stage
            Stage stage = (Stage) btnGodkend.getScene().getWindow();
            stage.close();
        }
    }

    private void handleUserInfo()
    {
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String bDay = txtBday.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String eMail = txtEmail.getText();
        String nationality = txtNationalitet.getText();
        String note = txtNote.getText();
        String gender = "";
        if (tg.getSelectedToggle().equals(rbMale))
        {
            gender = "Mand";
        } else if (tg.getSelectedToggle().equals(rbFemale))
        {
            gender = "Kvinde";
        }

        Volunteer user = new Volunteer(999999, fName, phoneNumber);

        user.setLastName(lName);
        user.setEmail(eMail);
        user.setNationality(nationality);
        user.setNote(note);
        user.setGender(gender);
        user.setLastInputDate(new Date());

        CreateNewUser(user);
    }

    private void CreateNewUser(Volunteer user)
    {
        dp.CreateNewUser(user);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

}
