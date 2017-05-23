package myvolunteer.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;

public class DBTransactions
{

    private final ConnectionManager cm = new ConnectionManager();

    private final DBGuildAccess ga = new DBGuildAccess();
    private final DBUserAccess ua = new DBUserAccess();

    private Connection transactionConnection;

    /**
     * Attempts to start a new transaction, using a connection gotten from the
     * connection manager.
     */
    private void startTransaction()
    {
        try
        {
            transactionConnection = cm.getConnection();
            transactionConnection.setAutoCommit(false);
            transactionConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Attempts to commit a previously started transaction. If failed, will
     * rollback any changes made.
     */
    private void commitTransaction()
    {
        try
        {
            transactionConnection.commit();
        } catch (SQLException sqle)
        {
            System.out.println(sqle);
            try
            {
                transactionConnection.rollback();
            } catch (SQLException ex)
            {
                System.out.println(ex);
            }
        }
    }

    public List<Guild> getGuilds()
    {
        List<Guild> returnList = new ArrayList();
        try
        {
            startTransaction();

            returnList = ga.getGuilds(transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnList;
    }

    public List<Volunteer> getUsers()
    {
        List<Volunteer> returnList = new ArrayList();
        try
        {
            startTransaction();

            returnList = ua.getUsers(transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnList;
    }

    public void CreateNewUser(Volunteer user)
    {
        try
        {
            startTransaction();

            ua.CreateNewUser(user, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CreateNewLaug(Guild guild, int MID)
    {
        try
        {
            startTransaction();

            ga.CreateNewLaug(guild, MID, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getDateID(Date date)
    {
        int returnInt = 0;

        try
        {
            startTransaction();

            returnInt = ua.getDateID(date, transactionConnection);

            commitTransaction();

        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return returnInt;
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date)
    {
        try
        {
            startTransaction();

            ua.writeHoursToDatabase(volunteer, hours, guild, date, transactionConnection);

            commitTransaction();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Manager getManagerForGuild(Guild guild)
    {
        Manager manager;
        try
        {
            startTransaction();

            manager = ua.getManagerForGuild(guild, transactionConnection);

            commitTransaction();

            return manager;
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Manager> getManagers()
    {
        List<Manager> returnList = new ArrayList<>();
        try
        {
            startTransaction();

            returnList = ua.getManagers(transactionConnection);

            commitTransaction();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return returnList;
    }

    public int getHoursWorkedForGuild(Guild guild)
    {
        int returnInt = 0;
        try
        {
            startTransaction();

            returnInt = ga.getHoursWorkedForGuild(guild, transactionConnection);

            commitTransaction();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnInt;
    }

    public int getTotalHoursWorked()
    {
        int returnInt = 0;
        try
        {
            startTransaction();

            returnInt = ga.getTotalHoursWorked(transactionConnection);

            commitTransaction();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnInt;
    }

    public int getHoursWorkedForVolunteer(Volunteer volunteer)
    {
        int returnInt = 0;
        try
        {
            startTransaction();

            returnInt = ua.getHoursWorkedForVolunteer(volunteer, transactionConnection);

            commitTransaction();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnInt;
    }

    public void UpdateUser(Volunteer user)
    {
        try
        {
            startTransaction();

            ua.UpdateUser(user, transactionConnection);

            commitTransaction();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void UpdateManager(Manager manager)
    {
        try
        {
            startTransaction();

            ua.UpdateManager(manager, transactionConnection);

            commitTransaction();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void UpdateGuild(Guild guild, Manager manager, List<Integer> in, List<Integer> out)
    {
        try
        {
            startTransaction();

            ga.UpdateGuild(guild, manager, in, out, transactionConnection);

            commitTransaction();

        } catch (SQLServerException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CreateNewManager(Manager manager, String password)
    {
        try
        {
            startTransaction();
            ua.CreateNewManager(manager, password, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Manager loginQuery(String login, String pass)
    {
        try
        {

            Manager manager;

            manager = ua.loginQuery(login, pass, transactionConnection);

            commitTransaction();
            return manager;
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public void setGuildRelationStatus(Guild guild, Volunteer volunteer, boolean active)
    {
        try
        {
            startTransaction();

            ga.setGuildRelationStatus(guild.getID(), volunteer.getId(), active, transactionConnection);

            commitTransaction();

        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeGuildManager(Guild guild, Manager manager)
    {
        try
        {
            startTransaction();

            ga.changeGuildManager(guild, manager, transactionConnection);

            commitTransaction();

        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deactiveVolunteer(Volunteer volunteer)
    {
        try
        {
            startTransaction();

            ua.deactivateVolunteer(volunteer, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVolunteerStatus(Volunteer volunteer, boolean active)
    {
        try
        {
            startTransaction();

            ua.setVolunteerStatus(volunteer, active, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deactivateGuild(Guild guild)
    {
        try
        {
            startTransaction();

            ga.deactivateGuild(guild, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGuildStatus(Guild guild, boolean active)
    {
        try
        {
            startTransaction();

            ga.setGuildStatus(guild, active, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deactivateManager(Manager manager)
    {
        try
        {
            startTransaction();

            ua.deactivateManager(manager, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setManagerStatus(Manager manager, boolean active)
    {
        try
        {
            startTransaction();

            ua.setManagerStatus(manager, active, transactionConnection);

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
