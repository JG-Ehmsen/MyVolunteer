package myvolunteer.BE;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User
{

    protected final int id;
    protected StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private Date BDay = new Date();
    private StringProperty profileImage = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    protected StringProperty phoneNumber = new SimpleStringProperty();
    protected StringProperty phoneNumber2 = new SimpleStringProperty();
    protected StringProperty phoneNumber3 = new SimpleStringProperty();
    protected StringProperty address = new SimpleStringProperty();
    protected StringProperty address2 = new SimpleStringProperty();
    private String image = "myvolunteer/GUI/View/Resource/UserPicture.png";
    private boolean isActive;

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

    public StringProperty getPhoneProperty()
    {
        return this.phoneNumber;
    }
    
    public StringProperty getPhone2Property()
    {
        return this.phoneNumber2;
    }
    
    public StringProperty getPhone3Property()
    {
        return this.phoneNumber3;
    }
    
    public StringProperty getAddressProperty()
    {
        return this.address;
    }
    
    public StringProperty getAddress2Property()
    {
        return this.address2;
    }

    public void setPhoneNumber(String value)
    {
        phoneNumber.set(value);
    }

    public String getImage()
    {
        return image;
    }

    @Override
    public String toString()
    {
        return firstName.get() + " " + lastName.get();
    }

    public StringProperty getFNameProperty()
    {
        return this.firstName;
    }

    public StringProperty getLNameProperty()
    {
        return this.lastName;
    }

    public StringProperty getMailProperty()
    {
        return this.email;
    }

    public boolean isIsActive()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public String getPhoneNumber2()
    {
        return phoneNumber2.get();
    }

    public void setPhoneNumber2(String phoneNumber2)
    {
        this.phoneNumber2.set(phoneNumber2);
    }

    public String getPhoneNumber3()
    {
        return phoneNumber3.get();
    }

    public void setPhoneNumber3(String phoneNumber3)
    {
        this.phoneNumber3.set(phoneNumber3);
    }

    public String getAddress()
    {
        return address.get();
    }

    public void setAddress(String address)
    {
        this.address.set(address);
    }

    public String getAddress2()
    {
        return address2.get();
    }

    public void setAddress2(String address2)
    {
        this.address2.set(address2);
    }

    public Date getBDay()
    {
        return BDay;
    }

    public void setBDay(Date BDay)
    {
        this.BDay = BDay;
    }
}
