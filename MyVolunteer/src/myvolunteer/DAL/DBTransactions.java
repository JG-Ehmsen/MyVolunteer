package myvolunteer.DAL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myvolunteer.BE.Guild;
import myvolunteer.BE.User;

/**
 *
 * @author jeppe
 */
public class DBTransactions
{

    private final ConnectionManager cm = new ConnectionManager();

    private final DBGuildAccess ga = new DBGuildAccess();
    private final DBUserAccess ua = new DBUserAccess();

    private Connection transactionConnection;

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

            returnList = ga.getGuilds(cm.getConnection());

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnList;
    }
    
    public List<User> getUsers()
    {
        List<User> returnList = new ArrayList();
        try
        {
            startTransaction();

            returnList = ua.getUsers(cm.getConnection());

            commitTransaction();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnList;
    }

}
