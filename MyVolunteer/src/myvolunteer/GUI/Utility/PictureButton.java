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

        ImageView image = new ImageView(new Image(user.getImage()));
        this.setGraphic(image);
        this.setText(user.getFirstName());
        this.setContentDisplay(ContentDisplay.TOP);
        this.setOnAction(new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                HandleClickUser();
            }
        });
    }

    private void InitGuild()
    {
        this.setMinWidth(200);
        this.setMinHeight(100);

        ImageView image = new ImageView(new Image(guild.getImage()));
        this.setGraphic(image);
        this.setText(guild.getName());
        this.setContentDisplay(ContentDisplay.TOP);
        this.setOnAction(new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                HandleClickGuild();
            }
        });
    }

    public Volunteer getUser()
    {
        return user;
    }

    public Guild getGuild()
    {
        return guild;
    }

    public void HandleClickUser()
    {
        System.out.println(user.getFirstName());
    }

    public void HandleClickGuild()
    {
        System.out.println(guild.getName());
    }

}
