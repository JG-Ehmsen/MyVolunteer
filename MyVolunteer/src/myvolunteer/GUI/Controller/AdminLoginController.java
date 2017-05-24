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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class AdminLoginController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dataParserModel = DataParserModel.getInstance();

    private Stage previousStage;

    @FXML
    private TextField UsernameField;
    @FXML
    private TextField codeField;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnBack;
    @FXML
    private Label xUsername;
    @FXML
    private Label xPassword;
    @FXML
    private Label lblUdfyldVenligst;
    @FXML
    private Label lblInvalidUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        xUsername.setVisible(false);
        xPassword.setVisible(false);
        lblUdfyldVenligst.setVisible(false);
        lblInvalidUser.setVisible(false);
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        if (!UsernameField.getText().isEmpty() && !codeField.getText().isEmpty())
        {
            Stage stage = (Stage) btnLogin.getScene().getWindow();

            dataParserModel.tryLogin(UsernameField.getText(), codeField.getText(), stage);

            xUsername.setVisible(false);
            xPassword.setVisible(false);
            lblUdfyldVenligst.setVisible(false);

            lblInvalidUser.setVisible(true);

        }
        if (UsernameField.getText().isEmpty())
        {
            lblInvalidUser.setVisible(false);
            xUsername.setVisible(true);
            lblUdfyldVenligst.setVisible(true);
        }
        if (codeField.getText().isEmpty())
        {
            lblInvalidUser.setVisible(false);
            xPassword.setVisible(true);
            lblUdfyldVenligst.setVisible(true);

        }
        if (xUsername.isVisible() && !UsernameField.getText().isEmpty())
        {
            xUsername.setVisible(false);

        }
        if (xPassword.isVisible() && !codeField.getText().isEmpty())
        {
            xPassword.setVisible(false);
        }

    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Laug", "GUI/View/LaugViewSpecial.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

}
