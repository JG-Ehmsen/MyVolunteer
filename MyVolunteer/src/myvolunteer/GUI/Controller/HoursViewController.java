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
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

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

    @FXML
    private Button btnConfirmHours;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtFieldHours;
    @FXML
    private Label lblLastUpdated;

    Volunteer user;
    Guild guild;

    // Validation file
    private String validationFile = "numberValidation.txt";
    @FXML
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        user = mainViewModel.getLastSelectedUser();
        guild = mainViewModel.getLastSelectedGuild();

        datePicker.setValue(LocalDate.now());
        lblLastUpdated.setText("Sidst opdateret: " + user.getLastInputDate().toString());
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
            }
        } else
        {
            // Displays an alertbox if the hours typed are incorrect.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Forkert input");
            alert.setHeaderText(null);
            alert.setContentText("Indtast venligst hele timer mellem 1 - 24");
            alert.showAndWait();
        }
    }

    /**
     *
     * @throws IOException
     */
    public boolean validateInput() throws IOException
    {
        boolean isFound = false;

        for (int i = 1; i < 25; i++)
        {
            String iString = Integer.toString(i);
            if (txtFieldHours.getText().equals(iString))
            {
                isFound = true;

                //Change view to mainView (LaugView) after validation has been confirmed
                mainViewModel.changeView("Laug", "GUI/View/LaugView.fxml");
                // Closes the primary stage
                Stage stage = (Stage) btnConfirmHours.getScene().getWindow();
                stage.close();

                break;
            }
        }
        // Boolean isFound is set to true if there is a match
        if (!isFound)
        {
            // Displays an alertbox if the hours typed are incorrect.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Forkert input");
            alert.setHeaderText(null);
            alert.setContentText("Indtast venligst hele timer mellem 1 - 24 ");
            alert.showAndWait();
        }
        // Closes the scanner
        return isFound;
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Frivillig", "GUI/View/VolunteerView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date) throws SQLServerException
    {
        //reference to writeToDatabase method in DataParserModel
        dataParserModel.writeHoursToDatabase(volunteer, hours, guild, date);
    }
}
