/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Model;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myvolunteer.App;

/**
 *
 * @author Fjord82
 */
public class ViewChangerModel
{

    MainViewModel mainViewModel = MainViewModel.getInstance();

    ResourceBundle rb;

    public ViewChangerModel()
    {
        this.rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());
    }

    /*
    Changes the view - Has the window title and path as parameters (Strings)
     */
    private void changeView(String title, String path) throws IOException
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

    private void updateResourceBundle()
    {
        this.rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());
    }

    public void showCreateLaugView(Stage stage)
    {
        try
        {
            changeView("Opret Laug", "GUI/View/AddLaug.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showCreateManagerView(Stage stage)
    {
        try
        {
            changeView("Opret Manager", "GUI/View/AddManager.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showCreateVolunteerView(Stage stage)
    {
        try
        {
            changeView("Opret Frivillig", "GUI/View/AddVolunteer.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showInformationView(Stage stage)
    {
        try
        {
            changeView("Informations side", "GUI/View/AdminInfoView.fxml");
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showLoginView(Stage stage)
    {
        try
        {
            changeView(rb.getString("Login.text"), "GUI/View/AdminLogin.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showAdminView(Stage stage)
    {
        try
        {
            changeView("Admin", "GUI/View/AdminView.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showEditLaugView(Stage stage)
    {
        try
        {
            changeView("Rediger Laug", "GUI/View/EditLaug.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showEditManagerView(Stage stage)
    {
        try
        {
            changeView("Rediger Manager", "GUI/View/EditManager.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showEditVolunteerView(Stage stage)
    {
        try
        {
            changeView("Rediger Frivillig", "GUI/View/EditVolunteer.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showGuideView(Stage stage)
    {
        updateResourceBundle();
        try
        {
            changeView(rb.getString("Guide.text"), "GUI/View/GuideView.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showHoursInputView(Stage stage)
    {
        updateResourceBundle();
        try
        {
            changeView(rb.getString("Hours.text"), "GUI/View/HoursInputView.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showLaugSelectionView(Stage stage)
    {
        updateResourceBundle();
        try
        {
            changeView(rb.getString("Guild.text"), "GUI/View/LaugSelectionView.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showManagerContactListView(Stage stage)
    {
        try
        {
            changeView("Kontaktliste for frivillige", "GUI/View/ManagerContactListView.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showManagerView(Stage stage)
    {
        try
        {
            changeView("Manager", "GUI/View/ManagerView.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showVolunteersView(Stage stage)
    {
        updateResourceBundle();
        try
        {
            changeView(rb.getString("Volunteer.text"), "GUI/View/VolunteerView.fxml");
            stage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeView(Stage stage)
    {
        stage.close();
    }
}
