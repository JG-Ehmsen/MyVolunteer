package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import myvolunteer.BE.Guild;
import myvolunteer.GUI.Model.DataParserModel;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Utility.PictureButton;

/**
 * FXML Controller class
 *
 * @author jeppe
 */
public class LaugViewController implements Initializable
{

    /**
     * Gets the singleton instance of the model.
     */
    MainViewModel mainViewModel = MainViewModel.getInstance();
    DataParserModel dp = DataParserModel.getInstance();

    private Locale german = new Locale("de", "DE");
    private Locale danish = new Locale("da", "DK");
    private Locale english = new Locale("en", "GB");

    @FXML
    private Button btnLogin;
    @FXML
    private FlowPane MainFlowPane;
    @FXML
    private ButtonBar languageBtnBar;
    @FXML
    private Button danishBtn;
    @FXML
    private Button germanBtn;
    @FXML
    private Button englishBtn;
    @FXML
    private Button guideBtn;
    @FXML
    private ScrollPane laugScroll;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        MainFlowPane.setVgap(20);
        MainFlowPane.setHgap(50);

        initGuildButtons();
        adjustScrollPane();
        setBundleLocale();
    }

    private void initGuildButtons()
    {
        for (Guild g : dp.getActiveGuilds())
        {
            PictureButton b = new PictureButton(g);

            b.setOnAction(new EventHandler()
            {
                @Override
                public void handle(Event event)
                {
                    mainViewModel.setLastSelectedGuild(b.getGuild());
                    handleGuildClick();
                }
            }
            );
            MainFlowPane.getChildren().add(b);
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

    private void handleGuildClick()
    {
        try
        {
            if (mainViewModel.getLastSelectedLocale().toString().equals("da_DK"))
            {
                mainViewModel.changeView("Frivillig", "GUI/View/VolunteerView.fxml");
            } else if (mainViewModel.getLastSelectedLocale().toString().equals("de_DE"))
            {
                mainViewModel.changeView("Sich freiwillig melden", "GUI/View/VolunteerView.fxml");
            } else if (mainViewModel.getLastSelectedLocale().toString().equals("en_GB"))
            {
                mainViewModel.changeView("Volunteer", "GUI/View/VolunteerView.fxml");
            }

            // Closes the primary stage
            Stage stage = (Stage) MainFlowPane.getScene().getWindow();
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(LaugViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Login", "GUI/View/AdminLogin.fxml");

        // Closes the primary stage
        Stage stage = (Stage) MainFlowPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleGuide(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Step-by-Step guide", "GUI/View/GuideView.fxml");

        // Closes the primary stage
        Stage stage = (Stage) MainFlowPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleLanguageDan(ActionEvent event)
    {
        mainViewModel.setLastSelectedLocale(danish);
        mainViewModel.setLastSelectedBundle("myvolunteer.GUI.Utility.MyLanguage");
        englishBtn.setDisable(false);
        germanBtn.setDisable(false);
        danishBtn.setDisable(true);
    }

    @FXML
    private void handleLanguageGer(ActionEvent event)
    {
        mainViewModel.setLastSelectedLocale(german);
        mainViewModel.setLastSelectedBundle("myvolunteer.GUI.Utility.MyLanguage_de_DE");
        englishBtn.setDisable(false);
        germanBtn.setDisable(true);
        danishBtn.setDisable(false);
    }

    @FXML
    private void handleLanguageEng(ActionEvent event)
    {
        mainViewModel.setLastSelectedLocale(english);
        mainViewModel.setLastSelectedBundle("myvolunteer.GUI.Utility.MyLanguage_en_GB");
        englishBtn.setDisable(true);
        germanBtn.setDisable(false);
        danishBtn.setDisable(false);
    }

    private void setBundleLocale()
    {
        ResourceBundle rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());

        if (mainViewModel.getLastSelectedLocale().equals(german))
        {
            englishBtn.setDisable(false);
            germanBtn.setDisable(true);
            danishBtn.setDisable(false);
        }
        if (mainViewModel.getLastSelectedLocale().equals(english))
        {
            englishBtn.setDisable(true);
            germanBtn.setDisable(false);
            danishBtn.setDisable(false);
        }
        if (mainViewModel.getLastSelectedLocale().equals(danish))
        {
            englishBtn.setDisable(false);
            germanBtn.setDisable(false);
            danishBtn.setDisable(true);
        }
    }
}
