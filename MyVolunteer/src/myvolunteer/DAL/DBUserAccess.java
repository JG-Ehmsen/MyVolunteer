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

    public List<Volunteer> getUsers(Connection con) throws SQLException
    {
        List<Volunteer> userList = new ArrayList();
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
                userList.add(user);
            }
//else
//            {
//                Manager user = new Manager(ID);
//                user.setEmail(email);
//                user.setFirstName(fName);
//                user.setLastName(lName);
//                user.setPhoneNumber(phoneNumber);
//                userList.add(user);
//            }
        }
        return userList;
    }

    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date, Connection con) throws SQLServerException, SQLException
    {
        System.out.println("Starting to add hours.");
        int DRID = 0;

        
        DRID = getDateRelationID(guild, volunteer, date, con);
        System.out.println("DRID = " + DRID);

        if (DRID == -1)
        {
            System.out.println("Creating new date relation");
            createNewDateRelation(guild, volunteer, date, con);
            DRID = getDateRelationID(guild, volunteer, date, con);
            System.out.println("Got DRID for new DR = " + DRID);
        }
        int startingHours = getDateRelationHours(DRID, con);
        System.out.println("Got starting hours");

        String sql = "UPDATE DateRelation SET Hours = ? WHERE DRID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, startingHours + hours);
        ps.setInt(2, DRID);

        ps.execute();
        System.out.println("Executed query");
    }

    public int getGuildRelationID(Volunteer volunteer, Guild guild, Connection con) throws SQLException
    {
        int returnInt = 0;

        String sql = "SELECT gr.GRID FROM GuildRelation gr, Guild g, Users u WHERE gr.GID = g.GID AND gr.UID = u.UID AND u.UID = ? AND g.GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, volunteer.getId());
        ps.setInt(2, guild.getID());

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            returnInt = rs.getInt("GRID");
        }
        return returnInt;

    }

    public int getDateID(Date date, Connection con) throws SQLException
    {
        int returnInt = 0;

        returnInt = fetchDateID(date, con);
        System.out.println("Got dateID = " + returnInt);

        if (returnInt != 0)
        {
            return returnInt;
        } else
        {
            createNewDate(date, con);
            returnInt = fetchDateID(date, con);
            System.out.println("Returning " + returnInt);
            return returnInt;
        }
    }

    private int fetchDateID(Date date, Connection con) throws SQLException
    {
        int returnInt = 0;

        String sql = "SELECT DID, Date FROM Dates";

        PreparedStatement ps = con.prepareStatement(sql);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        //ps.setDate(1, sqlDate);

        ResultSet rs = ps.executeQuery();

        if (rs.getFetchSize() == 0)
        {
            returnInt = 0;
        } else
        {
            while (rs.next())
            {
                Date checkDate = rs.getDate("Date");
                if (checkDate.toString().trim().equals(sqlDate.toString().trim()))
                {
                    returnInt = rs.getInt("DID");
                }
            }
        }
        return returnInt;
    }

    private void createNewDate(Date date, Connection con) throws SQLException
    {
        String sql = "INSERT INTO Dates(Date) VALUES (?)";

        PreparedStatement ps = con.prepareStatement(sql);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        ps.setDate(1, sqlDate);

        ps.execute();
    }

    public void CreateNewUser(Volunteer user, Connection con) throws SQLException
    {
        String sql = "INSERT INTO Users(FName, LName, Gender, Nationality, EMail, TLF, Manager, Note) VALUES (?, ?, ?, ?, ?, ?, 0, ?)";

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

    private int getDateRelationID(Guild guild, Volunteer volunteer, Date date, Connection con) throws SQLException
    {
        int returnInt = -1;

        String sql = "SELECT DID, GRID, DRID FROM DateRelation";

        PreparedStatement ps = con.prepareStatement(sql);

        int DID = getDateID(date, con);
        int GRID = getGuildRelationID(volunteer, guild, con);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            returnInt = -1;
            if (rs.getInt("GRID") == GRID && rs.getInt("DID") == DID)
            {
                System.out.println("Returning an ID");
                returnInt = rs.getInt("DRID");
                return returnInt;
            }
        }
        return returnInt;


    }

    private void createNewDateRelation(Guild guild, Volunteer volunteer, Date date, Connection con) throws SQLException
    {
        System.out.println("Creating new guild relation");
        int DID = getDateID(date, con);
        int GRID = getGuildRelationID(volunteer, guild, con);

        String sql = "INSERT INTO DateRelation(DID, GRID, Hours) VALUES (?, ?, 0)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, DID);
        ps.setInt(2, GRID);

        ps.execute();

    }

    private int getDateRelationHours(int DRID, Connection con) throws SQLException
    {
        int returnInt = 0;
        String sql = "SELECT Hours FROM DateRelation WHERE DRID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, DRID);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            returnInt = rs.getInt("Hours");
        }
        return returnInt;
    }

}
