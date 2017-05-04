package myvolunteer.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.User;
import myvolunteer.BE.Volunteer;

public class DBUserAccess
{

    public List<User> getUsers(Connection con) throws SQLException
    {
        List<User> userList = new ArrayList();
        String sql = ""
                + "SELECT *"
                + "FROM Users";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int ID = rs.getInt("UID");
            String fName = rs.getString("FName");
            String lName = rs.getString("LName");
            String email = rs.getString("EMail");
            String phoneNumber = rs.getString("TLF");

            if (!rs.getBoolean("Manager"))
            {
                String gender = rs.getString("Gender");
                String nationality = rs.getString("Nationality");
                String note = rs.getString("Note");

                Volunteer user = new Volunteer(ID, fName, phoneNumber);
                user.setLastName(lName);
                user.setEmail(email);
                user.setGender(gender);
                user.setNationality(nationality);
                user.setNote(note);
                System.out.println("Adding volunteer with ID " + ID);
                userList.add(user);
            } else
            {
                Manager user = new Manager(ID);
                user.setEmail(email);
                user.setFirstName(fName);
                user.setLastName(lName);
                user.setPhoneNumber(phoneNumber);
                System.out.println("Adding manager with ID " + ID);
                userList.add(user);
            }
        }
        return userList;
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date) throws SQLServerException
    {

        //SELECT gr.GRID
        //FROM GuildRelation gr, Guild g, Users u
        //WHERE gr.GID = g.GID AND gr.UID = u.UID
        
        //SELECT DID
        //FROM Dates
        //WHERE Date = '2015-05-03'
        
        //INSERT INTO Dates(Date)
        //VALUES(?)
        
        //SELECT Hours
        //FROM DateRelation
        //WHERE GRID = ? AND DID = ?
        
        //INSERT INTO Dates(Date)
        //VALUES(?)

        //SELECT Hours
        //FROM DateRelation
        //WHERE GRID = ? AND DID = ?
        
        //INSERT INTO DateRelation(Hours)
        //VALUES (?)
        //WHERE GRID = ? AND DID = ?
        
        //INSERT INTO DateRelation(GRID, DID, Hours)
        //VALUES (?, ?, ?)
        
        //PreparedStatement ps = con.prepareStatement(sql);

    }
    
    public void CreateNewUser(Volunteer user, Connection con) throws SQLException 
    {
        String sql = ""
                + "INSERT INTO Users(FName, LName, Gender, Nationality, EMail, TLF, Manager, Note)"
                + "VALUES (?, ?, ?, ?, ?, ?, 0, ?)";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getGender());
        ps.setString(4, user.getNationality());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getPhoneNumber());
        ps.setString(7, user.getNote());
        
        ps.execute();
    }
}
