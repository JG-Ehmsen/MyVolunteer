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
import myvolunteer.BE.Volunteer;

public class DBUserAccess
{

    /**
     * Given a database connection, queries the database for every user entry,
     * disregards ones that are noted as managers and interprets the data into
     * Volunteer BE's, then returns them in a list.
     *
     * @param con
     * @return
     * @throws SQLException
     */
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
                Date date = rs.getDate("LastDate");

                Volunteer user = new Volunteer(ID, fName, phoneNumber);
                user.setLastName(lName);
                user.setEmail(email);
                user.setGender(gender);
                user.setNationality(nationality);
                user.setNote(note);
                user.setLastInputDate(date);
                userList.add(user);
            }
        }
        return userList;
    }

    /**
     * Given a volunteer BE, a guild BE, an amount of hours and a date, writes
     * the amount of hours done on a particular date, for a particular volunteer
     * in a particular guild.
     *
     * @param volunteer
     * @param hours
     * @param guild
     * @param date
     * @param con
     * @throws SQLServerException
     * @throws SQLException
     */
    public void writeHoursToDatabase(Volunteer volunteer, int hours, Guild guild, Date date, Connection con) throws SQLServerException, SQLException
    {
        int DRID = 0;

        DRID = getDateRelationID(guild, volunteer, date, con);

        if (DRID == 0)
        {
            createNewDateRelation(guild, volunteer, date, con);
            DRID = getDateRelationID(guild, volunteer, date, con);
        }
        int startingHours = getDateRelationHours(DRID, con);

        String sql = "UPDATE DateRelation SET Hours = ? WHERE DRID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, startingHours + hours);
        ps.setInt(2, DRID);

        ps.execute();

        updateLastDate(volunteer, con);
    }

    /**
     * Given a Guild and Volunteer BE, returns either the ID of a matching
     * GuildRelation on the DB, or if no matching entry exists, returns 0.
     *
     * @param volunteer
     * @param guild
     * @param con
     * @return
     * @throws SQLException
     */
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

    /**
     * Queries the database for the ID of a given data entry by referencing a
     * date. Returns the date ID if a matching entry exists. If not, makes a
     * method call for creating a new entry, and then queries the database again
     * to get the newly created ID.
     *
     * @param date
     * @param con
     * @return
     * @throws SQLException
     */
    public int getDateID(Date date, Connection con) throws SQLException
    {
        int returnInt = 0;

        returnInt = fetchDateID(date, con);

        if (returnInt != 0)
        {
            return returnInt;
        } else
        {
            createNewDate(date, con);
            returnInt = fetchDateID(date, con);
            return returnInt;
        }
    }

    /**
     * Queries the database for the ID of a given data entry by referencing a
     * date. Returns the date ID if a matching entry exists. If not, returns 0.
     *
     * @param date
     * @param con
     * @return
     * @throws SQLException
     */
    private int fetchDateID(Date date, Connection con) throws SQLException
    {
        int returnInt = 0;

        String sql = "SELECT DID, Date FROM Dates";

        PreparedStatement ps = con.prepareStatement(sql);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        //ps.setDate(1, sqlDate);

        ResultSet rs = ps.executeQuery();

        if (rs.getFetchSize() != 0)
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

    /**
     * Given a data, creates a new entry on the database for a given date.
     *
     * @param date
     * @param con
     * @throws SQLException
     */
    private void createNewDate(Date date, Connection con) throws SQLException
    {
        String sql = "INSERT INTO Dates(Date) VALUES (?)";

        PreparedStatement ps = con.prepareStatement(sql);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        ps.setDate(1, sqlDate);

        ps.execute();
    }

    /**
     * Given a Volunteer BE, writes all the attributes of it to the database.
     *
     * @param user
     * @param con
     * @throws SQLException
     */
    public void CreateNewUser(Volunteer user, Connection con) throws SQLException
    {
        Date today = new Date();
        java.sql.Date sqlDate = new java.sql.Date(today.getTime());
        String sql = "INSERT INTO Users(FName, LName, Gender, Nationality, EMail, TLF, LastDate, Manager, Note) VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getGender());
        ps.setString(4, user.getNationality());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getPhoneNumber());
        ps.setDate(7, sqlDate);
        ps.setString(8, user.getNote());

        ps.execute();
    }

    public void UpdateUser(Volunteer user, Connection con) throws SQLException
    {
        String sql = ""
                + "UPDATE Users "
                + "SET FName = ?, LName = ?, Gender = ?, Nationality = ?, EMail = ?, TLF = ?, Note = ? "
                + "WHERE UID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getGender());
        ps.setString(4, user.getNationality());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getPhoneNumber());
        ps.setString(7, user.getNote());
        ps.setInt(8, user.getId());

        ps.execute();
    }

    /**
     * Given a guild and volunteer BE, and a date, utilizes other method calls
     * to get a date ID and a guild relation ID. Then queries the DB with these
     * as parameters and attempt to find a matching date relation. If found,
     * returns the ID of said relation. If not, returns 0.
     *
     * @param guild
     * @param volunteer
     * @param date
     * @param con
     * @return
     * @throws SQLException
     */
    private int getDateRelationID(Guild guild, Volunteer volunteer, Date date, Connection con) throws SQLException
    {
        int returnInt = 0;

        String sql = "SELECT DID, GRID, DRID FROM DateRelation";

        PreparedStatement ps = con.prepareStatement(sql);

        int DID = getDateID(date, con);
        int GRID = getGuildRelationID(volunteer, guild, con);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            if (rs.getInt("GRID") == GRID && rs.getInt("DID") == DID)
            {
                returnInt = rs.getInt("DRID");
                return returnInt;
            }
        }
        return returnInt;

    }

    /**
     * Creates a new date relation for a particular volunteer in a particular
     * guild, on a particular date. Sets the starting hours to 0.
     *
     * @param guild
     * @param volunteer
     * @param date
     * @param con
     * @throws SQLException
     */
    private void createNewDateRelation(Guild guild, Volunteer volunteer, Date date, Connection con) throws SQLException
    {
        int DID = getDateID(date, con);
        int GRID = getGuildRelationID(volunteer, guild, con);

        String sql = "INSERT INTO DateRelation(DID, GRID, Hours) VALUES (?, ?, 0)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, DID);
        ps.setInt(2, GRID);

        ps.execute();

    }

    /**
     * Queries the DB with a particular date relation ID, and returns the number
     * of hours logged for that relation.
     *
     * @param DRID
     * @param con
     * @return
     * @throws SQLException
     */
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

    /**
     * Updates the 'LastDate' attribute for a given volunteer to the current
     * date.
     *
     * @param user
     * @param con
     * @throws SQLException
     */
    private void updateLastDate(Volunteer user, Connection con) throws SQLException
    {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = ""
                + "UPDATE Users "
                + "SET LastDate = ? "
                + "WHERE UID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDate(1, sqlDate);
        ps.setInt(2, user.getId());
        ps.execute();
    }

    /**
     * Given a particular Guild BE, returns a Manager BE containing the
     * information about the manager of said guild.
     *
     * @param guild
     * @param con
     * @return
     * @throws SQLException
     */
    public Manager getManagerForGuild(Guild guild, Connection con) throws SQLException
    {

        String sql = ""
                + "SELECT * "
                + "FROM Users u, GuildRelation gr "
                + "WHERE u.UID = gr.UID AND u.Manager = 1 AND gr.GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, guild.getID());

        ResultSet rs = ps.executeQuery();

        int ID = 0;
        String phoneNumber = "";
        String eMail = "";
        String fName = "";
        String lName = "";

        while (rs.next())
        {
            ID = rs.getInt("UID");
            phoneNumber = rs.getString("TLF");
            eMail = rs.getString("EMail");
            fName = rs.getString("FName");
            lName = rs.getString("LName");

        }
        Manager manager = new Manager(ID);
        manager.setEmail(eMail);
        manager.setPhoneNumber(phoneNumber);
        manager.setFirstName(fName);
        manager.setLastName(lName);

        return manager;
    }

    /**
     * Queries the database for all tuples in the User table on the DB where
     * Manager = 1, that is it returns information about all managers. Then
     * interprets this into Manager BE's and returns them all in a list.
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public List<Manager> getManagers(Connection con) throws SQLException
    {
        List<Manager> returnList = new ArrayList<>();

        String sql = ""
                + "SELECT * "
                + "FROM Users "
                + "WHERE Manager = 1";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            Manager manager = new Manager(rs.getInt("UID"));
            manager.setEmail(rs.getString("EMail"));
            manager.setPhoneNumber(rs.getString("TLF"));
            manager.setFirstName(rs.getString("FName"));
            manager.setLastName(rs.getString("LName"));

            returnList.add(manager);
        }

        return returnList;
    }

    public int getHoursWorkedForVolunteer(Volunteer volunteer, Connection con) throws SQLException
    {
        int returnInt = 0;

        String sql = ""
                + "SELECT Hours "
                + "FROM DateRelation dr, GuildRelation gr "
                + "WHERE dr.GRID = gr.GRID AND gr.UID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, volunteer.getId());

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int hours = rs.getInt("Hours");

            returnInt = returnInt + hours;
        }
        return returnInt;
    }

}
