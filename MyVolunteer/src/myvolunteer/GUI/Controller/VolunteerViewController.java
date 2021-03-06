package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Model.ViewChangerModel;
import myvolunteer.GUI.Utility.PictureButton;

public class VolunteerViewController implements Initializable
{

    /**
     * Gets the singleton instance of the MainViewModel, DateParser and
     * ViewChangerModel.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

    Guild guild;
    Manager manager;

    @FXML
    private Button btnBack;
    @FXML
    private FlowPane MainFlowPane;
    @FXML
    private Label guildNameLbl;
    @FXML
    private Label contactNameLbl;
    @FXML
    private ScrollPane laugScroll;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        guild = mainViewModel.getLastSelectedGuild();
        manager = dp.getManagerForGuild(guild);

        guildNameLbl.setText(guild.getName());
        contactNameLbl.setText("Kontaktperson \n" + manager.getFirstName() + " " + manager.getLastName()
                + "\n" + manager.getPhoneNumber() + "\n" + manager.getEmail());
        generateButtons();
        adjustScrollPane();
        changeLanguage();
    }

    private void generateButtons()
    {
        MainFlowPane.setVgap(20);
        MainFlowPane.setHgap(30);
        for (Volunteer u : dp.getActiveUsers())
        {
            for (Integer i : guild.getMemberList())
            {
                if (u.getId() == i)
                {

                    PictureButton button = new PictureButton(u);
                    button.setOnAction(new EventHandler()
                    {
                        @Override
                        public void handle(Event event)
                        {
                            mainViewModel.setLastSelectedUser(button.getUser());
                            handleUserImage();
                        }
                    }
                    );
                    MainFlowPane.getChildren().add(button);
                }
            }
        }
    }

    private void adjustScrollPane()
    {
        laugScroll.viewportBoundsProperty().addListener(new ChangeListener<Bounds>()
        {

            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds)
            {
                MainFlowPane.setPrefWidth(bounds.getWidth());
                MainFlowPane.setPrefHeight(bounds.getHeight());
                laugScroll.setFitToHeight(true);
            }
        });

    }

    private void handleUserImage()
    {
        vcm.showHoursInputView((Stage) MainFlowPane.getScene().getWindow());
    }

    @FXML
    private void handleGoBack(ActionEvent event) throws IOException
    {
        vcm.showLaugSelectionView((Stage) MainFlowPane.getScene().getWindow());
    }

    private void changeLanguage()
    {
        ResourceBundle rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());
        btnBack.setText(rb.getString("VolunteerView.btnBack.text"));
        contactNameLbl.setText(rb.getString("VolunteerView.contactNameLbl.text") + "\n" + manager.getFirstName() + " " + manager.getLastName()
                + "\n" + manager.getPhoneNumber() + "\n" + manager.getEmail());
    }

}
