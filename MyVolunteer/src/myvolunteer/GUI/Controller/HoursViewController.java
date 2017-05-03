/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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

    private int hoursSpent;

    private String file;
    
    DataParserModel dataParserModel = DataParserModel.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void handleConfirmHours(ActionEvent event) throws IOException
    {
        validateInput();
    }

    public void validateInput() throws IOException
    {
        Scanner s = null;
        s = new Scanner(new BufferedReader(new FileReader("numberValidation.txt")));

        boolean isFound = false;
        while (s.hasNext())
        {
            String input = s.next();
            if (txtFieldHours.getText().equals(input))
            {
                isFound = true;

                //Handle UI action request so data can be saved to Database
                //NB only if validated as correct input
                writeHoursToDatabase();
                
                //Change view to mainView (LaugView) after validation has been confirmed
                mainViewModel.changeView("Laug", "GUI/View/LaugView.fxml");
                // Closes the primary stage
                Stage stage = (Stage) btnConfirmHours.getScene().getWindow();
                stage.close();

                System.out.println("JA DAAA");
                break;
            }
        }
        if (!isFound)
        {
            System.out.println("Det er forkert");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Forkert input");
            alert.setHeaderText(null);
            alert.setContentText("Indtast venligst hele timer mellem 1 - 24 ");
            alert.showAndWait();
        }
        s.close();
    }

    public void writeHoursToDatabase()
    {
        //reference to writeToDatabase method in DataParserModel
        dataParserModel.writeHoursToDatabase();
    }
}
