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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author Kristoffers
 */
public class AdminInfoViewController implements Initializable
{

    private List<Guild> guildList = new ArrayList<>();
    ObservableList<Volunteer> users = FXCollections.observableArrayList();

    /**
     * Gets the singleton instances.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    private Guild lastSelectedGuild;
    private Volunteer lastSelectedVolunteer;

    @FXML
    private Button btnGodkend;
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
    private TableColumn<?, ?> tblColumnLaug;
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        guildList = dp.getGuilds();
        initializeTable();
        comboContent();
    }

    @FXML
    private void handleGodkend(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Admin", "GUI/View/AdminView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnGodkend.getScene().getWindow();
        stage.close();
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
                    for (Volunteer user : dp.getUsers())
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
        tblColumnName.setCellValueFactory(cellData -> cellData.getValue().getFNameProperty());
        tblColumnLName.setCellValueFactory(celldata -> celldata.getValue().getLNameProperty());
        tblColumnPhone.setCellValueFactory(celldata -> celldata.getValue().getPhoneProperty());
        tblColumnMail.setCellValueFactory(celldata -> celldata.getValue().getMailProperty());

        ObservableList nameArrayList = FXCollections.observableArrayList(dp.getUsers());
        tblViewInfo.setItems(nameArrayList);
    }

    @FXML
    private void handleSave(ActionEvent event) throws Exception
    {
        writeExcel();
    }

    /*
    Exports the data in the tableView to a .xls (Excel) file.
     */
    private void writeExcel() throws Exception
    {
        Writer writer = null;
        try
        {
            Date todaysDate = new Date();
            SimpleDateFormat ft
                    = new SimpleDateFormat("dd.MM.yyyy");
            File file = new File("Laug information" + " " + ft.format(todaysDate) + ".xls");
            writer = new BufferedWriter(new FileWriter(file));
            for (Volunteer user : tblViewInfo.getItems())
            {
                String text = user.getFirstName() + ", " + user.getLastName() + ", " + user.getEmail() + ", " + user.getPhoneNumber() + "\n";

                writer.write(text);
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            writer.flush();
            writer.close();
        }
    }
}
