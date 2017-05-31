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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import myvolunteer.GUI.Model.ViewChangerModel;

/**
 * FXML Controller class
 *
 * @author jonas
 */
public class EditLaugController implements Initializable
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
    private Button btnBack;
    @FXML
    private TextField txtLaugInformation;
    @FXML
    private ListView<Volunteer> listAvailableVolunteers;
    @FXML
    private ListView<Volunteer> listChosenVolunteers;
    @FXML
    private TextField txtSearchChosen;
    @FXML
    private ComboBox<Manager> comboManager;
    @FXML
    private TextField txtLaugName;
    @FXML
    private TextField txtSearchFilterAvailable;
    @FXML
    private Text lblAntalFrivillige;
    @FXML
    private Button btnChangeStatus;
    @FXML
    private ImageView imgGuildPicture;
    @FXML
    private Button btnUploadPicture;

    Guild guild;
    Manager manager;

    List<Volunteer> allVolunteerList = new ArrayList<>();
    List<Manager> managerList = new ArrayList<>();
    ObservableList<Volunteer> allUsers = FXCollections.observableArrayList();
    ObservableList<Volunteer> chosenUsers = FXCollections.observableArrayList();
    ObservableList<Manager> managers = FXCollections.observableArrayList();

    List<Integer> inGuild = new ArrayList();
    List<Integer> notInGuild = new ArrayList();

    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        guild = mainViewModel.getLastSelectedGuild();
        allVolunteerList = dp.getActiveUsers();
        allUsers.setAll(allVolunteerList);
        listAvailableVolunteers.setItems(allUsers);
        listChosenVolunteers.setItems(chosenUsers);

        managerList = dp.getActiveManagers();
        managers.setAll(managerList);
        comboManager.setItems(managers);
        setStartManager();

        loadInformation();
        initialSortLists();

        lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteers.getItems().size());

        if (guild.isIsActive())
        {
            btnChangeStatus.setText("Gør inaktiv");
        } else
        {
            btnChangeStatus.setText("Gør aktiv");
        }

    }

    private void setStartManager()
    {
        manager = dp.getManagerForGuild(guild);
        comboManager.getSelectionModel().select(manager);
    }

    private void loadInformation()
    {
        this.txtLaugInformation.setText(guild.getDescription());
        this.txtLaugName.setText(guild.getName());
        imgGuildPicture.setImage(guild.getPicture());
        img = guild.getBufferedPicture();
    }

    private void initialSortLists()
    {
        for (Volunteer volunteer : allVolunteerList)
        {
            for (Integer i : guild.getMemberList())
            {
                if (i == volunteer.getId())
                {
                    chosenUsers.add(volunteer);
                    allUsers.remove(volunteer);
                }
            }
        }
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Rediger laug");
        alert.setHeaderText("Du er ved at redigere et laug.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            editGuild();
            goBack();
        } else
        {
            alert.close();
        }
    }

    private void editGuild()
    {
        guild.setDescription(txtLaugInformation.getText());
        guild.setName(txtLaugName.getText());
        guild.setPicture(img);

        for (Volunteer volunteer : listAvailableVolunteers.getItems())
        {
            for (Integer g : guild.getMemberList())
            {
                if (volunteer.getId() == g)
                {
                    notInGuild.add(volunteer.getId());
                }
            }

        }

        for (Volunteer volunteer : listChosenVolunteers.getItems())
        {
            inGuild.add(volunteer.getId());
        }

        for (Integer i : guild.getMemberList())
        {
            inGuild.remove(i);
        }

        dp.UpdateGuild(guild, manager, inGuild, notInGuild);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        goBack();
    }

    @FXML
    private void searchFilterAvailable(KeyEvent event)
    {
        String filter = txtSearchFilterAvailable.getText();
        listAvailableVolunteers.setItems(dp.filter(filter, allUsers));
    }

    @FXML
    private void searchFilterChosen(KeyEvent event)
    {
        String filter = txtSearchChosen.getText();
        listChosenVolunteers.setItems(dp.filter(filter, chosenUsers));

    }

    @FXML
    private void comboManager(ActionEvent event)
    {
        manager = comboManager.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void btnAddVolunteer(ActionEvent event)
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
            listAvailableVolunteers.getItems().remove(volunteer);

            lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteers.getItems().size());
        }
    }

    @FXML
    private void btnRemoveVolunteer(ActionEvent event)
    {
        removeVolunteer();
    }

    private void removeVolunteer()
    {
        if (listChosenVolunteers.getSelectionModel().getSelectedItem() != null)
        {
            Volunteer volunteer = listChosenVolunteers.getSelectionModel().getSelectedItem();
            allUsers.add(volunteer);
            chosenUsers.remove(volunteer);
            listChosenVolunteers.getItems().remove(volunteer);

            lblAntalFrivillige.setText("Antal frivillige: " + listChosenVolunteers.getItems().size());
        }
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
    private void handleChangeStatus(ActionEvent event) throws IOException
    {
        if (guild.isIsActive())
        {
            deactivate();
        } else
        {
            activate();
        }

    }

    private void deactivate() throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deaktiver Laug");
        alert.setHeaderText("Du er ved at deaktivere et laug. Det vil fjerne "
                + "alle de frivillige fra dette laug.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            btnChangeStatus.setText("Gør aktiv");
            dp.deactivateGuild(guild);

            alert.close();
        } else
        {
            alert.close();
        }

    }

    private void activate() throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Aktiver Laug");
        alert.setHeaderText("Du er ved at aktivere et laug. Der skal manuelt "
                + "tilføjes frivillige til dette laug igen.");
        alert.setContentText("Tryk OK for at fortsætte.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeAnnuller = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeAnnuller);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK)
        {
            btnChangeStatus.setText("Gør inaktiv");
            dp.setGuildStatus(guild, true);

            alert.close();
        } else
        {
            alert.close();
        }

    }

    @FXML
    private void handleUploadPicture(ActionEvent event)
    {
        try
        {
            FileChooser fs = new FileChooser();
            File file = fs.showOpenDialog((Stage) btnUploadPicture.getScene().getWindow());
            fs.setTitle("Vælg Billede");
            fs.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

            if (file != null)
            {
                img = ImageIO.read(file);

                Image image = SwingFXUtils.toFXImage(img, null);

                imgGuildPicture.setImage(image);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
