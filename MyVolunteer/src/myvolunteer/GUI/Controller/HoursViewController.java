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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
        if (txtFieldHours.getText().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Indtast timer");
            alert.setHeaderText(null);
            alert.setContentText("Indtast timer");

            alert.showAndWait();
        } else
        {
            mainViewModel.changeView("Laug", "GUI/View/LaugView.fxml");

            // Closes the primary stage
            Stage stage = (Stage) btnConfirmHours.getScene().getWindow();
            stage.close();
        }
    }

}
