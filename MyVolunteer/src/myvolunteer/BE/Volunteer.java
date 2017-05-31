package myvolunteer.BE;

import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Volunteer extends User
{

    private StringProperty gender = new SimpleStringProperty();
    private StringProperty nationality = new SimpleStringProperty();
    private Date lastInputDate = new Date();
    private StringProperty note = new SimpleStringProperty();
    private StringProperty lastInput = new SimpleStringProperty();
    private int totalHours;

    public StringProperty getTotalHoursProperty()
    {
        StringProperty totalHoursProperty = new SimpleStringProperty();
        totalHoursProperty.set(Integer.toString(totalHours));

        return totalHoursProperty;
    }

    public void setTotalHours(int totalHours)
    {
        this.totalHours = totalHours;
    }

    public StringProperty getLastInputProperty()
    {
        this.lastInput.set(lastInputDate.toString());
        return lastInput;
    }

    public StringProperty getLastInput()
    {
        return lastInput;
    }

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

    public StringProperty getGenderProperty()
    {
        return this.gender;
    }

    public String getNationality()
    {
        return nationality.get();
    }

    public StringProperty getNationalityProperty()
    {
        return this.nationality;
    }

    public void setNationality(String value)
    {
        nationality.set(value);
    }

    public Date getLastInputDate()
    {
        return lastInputDate;
    }

    public void setLastInputDate(Date date)
    {
        lastInputDate = date;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setIsActive(boolean status)
    {
        this.isActive = status;
    }

    public void setNote(String note)
    {
        this.note.set(note);
    }

    public String getNote()
    {
        return note.get();
    }
}
