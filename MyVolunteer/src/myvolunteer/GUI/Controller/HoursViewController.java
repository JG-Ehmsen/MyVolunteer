/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

    @FXML
    private Button btnConfirmHours;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtFieldHours;

    DataParserModel dataParserModel = DataParserModel.getInstance();

    // Validation file
    private String validationFile = "numberValidation.txt";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void handleConfirmHours(ActionEvent event) throws IOException, SQLServerException
    {
        int hoursToWrite = Integer.parseInt(txtFieldHours.getText());

        Volunteer testVlounteer = new Volunteer(2, "Anders", "25252525");
        Guild testGuild = new Guild(1, "Bakery");
        Date testDate = new Date();

        // Validates that the input is valid (Only integers between 1-24)
        if(validateInput())
        {
        writeHoursToDatabase(testVlounteer, hoursToWrite, testGuild, testDate);
        }
    }

    /**
     *
     * @throws IOException
     */
    public boolean validateInput() throws IOException
    {
        // Creates a new scanner and loads the numberValidation.txt file.
        Scanner s = null;
        s = new Scanner(new BufferedReader(new FileReader(validationFile)));

        // Boolean isFound is set to true if there is a match
        boolean isFound = false;
        while (s.hasNext())
        {
            String input = s.next();
            if (txtFieldHours.getText().equals(input))
            {

                isFound = true;

                //Handle UI action request so data can be saved to Database
                //NB only if validated as correct input
                //Change view to mainView (LaugView) after validation has been confirmed
                mainViewModel.changeView("Laug", "GUI/View/LaugView.fxml");
                // Closes the primary stage
                Stage stage = (Stage) btnConfirmHours.getScene().getWindow();
                stage.close();

                break;
            }
        }
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
        s.close();
        return isFound;
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date) throws SQLServerException
    {
        //reference to writeToDatabase method in DataParserModel
        dataParserModel.writeHoursToDatabase(volunteer, hours, guild, date);
    }
}
