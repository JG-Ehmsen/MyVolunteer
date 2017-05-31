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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class AddVolunteerController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

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
    @FXML
    private TextField txtPhoneNumberTwo;
    @FXML
    private TextField txtPhoneNumberthree;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddressTwo;
    @FXML
    private ImageView imgProfilePicture;

    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    private String defaultImage = "Resource/UserPicture.png";
    boolean imageSet = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        init();
    }

    private void init()
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

            goBack();
        }
    }

    private void handleUserInfo()
    {
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String bDay = txtBday.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String phoneNumber2 = txtPhoneNumberTwo.getText();
        String phoneNumber3 = txtPhoneNumberthree.getText();
        String Address = txtAddress.getText();
        String Address2 = txtAddressTwo.getText();
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
        user.setAddress(Address);
        user.setAddress2(Address2);
        user.setPhoneNumber2(phoneNumber2);
        user.setPhoneNumber3(phoneNumber3);
        if (!imageSet)
        {

            try
            {
                img = ImageIO.read(getClass().getResource(defaultImage));
            } catch (IOException ex)
            {
                Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        user.setPicture(img);

        CreateNewUser(user);
    }

    private void CreateNewUser(Volunteer user)
    {
        dp.CreateNewUser(user);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
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
                imageSet = true;
            }
        } catch (IOException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
