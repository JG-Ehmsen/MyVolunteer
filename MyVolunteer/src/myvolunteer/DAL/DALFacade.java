package myvolunteer.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.Date;
import java.util.List;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;

/**
 *
 * @author jeppe
 */
public class DALFacade
{

    // Private field for the Facade singleton instance.
    private static DALFacade instance;

    /**
     * Publicly accessible method for acquiring the singleton instance of the
     * class. Returns the instance of the class, and creates one if there
     * currently isn't one. Either case, returns a the singleton instance of
     * this class.
     *
     * @return DALFacade
     */
    public static DALFacade getInstance()
    {
        if (instance == null)
        {
            instance = new DALFacade();
        }
        return instance;
    }

    /**
     * Private constructor to ensure there will only be a single instance of
     * this class, adhering to the singleton design.
     */
    private DALFacade()
    {

    }

    DBUserAccess dbUserAccess = new DBUserAccess();

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date) throws SQLServerException
    {
        //reference to writeToDatabase method in DAL ConnectionManager
        db.writeHoursToDatabase(volunteer, hours, guild, date);
    }

    private DBTransactions db = new DBTransactions();

    public List<Guild> getGuilds()
    {
        return db.getGuilds();
    }

    public List<Volunteer> getUsers()
    {
        return db.getUsers();
    }

    public void CreateNewUser(Volunteer user)
    {
        db.CreateNewUser(user);
    }

    public void CreateNewLaug(Guild guild, int MID)
    {
        db.CreateNewLaug(guild, MID);
    }

    public int getDateID(Date date)
    {
        return db.getDateID(date);
    }

    public Manager getManagerForGuild(Guild guild)
    {
        return db.getManagerForGuild(guild);
    }

    public List<Manager> getManagers()
    {
        return db.getManagers();
    }

    public int getHoursWorkedForGuild(Guild guild)
    {
        return db.getHoursWorkedForGuild(guild);
    }

    public int getTotalHoursWorked()
    {
        return db.getTotalHoursWorked();
    }

    public int getHoursWorkedForVolunteer(Volunteer volunteer)
    {
        return db.getHoursWorkedForVolunteer(volunteer);
    }

    public void UpdateUser(Volunteer user)
    {
        db.UpdateUser(user);
    }
    
    public void UpdateManager(Manager manager)
    {
        db.UpdateManager(manager);
    }

    public void UpdateGuild(Guild guild, Manager manager, List<Integer> in, List<Integer> out)
    {
        db.UpdateGuild(guild, manager, in, out);
    }

    public void setGuildRelationStatus(Guild guild, Volunteer volunteer, boolean active)
    {
        db.setGuildRelationStatus(guild, volunteer, active);
    }

    public void changeGuildManager(Guild guild, Manager manager)
    {
        db.changeGuildManager(guild, manager);
    }

    public void CreateNewManager(Manager manager, String password)
    {
        db.CreateNewManager(manager, password);
    }

    public Manager loginQuery(String login, String pass)
    {
        return db.loginQuery(login, pass);
    }
    
    public void deactiveVolunteer(Volunteer volunteer)
    {
        db.deactiveVolunteer(volunteer);
    }
    
    public void setVolunteerStatus(Volunteer volunteer, boolean active)
    {
        db.setVolunteerStatus(volunteer, active);
    }
    
    public void deactivateGuild(Guild guild)
    {
        db.deactivateGuild(guild);
    }
    
    public void setGuildStatus(Guild guild, boolean active)
    {
        db.setGuildStatus(guild, active);
    }
    
    public void deactivateManager(Manager manager)
    {
        db.deactivateManager(manager);
    }
    
    public void setManagerStatus(Manager manager, boolean active)
    {
        db.setManagerStatus(manager, active);
    }
}
