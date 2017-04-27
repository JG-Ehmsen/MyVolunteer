/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeppjleemoritzled
 */
public class ConnectionManager
{

    private final SQLServerDataSource ds
            = new SQLServerDataSource();

    String DB_Properties = "src/myvolunteer/DAL/Resource/DB.cfg";

    public ConnectionManager()
    {
        setupDB();
    }

    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }

    public void setupDB()
    {
        try
        {
            FileReader fr;
            try
            {
                fr = new FileReader(DB_Properties);
                Properties prop = new Properties();
                prop.load(fr);

                ds.setDatabaseName(prop.getProperty("SERVER"));

                ds.setUser(prop.getProperty("USER"));
                ds.setPassword(prop.getProperty("PASS"));

                ds.setServerName(prop.getProperty("IP"));
                ds.setPortNumber(Integer.parseInt(prop.getProperty("PORT")));
            } catch (FileNotFoundException ex)
            {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
