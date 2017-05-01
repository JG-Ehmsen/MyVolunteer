package myvolunteer.GUI.Utility;

import com.sun.javafx.sg.prism.NGCanvas;
import myvolunteer.BE.User;

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
        this.setText(user.getFirstName());
        //this.setGraphic(user.getImage());
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
