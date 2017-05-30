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
    private Button btnSave;

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
                String header = "Køn" + "	" + "Fornavn" + "	" + "Efternavn" + "	" + "Email" + "	" + "Telefon" + "	" + "Telefon2" + "	" + "Telefon3" + "	" + "Nationalitet" + "	" + "Adresse" + "	" + "Adresse2" + "\n";
                writer.write(header);
                for (Volunteer user : tblViewInfo.getItems())
                {
                    String text = user.getGender() + "	" + user.getFirstName() + "	" + user.getLastName() + "	" + user.getEmail() + "	" + user.getPhoneNumber() + "	" + user.getPhoneNumber2() + "	" + user.getPhoneNumber3() + "	" + user.getNationality() + "	" + user.getAddress() + "	" + user.getAddress2() + "\n";

                    writer.write(text);
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

    @FXML
    private void handleCopyContent(KeyEvent event)
    {
        clipBoard.copySelectionToClipboard(tblViewInfo);
    }
}
