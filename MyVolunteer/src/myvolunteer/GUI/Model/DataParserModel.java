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

    public List<Manager> getManagers()
    {
        return bllFacade.getManagers();
    }

    public void UpdateUser(Volunteer user)
    {
        bllFacade.UpdateUser(user);
    }

    public void UpdateGuild(Guild guild)
    {
        bllFacade.UpdateGuild(guild);
    }

    public void setGuildRelationStatus(Guild guild, Volunteer volunteer, boolean active)
    {
        bllFacade.setGuildRelationStatus(guild, volunteer, active);
    }

    public void changeGuildManager(Guild guild, Manager manager)
    {
        bllFacade.changeGuildManager(guild, manager);
    }
}
