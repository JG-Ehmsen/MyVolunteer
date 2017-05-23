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
        if (user.getLastInputDate() != null)
        {
            lblLastUpdated.setText("Sidst opdateret:\n" + user.getLastInputDate().toString());
        }
        lblName.setText(user.getFirstName() + " " + user.getLastName());

        changeLanguage();

        
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
                mainViewModel.changeView("Laug", "GUI/View/LaugViewSpecial.fxml");
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

    private void changeLanguage()
    {
        ResourceBundle rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());
        btnBack.setText(rb.getString("HoursSpecial.btnBack.text"));
        lblLastUpdated.setText(rb.getString("HoursSpecial.lblLastUpdated.text"));
        lblDatePick.setText(rb.getString("HoursSpecial.lblDatePick.text"));
        lblHoursInput.setText(rb.getString("HoursSpecial.lblHoursInput.text"));
        btnConfirmHours.setText(rb.getString("HoursSpecial.btnConfirmHours.text"));
    }
}
