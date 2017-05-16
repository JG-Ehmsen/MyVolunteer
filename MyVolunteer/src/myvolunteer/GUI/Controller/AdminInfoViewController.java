/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import myvolunteer.BE.Manager;
import myvolunteer.BE.User;
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
    private List<Volunteer> userList = new ArrayList<>();
    ObservableList<Volunteer> users = FXCollections.observableArrayList();

    /**
     * Gets the singleton instances.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    private Guild lastSelectedGuild;
    private Manager lastManager;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        guildList = dp.getGuilds();
        comboContent();
        loadTableView();
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
        lastManager = dp.getManagerForGuild(lastSelectedGuild);
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
            tblColumnName.setCellValueFactory(cellData -> cellData.getValue().getFNameProperty());
            tblColumnLName.setCellValueFactory(celldata -> celldata.getValue().getLNameProperty());
            tblColumnPhone.setCellValueFactory(celldata -> celldata.getValue().getPhoneProperty());
            tblColumnMail.setCellValueFactory(celldata -> celldata.getValue().getMailProperty());

            ObservableList nameArrayList = FXCollections.observableArrayList(dp.getUsers());
            tblViewInfo.setItems(nameArrayList);
        } else
        {
            {
                ObservableList<Volunteer> guildUsers = FXCollections.observableArrayList(dp.getUsers());
                for (Integer i : lastSelectedGuild.getMemberList())
                {
                    for (Volunteer user : userList)
                    {
                        if (user.getId() == i)
                        {
                            guildUsers.add(user);
                        }
                    }
                }
                users.setAll(guildUsers);
            }
            System.out.println(users.toString());
            tblViewInfo.setItems(users);
        }
    }
}
