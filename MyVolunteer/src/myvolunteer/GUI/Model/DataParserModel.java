/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Model;

import java.util.List;
import myvolunteer.BE.Guild;
import myvolunteer.BE.User;
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

    public List<User> getUsers()
    {
        return bllFacade.getUsers();
    }

}
