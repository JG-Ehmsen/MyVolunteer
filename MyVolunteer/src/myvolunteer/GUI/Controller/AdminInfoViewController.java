/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Utility.ClipBoardUtility;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class AdminInfoViewController implements Initializable
{

    ObservableList<Volunteer> users = FXCollections.observableArrayList();

    /**
     * Gets the singleton instances.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    ClipBoardUtility clipBoard = new ClipBoardUtility();

    private Guild lastSelectedGuild;
    private List<Guild> guildList = new ArrayList<>();

    private String documentHeader = "Fornavn" + "	" + "Efternavn" + "	";
    private String documentText;

    @FXML
    private Button btnBack;
    @FXML
    private ComboBox<Guild> comboBoxGuild;
    @FXML
    private TableView<Volunteer> tblViewInfo;
    @FXML
    private TableColumn<Volunteer, String> tblColumnName;
    @FXML
    private TableColumn<Volunteer, String> tblColumnPhone;
    @FXML
    private TableColumn<Volunteer, String> tblColumnMail;
    @FXML
    private TableColumn<Volunteer, String> tblColumnLName;
    @FXML
    private Button btnAllVolunteers;
    @FXML
    private TableColumn<Volunteer, String> tblColumnGender;
    @FXML
    private TableColumn<Volunteer, String> tblColumnNationality;
    @FXML
    private TableColumn<Volunteer, String> tblColumnPhone2;
    @FXML
    private TableColumn<Volunteer, String> tblColumnPhone3;
    @FXML
    private TableColumn<Volunteer, String> tblColumnAddress;
    @FXML
    private TableColumn<Volunteer, String> tblColumnAddress2;
    @FXML
    private TableColumn<Volunteer, String> tblColumnLastInput;
    @FXML
    private TableColumn<Volunteer, String> tblColumnTotalHours;
    @FXML
    private Button btnSave;
    @FXML
    private CheckBox cBoxPhone;
    @FXML
    private CheckBox cBoxPhone2;
    @FXML
    private CheckBox cBoxPhone3;
    @FXML
    private CheckBox cBoxEmail;
    @FXML
    private CheckBox cBoxNationality;
    @FXML
    private CheckBox cBoxAddress;
    @FXML
    private CheckBox cBoxAddress2;
    @FXML
    private CheckBox cBoxGender;
    @FXML
    private CheckBox cBoxLastInput;
    @FXML
    private CheckBox cBoxTotalHours;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        guildList = dp.getActiveGuilds();
        initializeTable();
        comboContent();

        tblViewInfo.getSelectionModel().setCellSelectionEnabled(true);
        tblViewInfo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleComboClick(ActionEvent event)
    {
        lastSelectedGuild = comboBoxGuild.getSelectionModel().getSelectedItem();
        loadTableView();
    }

    private void comboContent()
    {
        ObservableList guilds = FXCollections.observableArrayList(guildList);
        comboBoxGuild.setItems(guilds);
    }

    private void loadTableView()
    {
        if (lastSelectedGuild == null)
        {
            initializeTable();
        } else
        {
            {
                ObservableList<Volunteer> guildUsers = FXCollections.observableArrayList();
                for (Integer i : lastSelectedGuild.getMemberList())
                {
                    for (Volunteer user : dp.getActiveUsers())
                    {
                        if (user.getId() == i)
                        {
                            guildUsers.add(user);
                        }
                    }
                }
                tblViewInfo.setItems(guildUsers);
            }
        }
    }

    private void initializeTable()
    {
        tblColumnGender.setCellValueFactory(celldata -> celldata.getValue().getGenderProperty());
        tblColumnName.setCellValueFactory(cellData -> cellData.getValue().getFNameProperty());
        tblColumnLName.setCellValueFactory(celldata -> celldata.getValue().getLNameProperty());
        tblColumnPhone.setCellValueFactory(celldata -> celldata.getValue().getPhoneProperty());
        tblColumnPhone2.setCellValueFactory(celldata -> celldata.getValue().getPhone2Property());
        tblColumnPhone3.setCellValueFactory(celldata -> celldata.getValue().getPhone3Property());
        tblColumnMail.setCellValueFactory(celldata -> celldata.getValue().getMailProperty());
        tblColumnNationality.setCellValueFactory(celldata -> celldata.getValue().getNationalityProperty());
        tblColumnAddress.setCellValueFactory(celldata -> celldata.getValue().getAddressProperty());
        tblColumnAddress2.setCellValueFactory(celldata -> celldata.getValue().getAddress2Property());
        tblColumnLastInput.setCellValueFactory(celldata -> celldata.getValue().getLastInputProperty());
        tblColumnTotalHours.setCellValueFactory(celldata -> celldata.getValue().getTotalHoursProperty());
        
        ObservableList nameArrayList = FXCollections.observableArrayList(dp.getActiveUsers());
        tblViewInfo.setItems(nameArrayList);
    }

    @FXML
    private void handleSave(ActionEvent event) throws Exception
    {
        saveData();
    }

    /*
    Exports the data in the tableView to a .xls file.
     */
    private void saveData() throws Exception
    {
        Writer writer = null;
        try
        {
            FileChooser fileChooser = new FileChooser();

            Date todaysDate = new Date();
            SimpleDateFormat ft
                    = new SimpleDateFormat("dd.MM.yyyy");

            //Show save file dialog
            Stage stage = (Stage) btnAllVolunteers.getScene().getWindow();
            File fileName = new File("Laug information " + ft.format(todaysDate) + ".xls");
            fileChooser.setInitialFileName(fileName.toString());
            File file = fileChooser.showSaveDialog(stage);
            if (file != null)
            {
                writer = new BufferedWriter(new FileWriter(file));
                if (cBoxAddress.isSelected())
                {
                    documentHeader = documentHeader + "Adresse" + "	";
                }
                if (cBoxAddress2.isSelected())
                {
                    documentHeader = documentHeader + "Adresse 2" + "	";
                }
                if (cBoxEmail.isSelected())
                {
                    documentHeader = documentHeader + "Email" + "	";
                }
                if (cBoxNationality.isSelected())
                {
                    documentHeader = documentHeader + "Nationalitet" + "	";
                }
                if (cBoxPhone.isSelected())
                {
                    documentHeader = documentHeader + "Telefon" + "	";
                }
                if (cBoxPhone2.isSelected())
                {
                    documentHeader = documentHeader + "Telefon 2" + "	";
                }
                if (cBoxPhone3.isSelected())
                {
                    documentHeader = documentHeader + "Telefon 3" + "	";
                }
                if (cBoxGender.isSelected())
                {
                    documentHeader = documentHeader + "Køn" + "	";
                }
                if (cBoxLastInput.isSelected())
                {
                    documentHeader = documentHeader + "Sidst opdateret" + "	";
                }
                if (cBoxTotalHours.isSelected())
                {
                    documentHeader = documentHeader + "Total timer" + "	";
                }
                documentHeader = documentHeader + "\n";
                writer.write(documentHeader);
                documentHeader = "Fornavn" + "	" + "Efternavn" + "	";

                for (Volunteer user : tblViewInfo.getItems())
                {
                    documentText = user.getFirstName() + "	" + user.getLastName() + "	";
                    if (cBoxAddress.isSelected())
                    {
                        documentText = documentText + user.getAddress() + "	";
                    }
                    if (cBoxAddress2.isSelected())
                    {
                        documentText = documentText + user.getAddress2() + "	";
                    }
                    if (cBoxEmail.isSelected())
                    {
                        documentText = documentText + user.getEmail() + "	";
                    }
                    if (cBoxNationality.isSelected())
                    {
                        documentText = documentText + user.getNationality() + "	";
                    }
                    if (cBoxPhone.isSelected())
                    {
                        documentText = documentText + user.getPhoneNumber() + "	";
                    }
                    if (cBoxPhone2.isSelected())
                    {
                        documentText = documentText + user.getPhoneNumber2() + "	";
                    }
                    if (cBoxPhone3.isSelected())
                    {
                        documentText = documentText + user.getPhoneNumber3() + "	";
                    }
                    if (cBoxGender.isSelected())
                    {
                        documentText = documentText + user.getGender() + "	";
                    }
                    if (cBoxLastInput.isSelected())
                    {
                        documentText = documentText + user.getLastInputDate() + "	";
                    }
                    if (cBoxTotalHours.isSelected())
                    {
                        documentText = documentText + dp.getHoursWorkedForVolunteer(user) + "	";
                    }
                    documentText = documentText + "\n";
                    writer.write(documentText);
                    documentText = documentText = user.getFirstName() + "	" + user.getLastName() + "	";
                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
    }

    @FXML
    private void handleAllVolunteers(ActionEvent event)
    {
        initializeTable();
        comboBoxGuild.getItems().clear();
        comboContent();
    }

    private void handleCopyContent(KeyEvent event)
    {
        clipBoard.copySelectionToClipboard(tblViewInfo);
    }
}
