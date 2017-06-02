package myvolunteer.BE;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Guild
{

    private final int ID;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private List<Integer> memberList = new ArrayList();
    private String defaultImage = "myvolunteer/GUI/View/Resource/GuildPicture.png";
    private boolean isActive;
    private BufferedImage picture = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

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

    public Image getPicture()
    {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        if (picture != null)
        {
            img = picture;
            return SwingFXUtils.toFXImage(img, null);
        } else
        {
            return new Image(defaultImage);

        }
    }

    public BufferedImage getBufferedPicture()
    {
        return picture;
    }

    public void setPicture(BufferedImage picture)
    {
        this.picture = picture;
    }

    public String getImage()
    {
        return defaultImage;
    }

    @Override
    public String toString()
    {
        return this.getName();
    }

    public boolean isIsActive()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }

}
