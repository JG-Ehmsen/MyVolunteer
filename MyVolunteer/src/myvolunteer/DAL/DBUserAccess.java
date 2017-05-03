package myvolunteer.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import myvolunteer.BE.Manager;
import myvolunteer.BE.User;
import myvolunteer.BE.Volunteer;

/**
 *
 * @author jeppe
 */
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
}
