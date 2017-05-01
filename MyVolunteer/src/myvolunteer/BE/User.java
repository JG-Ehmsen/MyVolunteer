package myvolunteer.BE;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User
{

    protected final int id;
    protected StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty profileImage = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    protected StringProperty phoneNumber = new SimpleStringProperty();
    //protected Image image = new BufferedImage(100, 100, 0);

    public User(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public void setFirstName(String text)
    {
        firstName.set(text);
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public void setLastName(String text)
    {
        lastName.set(text);
    }

    public String getProfileImage()
    {
        return profileImage.get();
    }

    public void setProfileImage(String value)
    {
        profileImage.set(value);
    }

    public String getEmail()
    {
        return email.get();
    }

    public void setEmail(String text)
    {
        email.set(text);
    }

    public String getPhoneNumber()
    {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String value)
    {
        phoneNumber.set(value);
    }

//    public Image getImage()
//    {
//        return image;
//    }
//
//    public void setImage(Image image)
//    {
//        this.image = image;
//    }
}
