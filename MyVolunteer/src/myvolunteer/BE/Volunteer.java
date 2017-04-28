/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Volunteer extends User
{
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty nationality = new SimpleStringProperty();
    private StringProperty lastInputDate = new SimpleStringProperty();
    
    private boolean isActive;

    public Volunteer(int id, String name, String phoneNumber)
    {
        super(id);
        this.firstName.set(name);
        this.phoneNumber.set(phoneNumber);
    }
    
    public String getGender()
    {
        return gender.get();
    }
    
    public void setGender(String value)
    {
        gender.set(value);
    }
    
    public String getNationality()
    {
        return nationality.get();
    }
    
    public void setNationality(String value)
    {
        nationality.set(value);
    }
    
    public String getLastInputDate()
    {
        return lastInputDate.get();
    }
    
    public void setLastInputDate(String info)
    {
        lastInputDate.set(info);
    }
    
    public boolean isActive()
    {
        return isActive;
    }
    
    public void setIsActive(boolean status)
    {
        this.isActive = status;
    }
    
    
}
