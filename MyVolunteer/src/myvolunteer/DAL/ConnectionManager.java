/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

/**
 *
 * @author jeppjleemoritzled
 */
public class ConnectionManager
{
    private final SQLServerDataSource ds =
            new SQLServerDataSource();

    public ConnectionManager()
    {
        setupDB();
    }
    
    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }
    
    private void setupDB()
    {
        ds.setDatabaseName("CS2016B_12_Volunteer");
        
        ds.setUser("CS2016B_12");
        ds.setPassword("CS2016B_12");
        
        ds.setServerName("10.176.111.31");
        ds.setPortNumber(1433);
    }
}
