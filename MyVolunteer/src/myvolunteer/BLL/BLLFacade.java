package myvolunteer.BLL;

import java.util.List;
import myvolunteer.BE.Guild;
import myvolunteer.BE.User;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.Date;
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

    public List<Guild> getGuilds()
    {
        return dalFacade.getGuilds();
    }

    public List<User> getUsers()
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
    
    public void CreateNewLaug(Guild guild)
    {
        dalFacade.CreateNewLaug(guild);
    }
    
}
