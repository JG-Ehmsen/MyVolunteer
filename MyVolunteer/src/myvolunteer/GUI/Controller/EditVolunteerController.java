/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Model.ViewChangerModel;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class EditVolunteerController implements Initializable
{

    /**
     * Gets the singleton instance of the MainViewModel, DateParser and
     * ViewChangerModel.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

    @FXML
    private Button btnApprove;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnChangeStatus;
    @FXML
    private TextField txtFName;
    @FXML
    private RadioButton rdMale;
    @FXML
    private RadioButton rdFemale;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNationality;
    @FXML
    private TextArea txtNote;
    @FXML
    private TextField txtLName;
    @FXML
    private Label lblXFirstName;
    @FXML
    private Label lblXLastName;
    @FXML
    private Label lblXTelephone;
    @FXML
    private Label lblPleaseFill;
    @FXML
    private TextField txtPhoneNumber2;
    @FXML
    private TextField txtPhoneNumber3;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddress2;
    @FXML
    private TextField tblBDay;
    @FXML
    private Button btnUploadImage;
    @FXML
    private ImageView imgProfilePicture;

    Volunteer volunteer;
    final ToggleGroup tg = new ToggleGroup();
    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        volunteer = mainViewModel.getLastSelectedUser();

        rdMale.setToggleGroup(tg);
        rdFemale.setToggleGroup(tg);

        loadInfo();

        lblXTelephone.setVisible(false);
        lblXFirstName.setVisible(false);
        lblXLastName.setVisible(false);
        lblPleaseFill.setVisible(false);

        if (volunteer.isActive())
        {
            btnChangeStatus.setText("Gør inaktiv");
        } else
        {
            btnChangeStatus.setText("Gør aktiv");
        }

    }

    private void loadInfo()
    {
        txtFName.setText(volunteer.getFirstName());
        txtLName.setText(volunteer.getLastName());
        tblBDay.setText(volunteer.getBDay().toString());
        txtEmail.setText(volunteer.getEmail());
        txtPhoneNumber.setText(volunteer.getPhoneNumber());
        txtPhoneNumber2.setText(volunteer.getPhoneNumber2());
        txtPhoneNumber3.setText(volunteer.getPhoneNumber3());
        txtAddress.setText(volunteer.getAddress());
        txtAddress2.setText(volunteer.getAddress2());
        txtNationality.setText(volunteer.getNationality());
        txtNote.setText(volunteer.getNote());
        imgProfilePicture.setImage(volunteer.getPicture());
        img = volunteer.getBufferedPicture();

        if (volunteer.getGender().equals("Mand"))
        {
            tg.selectToggle(rdMale);
        } else
        {
            tg.selectToggle(rdFemale);
        }
    }

    @FXML
    private void handleApproval(ActionEvent event) throws IOException
    {
        if (txtFName.getText().isEmpty())
        {
            lblXFirstName.setVisible(true);
            lblPleaseFill.setVisible(true);
        }
        if (txtLName.getText().isEmpty())
        {
            lblXLastName.setVisible(true);
            lblPleaseFill.setVisible(true);
        }
        if (txtPhoneNumber.getText().isEmpty())
        {
            lblXTelephone.setVisible(true);
            lblPleaseFill.setVisible(true);
        }
        if (lblXFirstName.isVisible() && !txtFName.getText().isEmpty())
        {
            lblXFirstName.setVisible(false);
        }
        if (lblXLastName.isVisible() && !txtLName.getText().isEmpty())
        {
            lblXLastName.setVisible(false);
        }
        if (lblXTelephone.isVisible() && !txtPhoneNumber.getText().isEmpty())
        {
            lblXTelephone.setVisible(false);
        }
        if (!txtFName.getText().isEmpty() && !txtLName.getText().isEmpty() && !txtPhoneNumber.getText().isEmpty())
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
    }

    @FXML
    private void handleGoBack(ActionEvent event) throws IOException
    {
        goBack();
    }

    private void goBack() throws IOException
    {
        if (mainViewModel.getLoggedInManager().isAdmin())
        {
            vcm.showAdminView((Stage) btnBack.getScene().getWindow());
        } else if (!mainViewModel.getLoggedInManager().isAdmin())
        {
            vcm.showManagerView((Stage) btnBack.getScene().getWindow());
        }
    }

    @FXML
    private void handleChangeStatus(ActionEvent event) throws IOException
    {
        if (volunteer.isActive())
        {
            deactivateVolunteer();
        } else
        {
            activateVolunteer();
        }

    }

    /**
     * Changes the status of the selected manager to inactive
     *
     * @throws IOException
     */
    private void deactivateVolunteer() throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deaktiver frivillig");
        alert.setHeaderText("Du er ved at deaktivere en frivillig. De vil også "
                + "blive fjernet fra alle laug.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            dp.deactiveVolunteer(volunteer);
            goBack();
        } else
        {
            alert.close();
        }
    }

    /**
     * Changes the status of the selected manager to active
     *
     * @throws IOException
     */
    private void activateVolunteer() throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Aktiver frivillig");
        alert.setHeaderText("Du er ved at aktivere en frivillig. De skal manuelt "
                + "tilføjes til alle laug igen.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            dp.setVolunteerStatus(volunteer, true);
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
        volunteer.setPhoneNumber2(txtPhoneNumber2.getText());
        volunteer.setPhoneNumber3(txtPhoneNumber3.getText());
        volunteer.setAddress(txtAddress.getText());
        volunteer.setAddress2(txtAddress2.getText());
        volunteer.setNationality(txtNationality.getText());
        volunteer.setNote(txtNote.getText());
        volunteer.setPicture(img);

        if (tg.getSelectedToggle().equals(rdMale))
        {
            volunteer.setGender("Mand");
        } else if (tg.getSelectedToggle().equals(rdFemale))
        {
            volunteer.setGender("Kvinde");
        }

        dp.UpdateUser(volunteer);
    }

    @FXML
    private void handleUploadImage(ActionEvent event)
    {
        try
        {
            FileChooser fs = new FileChooser();
            File file = fs.showOpenDialog((Stage) btnUploadImage.getScene().getWindow());
            fs.setTitle("Vælg Billede");
            fs.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

            if (file != null)
            {
                img = ImageIO.read(file);

                Image image = SwingFXUtils.toFXImage(img, null);

                imgProfilePicture.setImage(image);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
