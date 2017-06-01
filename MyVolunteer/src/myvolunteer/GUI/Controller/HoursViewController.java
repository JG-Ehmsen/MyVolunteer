/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Model.ViewChangerModel;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class HoursViewController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dataParserModel = DataParserModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

    @FXML
    private Button btnConfirmHours;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtFieldHours;
    @FXML
    private Label lblLastUpdated;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblName;
    @FXML
    private Label lblHoursInput;
    @FXML
    private Label lblDatePick;
    @FXML
    private ImageView imgProfilePicture;

    Volunteer user;
    Guild guild;

    ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        this.rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());

        user = mainViewModel.getLastSelectedUser();
        guild = mainViewModel.getLastSelectedGuild();

        datePicker.setValue(LocalDate.now());

        setText();

        imgProfilePicture.setImage(user.getPicture());
    }

    @FXML
    private void handleConfirmHours(ActionEvent event) throws IOException, SQLServerException
    {
        if (!txtFieldHours.getText().isEmpty())
        {
            Instant instant = Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            // Validates that the input is valid (Only integers between 1-24)
            if (validateInput())
            {
                int hoursToWrite = Integer.parseInt(txtFieldHours.getText());
                writeHoursToDatabase(user, hoursToWrite, guild, date);
            } else
            {
                showError();
            }
        } else
        {
            showError();
        }
    }

    public void showError()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(rb.getString("HoursSpecial.alertTitle.text"));
        alert.setHeaderText(null);
        alert.setContentText(rb.getString("HoursSpecial.alertContent.text"));

        alert.showAndWait();
    }

    /**
     * Validates the hours put in to be whole hours between 1 and 24.
     *
     * @throws IOException
     */
    public boolean validateInput() throws IOException
    {
        boolean validHours = false;
        int inputHours;
        try
        {
            inputHours = Integer.parseInt(txtFieldHours.getText());
        } catch (NumberFormatException e)
        {
            return false;
        }
        if (inputHours >= 1 && inputHours <= 24)
        {
            validHours = true;

            //Change view to mainView (LaugView) after validation has been confirmed
            vcm.showLaugSelectionView((Stage) imgProfilePicture.getScene().getWindow());
        }
        // Boolean validHours is set to true if there is a match
        return validHours;
    }

    @FXML
    private void handleGoBack(ActionEvent event) throws IOException
    {
        vcm.showVolunteersView((Stage) imgProfilePicture.getScene().getWindow());
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date) throws SQLServerException
    {
        //reference to writeToDatabase method in DataParserModel
        dataParserModel.writeHoursToDatabase(volunteer, hours, guild, date);
    }

    @FXML
    private void handleDigitAction(ActionEvent event)
    {
        String digit = ((Button) event.getSource()).getText();
        String oldNumber = txtFieldHours.getText();
        String newNumber = oldNumber + digit;
        txtFieldHours.setText(newNumber);
    }

    @FXML
    private void handleDeleteAction(ActionEvent event)
    {
        String originalText = txtFieldHours.getText();
        String currentText = removeLastChar(originalText);
        txtFieldHours.setText(currentText);

    }

    public String removeLastChar(String str)
    {

        if (str != null && str.length() > 0)
        {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private void setText()
    {
        lblName.setText(user.getFirstName() + " " + user.getLastName());

        if (user.getLastInputDate() != null)
        {
            lblLastUpdated.setText(rb.getString("HoursSpecial.lblLastUpdated.text") + "\n" + user.getLastInputDate().toString());
        } else
        {
            lblLastUpdated.setText(rb.getString("HoursSpecial.lblLastUpdated.text"));
        }
        lblDatePick.setText(rb.getString("HoursSpecial.lblDatePick.text"));
        lblHoursInput.setText(rb.getString("HoursSpecial.lblHoursInput.text"));
        btnConfirmHours.setText(rb.getString("HoursSpecial.btnConfirmHours.text"));
        btnBack.setText(rb.getString("HoursSpecial.btnBack.text"));
    }

}
