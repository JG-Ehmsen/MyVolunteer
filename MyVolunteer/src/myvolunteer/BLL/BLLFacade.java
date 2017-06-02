package myvolunteer.BLL;

import java.util.List;
import myvolunteer.BE.Guild;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.Date;
import javafx.collections.ObservableList;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;
import myvolunteer.DAL.DALFacade;

/**
 *
 * @author jeppe
 */
public class BLLFacade
{

    // Private field for the Facade singleton instance.
    private static BLLFacade instance;

    /**
     * Publicly accessible method for acquiring the singleton instance of the
     * class. Returns the instance of the class, and creates one if there
     * currently isn't one. Either case, returns a the singleton instance of
     * this class.
     *
     * @return BLLFacade
     */
    public static BLLFacade getInstance()
    {
        if (instance == null)
        {
            instance = new BLLFacade();
        }
        return instance;
    }

    /**
     * Private constructor to ensure there will only be a single instance of
     * this class, adhering to the singleton design.
     */
    private BLLFacade()
    {

    }

    DALFacade dalFacade = DALFacade.getInstance();

    ListFiltering filtering = new ListFiltering();

    public List<Guild> getGuilds()
    {
        return dalFacade.getGuilds();
    }

    public List<Volunteer> getUsers()
    {
        return dalFacade.getUsers();
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date) throws SQLServerException
    {
        //reference to writeToDatabase method in DAL Facade
        dalFacade.writeHoursToDatabase(volunteer, hours, guild, date);
    }

    public void CreateNewUser(Volunteer user)
    {
        dalFacade.CreateNewUser(user);
    }

    public void CreateNewLaug(Guild guild, int MID)
    {
        dalFacade.CreateNewLaug(guild, MID);
    }

    public Manager getManagerForGuild(Guild guild)
    {
        return dalFacade.getManagerForGuild(guild);
    }

    public List<Manager> getManagers()
    {
        return dalFacade.getManagers();
    }

    public int getHoursWorkedForGuild(Guild guild)
    {
        return dalFacade.getHoursWorkedForGuild(guild);
    }

    public int getTotalHoursWorked()
    {
        return dalFacade.getTotalHoursWorked();
    }

    public int getHoursWorkedForVolunteer(Volunteer volunteer)
    {
        return dalFacade.getHoursWorkedForVolunteer(volunteer);
    }

    public void UpdateUser(Volunteer user)
    {
        dalFacade.UpdateUser(user);
    }

    public void UpdateManager(Manager manager)
    {
        dalFacade.UpdateManager(manager);
    }

    public void UpdateGuild(Guild guild, Manager manager, List<Integer> in, List<Integer> out)
    {
        dalFacade.UpdateGuild(guild, manager, in, out);
    }

    public void CreateNewManager(Manager manager, String password)
    {
        dalFacade.CreateNewManager(manager, password);
    }

    public Manager getManagers(String login, String pass)
    {
        return dalFacade.loginQuery(login, pass);
    }

    public void setGuildRelationStatus(Guild guild, Volunteer volunteer, boolean active)
    {
        dalFacade.setGuildRelationStatus(guild, volunteer, active);
    }

    public void changeGuildManager(Guild guild, Manager manager)
    {
        dalFacade.changeGuildManager(guild, manager);
    }

    public void deactiveVolunteer(Volunteer volunteer)
    {
        dalFacade.deactiveVolunteer(volunteer);
    }

    public void setVolunteerStatus(Volunteer volunteer, boolean active)
    {
        dalFacade.setVolunteerStatus(volunteer, active);
    }

    public void deactivateGuild(Guild guild)
    {
        dalFacade.deactivateGuild(guild);
    }

    public void setGuildStatus(Guild guild, boolean active)
    {
        dalFacade.setGuildStatus(guild, active);
    }

    public void deactivateManager(Manager manager)
    {
        dalFacade.deactivateManager(manager);
    }

    public void setManagerStatus(Manager manager, boolean active)
    {
        dalFacade.setManagerStatus(manager, active);
    }

    public ObservableList<Volunteer> filter(String filter, List<Volunteer> allUsers)
    {
        return filtering.filter(filter, allUsers);
    }

}
