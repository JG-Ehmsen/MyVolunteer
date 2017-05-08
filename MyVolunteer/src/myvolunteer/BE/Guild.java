package myvolunteer.BE;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeppe
 */
public class Guild
{

    private final int ID;

    private StringProperty name = new SimpleStringProperty();

    private StringProperty description = new SimpleStringProperty();

    private List<Integer> memberList = new ArrayList();

    private String image = "myvolunteer/GUI/View/Resource/GuildPicture.png";

    public Guild(int ID, String name)
    {
        this.ID = ID;
        this.name.set(name);
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getDescription()
    {
        return description.get();
    }

    public void setDescription(String description)
    {
        this.description.set(description);
    }

    public int getID()
    {
        return ID;
    }

    public List<Integer> getMemberList()
    {
        return memberList;
    }

    public void setMemberList(List<Integer> memberList)
    {
        this.memberList = memberList;
    }

    public String getImage()
    {
        return image;
    }

    @Override
    public String toString()
    {
        return this.getName();
    }

}
