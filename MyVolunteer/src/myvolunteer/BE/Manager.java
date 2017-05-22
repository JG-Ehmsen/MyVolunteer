package myvolunteer.BE;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User
{
    private List<Integer> managerGuilds = new ArrayList<>();
    
    private boolean isAdmin;

    public Manager(int id)
    {       
        super(id);
        
    }
    
    public boolean isAdmin()
    {
        return isAdmin;
    }
    
    public void setIsAdmin(boolean isAdmin)
    {
         this.isAdmin = isAdmin;  
    }
    
    public List<Integer> getManagerGuilds()
    {
        return managerGuilds;
    }

    public void setManagerGuilds(List<Integer> managerGuilds)
    {
        this.managerGuilds = managerGuilds;
    }

}
