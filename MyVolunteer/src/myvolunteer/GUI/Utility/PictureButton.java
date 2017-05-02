package myvolunteer.GUI.Utility;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myvolunteer.BE.Manager;
import myvolunteer.BE.User;
import myvolunteer.BE.Volunteer;

/**
 *
 * @author jeppe
 */
public class PictureButton extends javafx.scene.control.Button
{
    
    User user;
    
    
    public PictureButton(User user)
    {
        this.user = user;
        initialize();
    }
    
    private void initialize()
    {
        if (user.getClass().equals(Volunteer.class))
        {
        this.setMinWidth(100);
        this.setMinHeight(100);
        }
        else if (user.getClass().equals(Manager.class))
        {
            this.setMinHeight(100);
            this.setMinWidth(200);
        }
        this.setText(user.getFirstName());
        ImageView image = new ImageView(new Image("myvolunteer/GUI/View/Resource/Picture.png"));
        this.setGraphic(image);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setOnAction(new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                HandleClick();
            }
        });
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void HandleClick()
    {
        System.out.println(user.getFirstName());
    }
}
