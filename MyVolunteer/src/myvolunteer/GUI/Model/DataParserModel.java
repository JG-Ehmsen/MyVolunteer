/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Model;

import java.util.List;
import myvolunteer.BE.Guild;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;
import myvolunteer.BLL.BLLFacade;

/**
 *
 * @author Fjord82
 */
public class DataParserModel
{

    private static DataParserModel instance;

    MainViewModel mainViewModel = MainViewModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

    public static DataParserModel getInstance()
    {
        if (instance == null)
        {
            instance = new DataParserModel();
        }
        return instance;
    }

    private DataParserModel()
    {

    }

    BLLFacade bllFacade = BLLFacade.getInstance();

    /*
    Gets all active guilds
     */
    public List<Guild> getActiveGuilds()
    {
        List<Guild> activeGuilds = new ArrayList();
        List<Guild> allGuilds = bllFacade.getGuilds();

        for (Guild guild : allGuilds)
        {
            if (guild.isIsActive())
            {
                activeGuilds.add(guild);
            }
        }
        return activeGuilds;
    }

    /*
    Gets all guilds
     */
    public List<Guild> getAllGuilds()
    {
        return bllFacade.getGuilds();
    }

    public List<Guild> getGuildForManager(Manager manager)
    {
        List<Guild> returnList = new ArrayList();

        for (Integer i : manager.getManagerGuilds())
        {
            for (Guild guild : getActiveGuilds())
            {
                if (guild.getID() == i)
                {
                    returnList.add(guild);
                }
            }
        }
        return returnList;
    }

    public List<Volunteer> getActiveUsers()
    {
        List<Volunteer> activeUsers = new ArrayList();
        List<Volunteer> allUsers = bllFacade.getUsers();

        for (Volunteer user : allUsers)
        {
            if (user.isActive())
            {
                activeUsers.add(user);
            }
        }
        return activeUsers;
    }

    public List<Volunteer> getAllUsers()
    {
        return bllFacade.getUsers();
    }

    public List<Manager> getActiveManagers()
    {
        List<Manager> activeManagers = new ArrayList();
        List<Manager> allManagers = bllFacade.getManagers();

        for (Manager manager : allManagers)
        {
            if (manager.isActive())
            {
                activeManagers.add(manager);
            }
        }
        return activeManagers;
    }

    public List<Manager> getAllManagers()
    {
        return bllFacade.getManagers();
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date) throws SQLServerException
    {
        //reference to writeToDatabase method in BLL Facade
        bllFacade.writeHoursToDatabase(volunteer, hours, guild, date);
    }

    public void CreateNewUser(Volunteer user)
    {
        bllFacade.CreateNewUser(user);
    }

    public void CreateNewLaug(Guild guild, int MID)
    {
        bllFacade.CreateNewLaug(guild, MID);
    }

    public Manager getManagerForGuild(Guild guild)
    {
        return bllFacade.getManagerForGuild(guild);
    }

    public int getHoursWorkedForGuild(Guild guild)
    {
        return bllFacade.getHoursWorkedForGuild(guild);
    }

    public int getTotalHoursWorked()
    {
        return bllFacade.getTotalHoursWorked();
    }

    public int getHoursWorkedForVolunteer(Volunteer volunteer)
    {
        return bllFacade.getHoursWorkedForVolunteer(volunteer);
    }

    public void UpdateUser(Volunteer user)
    {
        bllFacade.UpdateUser(user);
    }

    public void UpdateManager(Manager manager)
    {
        bllFacade.UpdateManager(manager);
    }

    public void UpdateGuild(Guild guild, Manager manager, List<Integer> in, List<Integer> out)
    {
        bllFacade.UpdateGuild(guild, manager, in, out);
    }

    public void setGuildRelationStatus(Guild guild, Volunteer volunteer, boolean active)
    {
        bllFacade.setGuildRelationStatus(guild, volunteer, active);
    }

    public void changeGuildManager(Guild guild, Manager manager)
    {
        bllFacade.changeGuildManager(guild, manager);
    }

    public void CreateNewManager(Manager manager, String password)
    {
        bllFacade.CreateNewManager(manager, password);
    }

    public void tryLogin(String login, String pass, Stage stage)
    {
        Manager manager = bllFacade.getManagers(login, pass);

        if (manager != null)
        {
            if (!manager.isActive())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Inaktiv bruger");
                alert.setContentText("Denne bruger er deaktiveret. Kontakt en administrator.");

                alert.showAndWait();
            } else
            {
                mainViewModel.setLoggedInManager(manager);
                if (!manager.isAdmin())
                {
                    vcm.showManagerView(stage);
                } else
                {
                    vcm.showAdminView(stage);
                }
            }
        }
    }

    public void deactiveVolunteer(Volunteer volunteer)
    {
        bllFacade.deactiveVolunteer(volunteer);
    }

    public void setVolunteerStatus(Volunteer volunteer, boolean active)
    {
        bllFacade.setVolunteerStatus(volunteer, active);
    }

    public void deactivateGuild(Guild guild)
    {
        bllFacade.deactivateGuild(guild);
    }

    public void setGuildStatus(Guild guild, boolean active)
    {
        bllFacade.setGuildStatus(guild, active);
    }

    public void deactivateManager(Manager manager)
    {
        bllFacade.deactivateManager(manager);
    }

    public void setManagerStatus(Manager manager, boolean active)
    {
        bllFacade.setManagerStatus(manager, active);
    }

    public ObservableList<Volunteer> filter(String filter, List<Volunteer> allUsers)
    {
        return bllFacade.filter(filter, allUsers);
    }
}
