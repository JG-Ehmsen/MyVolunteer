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
import myvolunteer.GUI.Model.ViewChangerModel;
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
    ViewChangerModel vcm = new ViewChangerModel();

    ResourceBundle rb;

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

        rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());

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
        vcm.showVolunteersView((Stage) MainFlowPane.getScene().getWindow());
    }

    @FXML
    private void handleGoToLogin(ActionEvent event) throws IOException
    {
        vcm.showLoginView((Stage) MainFlowPane.getScene().getWindow());
    }

    @FXML
    private void handleGoToGuide(ActionEvent event) throws IOException
    {
        vcm.showGuideView((Stage) MainFlowPane.getScene().getWindow());
    }

    @FXML
    private void handleLanguageDan(ActionEvent event)
    {
        mainViewModel.setLastSelectedLocale(danish);
        mainViewModel.setLastSelectedBundle("myvolunteer.GUI.Utility.MyLanguage");
        updateLanguage();
        englishBtn.setDisable(false);
        germanBtn.setDisable(false);
        danishBtn.setDisable(true);
    }

    @FXML
    private void handleLanguageGer(ActionEvent event)
    {
        mainViewModel.setLastSelectedLocale(german);
        mainViewModel.setLastSelectedBundle("myvolunteer.GUI.Utility.MyLanguage_de_DE");
        updateLanguage();
        englishBtn.setDisable(false);
        germanBtn.setDisable(true);
        danishBtn.setDisable(false);
    }

    @FXML
    private void handleLanguageEng(ActionEvent event)
    {
        mainViewModel.setLastSelectedLocale(english);
        mainViewModel.setLastSelectedBundle("myvolunteer.GUI.Utility.MyLanguage_en_GB");
        updateLanguage();
        englishBtn.setDisable(true);
        germanBtn.setDisable(false);
        danishBtn.setDisable(false);

    }

    private void setBundleLocale()
    {
        rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());

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

    private void updateLanguage()
    {
        rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());

        Stage stage = (Stage) MainFlowPane.getScene().getWindow();
        stage.setTitle(rb.getString("Guild.text"));
    }
}
