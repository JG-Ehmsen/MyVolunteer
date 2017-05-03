package myvolunteer.DAL;

import java.util.List;
import myvolunteer.BE.Guild;
import myvolunteer.BE.User;

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
    
    ConnectionManager connectionManager = new ConnectionManager();
    
    public void writeHoursToDatabase(int ID, int hours)
    {
        //reference to writeToDatabase method in DAL ConnectionManager
        connectionManager.writeHoursToDatabase(ID, hours);
    }

    private DBTransactions db = new DBTransactions();

    public List<Guild> getGuilds()
    {
        return db.getGuilds();
    }

    public List<User> getUsers()
    {
        return db.getUsers();
    }
}
