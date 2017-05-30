/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Utility.ClipBoardUtility;

/**
 * FXML Controller class
 *
 * @author Fjord82
 */
public class ManagerContactListViewController implements Initializable
{

    DataParserModel dp = DataParserModel.getInstance();
    MainViewModel mainViewModel = MainViewModel.getInstance();
    ClipBoardUtility clipBoard = new ClipBoardUtility();

    ObservableList<Volunteer> users = FXCollections.observableArrayList();
    private List<Guild> guildList = new ArrayList<>();

    @FXML
    private TableView<Volunteer> tblViewContact;
    @FXML
    private TableColumn<Volunteer, String> tblColumnName;
    @FXML
    private TableColumn<Volunteer, String> tblColumnPhone1;
    @FXML
    private TableColumn<Volunteer, String> tblColumnPhone2;
    @FXML
    private TableColumn<Volunteer, String> tblColumnMail;
    @FXML
    private Button btnBack;
    @FXML
    private TextField searchBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        guildList = dp.getActiveGuilds();
        initializeTable();
        
        tblViewContact.getSelectionModel().setCellSelectionEnabled(true);
        tblViewContact.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleBack(ActionEvent event)
    {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    private void initializeTable()
    {
        tblColumnName.setCellValueFactory(cellData -> cellData.getValue().getFNameProperty());
        tblColumnPhone1.setCellValueFactory(cellDate -> cellDate.getValue().getPhoneProperty());
        tblColumnPhone2.setCellValueFactory(cellDate -> cellDate.getValue().getPhone2Property());
        tblColumnMail.setCellValueFactory(cellDate -> cellDate.getValue().getMailProperty());

        ObservableList nameArrayList = FXCollections.observableArrayList(dp.getActiveUsers());
        tblViewContact.setItems(nameArrayList);
    }

    @FXML
    private void searchFilter(KeyEvent event)
    {
        String filter = searchBar.getText();
        ObservableList<Volunteer> filteredList = FXCollections.observableArrayList();
        if (filter.equals(""))
        {
            tblViewContact.setItems(users);
        } else
        {
            for (Volunteer vol : users)
            {
                if (vol.toString().toLowerCase().contains(filter.toLowerCase()))
                {
                    filteredList.add(vol);
                }
            }
            tblViewContact.setItems(filteredList);
        }
    }

    @FXML
    private void handleCopyContent(KeyEvent event)
    {
        clipBoard.copySelectionToClipboard(tblViewContact);
    }
}
