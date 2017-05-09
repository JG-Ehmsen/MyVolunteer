/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Model;

import java.util.List;
import myvolunteer.BE.Guild;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.Date;
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

    public List<Guild> getGuilds()
    {
        return bllFacade.getGuilds();
    }

    public List<Volunteer> getUsers()
    {
        return bllFacade.getUsers();
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
    
    public void CreateNewLaug(Guild guild)
    {
        bllFacade.CreateNewLaug(guild);
    }
    
        public Manager getManagerForGuild(Guild guild)
    {
        return bllFacade.getManagerForGuild(guild);
    }

}
