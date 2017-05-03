/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Model;

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
    
    BLLFacade bllfacade = BLLFacade.getInstance();
    
    public void writeHoursToDatabase(int ID, int hours)
    {
        //reference to writeToDatabase method in BLL Facade
        bllfacade.writeHoursToDatabase(ID, hours);
    }
    
    

}
