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
import myvolunteer.GUI.Model.ViewChangerModel;
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
    ViewChangerModel vcm = new ViewChangerModel();

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
        populateList();
    }

    @FXML
    private void handleBack(ActionEvent event)
    {
        vcm.showManagerView((Stage) btnBack.getScene().getWindow());
    }

    private void initializeTable()
    {
        tblColumnName.setCellValueFactory(cellData -> cellData.getValue().getFNameProperty());
        tblColumnPhone1.setCellValueFactory(cellDate -> cellDate.getValue().getPhoneProperty());
        tblColumnPhone2.setCellValueFactory(cellDate -> cellDate.getValue().getPhone2Property());
        tblColumnMail.setCellValueFactory(cellDate -> cellDate.getValue().getMailProperty());
        tblViewContact.getSelectionModel().setCellSelectionEnabled(true);
        tblViewContact.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ClipBoardUtility.installCopyPasteHandler(tblViewContact);

    }

    private void populateList()
    {
        List<Integer> IDList = new ArrayList();
        List<Volunteer> volunteerList = new ArrayList();
        List<Guild> managerGuildList = dp.getGuildForManager(mainViewModel.getLoggedInManager());

        for (Guild guild : managerGuildList)
        {
            for (Integer i : guild.getMemberList())
            {
                if (!IDList.contains(i))
                {
                    IDList.add(i);
                }
            }
        }

        for (Integer integer : IDList)
        {
            for (Volunteer volunteer : dp.getActiveUsers())
            {
                if (integer == volunteer.getId())
                {

                    volunteerList.add(volunteer);

                }
            }
        }
        users.setAll(volunteerList);
        tblViewContact.setItems(users);
    }

    @FXML
    private void searchFilter(KeyEvent event)
    {
        String filter = searchBar.getText();
        tblViewContact.setItems(dp.filter(filter, users));
    }

    @FXML
    private void handleCopyContent(KeyEvent event)
    {
    }
}
