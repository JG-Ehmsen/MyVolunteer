package myvolunteer.BE;

public class Manager extends User
{
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

}
