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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

public class AddLaugController implements Initializable
{

    /**
     * Gets the singleton instance of the model and date parser.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();

    @FXML
    private Button btnGodkend;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private TextField txtLaugName;
    @FXML
    private TextField txtLaugInfo;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtSearchAvailable;
    @FXML
    private ListView<Volunteer> listChosenVolunteer;
    @FXML
    private ComboBox<Manager> comboManager;
    @FXML
    private ListView<Volunteer> listAvailableVolunteers;
    @FXML
    private Text lblAntalFrivillige;
    @FXML
    private Label xLaugName;
    @FXML
    private Label xManager;
    @FXML
    private Label lblUdfyldVenligst;
    @FXML
    private ImageView imgGuildImage;
    @FXML
    private Button btnUploadImage;

    List<Volunteer> allVolunteerList = new ArrayList<>();
    List<Manager> managerList = new ArrayList<>();
    ObservableList<Volunteer> allUsers = FXCollections.observableArrayList();
    ObservableList<Volunteer> chosenUsers = FXCollections.observableArrayList();
    ObservableList<Manager> managers = FXCollections.observableArrayList();

    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    private String defaultImage = "Resource/GuildPicture.png";
    boolean imageSet = false;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        init();
    }

    private void init()
    {
        lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteer.getItems().size());

        allVolunteerList = dp.getActiveUsers();
        allUsers.setAll(allVolunteerList);
        listAvailableVolunteers.setItems(allUsers);
        listChosenVolunteer.setItems(chosenUsers);
        managerList = dp.getActiveManagers();
        managers.setAll(managerList);
        comboManager.setItems(managers);

        xLaugName.setVisible(false);
        lblUdfyldVenligst.setVisible(false);
        xManager.setVisible(false);
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {

        if (txtLaugName.getText().isEmpty())
        {
            xLaugName.setVisible(true);
            lblUdfyldVenligst.setVisible(true);
        }
        if (comboManager.getSelectionModel().isEmpty())
        {
            xManager.setVisible(true);
            lblUdfyldVenligst.setVisible(true);
        }
        if (xLaugName.isVisible() && !txtLaugName.getText().isEmpty())
        {
            xLaugName.setVisible(false);
        }
        if (xManager.isVisible() && !comboManager.getSelectionModel().isEmpty())
        {
            xManager.setVisible(false);
        }
        if (!txtLaugName.getText().isEmpty() && !comboManager.getSelectionModel().isEmpty())
        {
            handleLaugInfo();

            mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

            // Closes the primary stage
            Stage stage = (Stage) btnGodkend.getScene().getWindow();
            stage.close();
        }
    }

    private void handleLaugInfo()
    {
        String LaugName = txtLaugName.getText();
        String LaugInformation = txtLaugInfo.getText();
        int MID = comboManager.getSelectionModel().getSelectedItem().getId();

        Guild guild = new Guild(999999, LaugName);
        List<Integer> userID = new ArrayList();
        for (Volunteer volunteer : listChosenVolunteer.getItems())
        {
            userID.add(volunteer.getId());
        }
        //userID.add(comboManager.getSelectionModel().getSelectedItem().getId());
        guild.setMemberList(userID);
        guild.setDescription(txtLaugInfo.getText());

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
        guild.setPicture(img);

        dp.CreateNewLaug(guild, MID);
    }

    @FXML
    private void handleAddVolunteer(ActionEvent event)
    {
        addVolunteer();
    }

    private void addVolunteer()
    {
        if (listAvailableVolunteers.getSelectionModel().getSelectedItem() != null)
        {
            Volunteer volunteer = listAvailableVolunteers.getSelectionModel().getSelectedItem();
            chosenUsers.add(volunteer);
            allUsers.remove(volunteer);

            lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteer.getItems().size());
        }
    }

    @FXML
    private void handleRemoveVolunteer(ActionEvent event)
    {
        removeVolunteer();
    }

    private void removeVolunteer()
    {
        if (listChosenVolunteer.getSelectionModel().getSelectedItem() != null)
        {
            Volunteer volunteer = listChosenVolunteer.getSelectionModel().getSelectedItem();
            allUsers.add(volunteer);
            chosenUsers.remove(volunteer);

            lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteer.getItems().size());
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void searchFilter(KeyEvent event)
    {
        String filter = txtSearchAvailable.getText();
        ObservableList<Volunteer> filteredList = FXCollections.observableArrayList();
        if (filter.equals(""))
        {
            listAvailableVolunteers.setItems(allUsers);
        } else
        {
            for (Volunteer vol : allUsers)
            {
                if (vol.toString().toLowerCase().contains(filter.toLowerCase()))
                {
                    filteredList.add(vol);
                }
            }
            listAvailableVolunteers.setItems(filteredList);
        }
    }

    @FXML
    private void keyEventAvailable(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            addVolunteer();
        }
    }

    @FXML
    private void keyEventChosen(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER) || (event.getCode().equals(KeyCode.DELETE)))
        {
            removeVolunteer();
        }
    }

    @FXML
    private void handleUploadPicture(ActionEvent event)
    {
        try
        {
            FileChooser fs = new FileChooser();
            File file = fs.showOpenDialog((Stage) btnUploadImage.getScene().getWindow());
            fs.setTitle("Vælg Billede");
            fs.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

            img = ImageIO.read(file);

            Image image = SwingFXUtils.toFXImage(img, null);

            imgGuildImage.setImage(image);
            imageSet = true;
        } catch (IOException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
