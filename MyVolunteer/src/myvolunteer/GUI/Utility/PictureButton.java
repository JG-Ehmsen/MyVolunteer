package myvolunteer.GUI.Utility;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.User;
import myvolunteer.BE.Volunteer;

/**
 *
 * @author jeppe
 */
public class PictureButton extends javafx.scene.control.Button
{

    Volunteer user;
    Guild guild;

    public PictureButton(Volunteer user)
    {
        this.user = user;
        InitUser();
    }

    public PictureButton(Guild guild)
    {
        this.guild = guild;
        InitGuild();
    }

    private void InitUser()
    {
        this.setMinWidth(100);
        this.setMinHeight(100);

        ImageView image = new ImageView(user.getPicture());
        this.setGraphic(image);
        this.setText(user.getFirstName());
        this.setContentDisplay(ContentDisplay.TOP);
    }

    private void InitGuild()
    {
        this.setMinWidth(200);
        this.setMinHeight(100);

        ImageView image = new ImageView(guild.getPicture());
        this.setGraphic(image);
        this.setText(guild.getName());
        this.setContentDisplay(ContentDisplay.TOP);
    }

    public Volunteer getUser()
    {
        return user;
    }

    public Guild getGuild()
    {
        return guild;
    }
}
