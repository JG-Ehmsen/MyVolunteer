package myvolunteer.GUI.Model;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myvolunteer.App;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;

/**
 *
 * @author jeppe
 */
public class MainViewModel
{

    /**
     * Ensures that the class can be used as a singleton, by making a static
     * instance of it, ensuring that the constructor is private, and having a
     * method that either returns the static instance if it exists, or makes a
     * new one.
     */
    private static MainViewModel instance;

    public static MainViewModel getInstance()
    {

        if (instance == null)
        {
            instance = new MainViewModel();
        }
        return instance;
    }

    private MainViewModel()
    {

    }

    private Volunteer lastSelectedUser;
    private Guild lastSelectedGuild;
    private Manager loggedInManager;

    private String lastSelectedBundle = "myvolunteer.GUI.Utility.MyLanguage";
    private Locale lastSelectedLocale = new Locale("da", "DK");
    private String bundle = "myvolunteer.GUI.Utility.MyLanguage";
    private String bundleDE = "myvolunteer.GUI.Utility.MyLanguage_de_DE";
    private String bundleEN = "myvolunteer.GUI.Utility.MyLanguage_en_GB";
    private String btn = "LaugViewSpecial.btnLogin.text";
    ResourceBundle rb = ResourceBundle.getBundle(bundle, lastSelectedLocale);

    public void changeView(String title, String path) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource(path));
        Pane page = (Pane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setTitle(title);

        //dialogStage.setOnCloseRequest(value);
        dialogStage.show();
    }

    public Volunteer getLastSelectedUser()
    {
        return lastSelectedUser;
    }

    public void setLastSelectedUser(Volunteer lastSelectedUser)
    {
        this.lastSelectedUser = lastSelectedUser;
    }

    public Guild getLastSelectedGuild()
    {
        return lastSelectedGuild;
    }

    public void setLastSelectedGuild(Guild lasSelectedGuild)
    {
        this.lastSelectedGuild = lasSelectedGuild;
    }

    public Manager getLoggedInManager()
    {
        return loggedInManager;
    }

    public void setLoggedInManager(Manager loggedInManager)
    {
        this.loggedInManager = loggedInManager;
    }

    public Locale getLastSelectedLocale()
    {
        return lastSelectedLocale;
    }

    public void setLastSelectedLocale(Locale lastSelectedLocale)
    {
        this.lastSelectedLocale = lastSelectedLocale;
    }

    public String getLastSelectedBundle()
    {
        return lastSelectedBundle;
    }

    public void setLastSelectedBundle(String lastSelectedBundle)
    {
        this.lastSelectedBundle = lastSelectedBundle;
    }

}
