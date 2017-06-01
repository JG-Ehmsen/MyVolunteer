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
import myvolunteer.GUI.Model.ViewChangerModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class AdminLoginController implements Initializable
{

    /**
     * Gets the singleton instance of the MainViewModel, DateParser and
     * ViewChangerModel.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dataParserModel = DataParserModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

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
    private Label lblXUsername;
    @FXML
    private Label lblXPassword;
    @FXML
    private Label lblPleaseFill;
    @FXML
    private Label lblInvalidUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        lblXUsername.setVisible(false);
        lblXPassword.setVisible(false);
        lblPleaseFill.setVisible(false);
        lblInvalidUser.setVisible(false);
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        if (!UsernameField.getText().isEmpty() && !codeField.getText().isEmpty())
        {
            Stage stage = (Stage) btnLogin.getScene().getWindow();

            dataParserModel.tryLogin(UsernameField.getText(), codeField.getText(), stage);

            lblXUsername.setVisible(false);
            lblXPassword.setVisible(false);
            lblPleaseFill.setVisible(false);
            lblInvalidUser.setVisible(true);
        }
        if (UsernameField.getText().isEmpty())
        {
            lblInvalidUser.setVisible(false);
            lblXUsername.setVisible(true);
            lblPleaseFill.setVisible(true);
        }
        if (codeField.getText().isEmpty())
        {
            lblInvalidUser.setVisible(false);
            lblXPassword.setVisible(true);
            lblPleaseFill.setVisible(true);
        }
        if (lblXUsername.isVisible() && !UsernameField.getText().isEmpty())
        {
            lblXUsername.setVisible(false);
        }
        if (lblXPassword.isVisible() && !codeField.getText().isEmpty())
        {
            lblXPassword.setVisible(false);
        }
    }

    @FXML
    private void handleGoBack(ActionEvent event) throws IOException
    {
        vcm.showLaugSelectionView((Stage) btnBack.getScene().getWindow());
    }
}
