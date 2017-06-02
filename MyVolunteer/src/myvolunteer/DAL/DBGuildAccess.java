package myvolunteer.DAL;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;

public class DBGuildAccess
{

    /**
     * Queries the DB and returns a list of all guilds. For each guild, also
     * stores a list of ID's for every volunteer registered with that guild.
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public List<Guild> getGuilds(Connection con) throws SQLException, IOException
    {
        List<Guild> guildList = new ArrayList();
        String sql = ""
                + "SELECT * "
                + "FROM Guild";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int ID = rs.getInt("GID");
            String name = rs.getString("GName");
            String description = rs.getString("Description");
            boolean active = rs.getBoolean("Active");

            Guild guild = new Guild(ID, name);
            guild.setDescription(description);
            guild.setIsActive(active);

            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Blob imgAsBlob = rs.getBlob("Image");
            if (imgAsBlob != null)
            {
                byte[] immAsBytes = imgAsBlob.getBytes(1, (int) imgAsBlob.length());

                InputStream in = new ByteArrayInputStream(immAsBytes);
                img = ImageIO.read(in);
            }

            guild.setPicture(img);

            guildList.add(guild);

        }

        sql = ""
                + "SELECT * "
                + "FROM GuildRelation "
                + "WHERE Active = 1";

        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next())
        {
            int GID = rs.getInt("GID");
            for (Guild guild : guildList)
            {
                if (GID == guild.getID() && guild.isIsActive())
                {
                    int UID = rs.getInt("UID");
                    guild.getMemberList().add(UID);
                }
            }
        }

        return guildList;
    }

    /**
     * Given a guild BE, queries the database to create a new tuple for that
     * guild.
     *
     * @param guild
     * @param con
     * @throws SQLException
     */
    public void CreateNewLaug(Guild guild, int MID, Connection con) throws SQLException, IOException
    {
        String sql = ""
                + "INSERT INTO Guild(GName, Description, Image, Active) "
                + "VALUES(?, ?, ?, 1)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, guild.getName());
        ps.setString(2, guild.getDescription());

        byte[] imgAsBytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(guild.getBufferedPicture(), "jpg", baos);
        baos.flush();

        imgAsBytes = baos.toByteArray();
        baos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(imgAsBytes);
        ps.setBinaryStream(3, bais);

        ps.execute();

        int GID = getGuildID(guild, con);

        if (!guild.getMemberList().isEmpty())
        {
            for (Integer i : guild.getMemberList())
            {
                createGuildRelation(GID, i, con);
            }
        }
        createManagerRelation(GID, MID, con);
    }

    /**
     * Composite method for updating a guild, utilizing other methods to update
     * the information about the guild, change the manager and add/remove
     * volunteers from the guild.
     *
     * @param guild
     * @param manager
     * @param in
     * @param out
     * @param con
     * @throws SQLException
     * @throws IOException
     */
    public void UpdateGuild(Guild guild, Manager manager, List<Integer> in, List<Integer> out, Connection con) throws SQLException, IOException
    {
        setGuildInfo(guild, con);
        changeGuildManager(guild, manager, con);
        addOrActivateGuildRelations(guild, in, con);
        deactivateGuildRelations(guild, out, con);
    }

    /**
     * Given a guild, and a list of integers containing ID's of people who
     * should be in the guild.
     *
     * Queries the database for a guild matching the information. Then, for each
     * ID in the given list, it either creates new guild relations if the do not
     * exist, or sets them as active if already existing.
     *
     * @param guild
     * @param in
     * @param con
     * @throws SQLException
     */
    public void addOrActivateGuildRelations(Guild guild, List<Integer> in, Connection con) throws SQLException
    {
        int GID = guild.getID();

        for (Integer UID : in)
        {
            int GRID = getGuildRelationID(GID, UID, con);

            if (GRID == -1)
            {
                createGuildRelation(GID, UID, con);
            } else if (GRID > 0)
            {
                setGuildRelationStatus(GID, UID, true, con);
            }
        }
    }

    /**
     * Given a guild, and a list of volunteer ID's that should NOT be in the
     * guild, it runs a loop to deactivate each of them.
     *
     * @param guild
     * @param out
     * @param con
     * @throws SQLException
     */
    public void deactivateGuildRelations(Guild guild, List<Integer> out, Connection con) throws SQLException
    {
        int GID = guild.getID();
        for (Integer UID : out)
        {
            setGuildRelationStatus(GID, UID, false, con);
        }
    }

    /**
     * Given a guild BE that has been modified to contain different information,
     * this method queries the database to update the record with a matching ID
     * to match the information given.
     *
     * @param guild
     * @param con
     * @throws SQLException
     * @throws IOException
     */
    public void setGuildInfo(Guild guild, Connection con) throws SQLException, IOException
    {
        String sql = ""
                + "UPDATE Guild "
                + "SET GName = ?, Description = ?, Image = ? "
                + "WHERE GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, guild.getName());
        ps.setString(2, guild.getDescription());

        byte[] imgAsBytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(guild.getBufferedPicture(), "jpg", baos);
        baos.flush();

        imgAsBytes = baos.toByteArray();
        baos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(imgAsBytes);
        ps.setBinaryStream(3, bais);

        ps.setInt(4, guild.getID());

        ps.execute();
    }

    /**
     * Given a guild and a manager, updates the manager relation to change the
     * manager to the one that is given.
     *
     * @param guild
     * @param manager
     * @param con
     * @throws SQLException
     */
    public void changeGuildManager(Guild guild, Manager manager, Connection con) throws SQLException
    {
        String sql = ""
                + "UPDATE ManagerRelation "
                + "SET MID = ? "
                + "WHERE GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, manager.getId());
        ps.setInt(2, guild.getID());

        ps.execute();
    }

    /**
     * Given a guild BE, queries the database for a guild with a matching name
     * and then returns the ID of that.
     *
     * If one exists, returns the ID. If not, returns 0.
     *
     * @param guild
     * @param con
     * @return
     * @throws SQLException
     */
    private int getGuildID(Guild guild, Connection con) throws SQLException
    {
        int returnInt = 0;
        String sql = ""
                + "SELECT GID "
                + "FROM Guild "
                + "WHERE GName = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, guild.getName());

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            returnInt = rs.getInt("GID");
        }
        return returnInt;
    }

    /**
     * Given a guild ID and a user ID, creates a new GuildRelation with that
     * information, thus adding a volunteer to a guild.
     *
     * @param GID
     * @param UID
     * @param con
     * @throws SQLException
     */
    private void createGuildRelation(int GID, int UID, Connection con) throws SQLException
    {
        String sql = ""
                + "INSERT INTO GuildRelation(UID, GID, Active) "
                + "VALUES(?, ?, 1)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, UID);
        ps.setInt(2, GID);

        ps.execute();
    }

    /**
     * Given a guild ID and a manager ID, creates a new ManagerRelation with the
     * given information, thus adding a manager to the guild.
     *
     * @param GID
     * @param MID
     * @param con
     * @throws SQLException
     */
    private void createManagerRelation(int GID, int MID, Connection con) throws SQLException
    {
        String sql = ""
                + "INSERT INTO ManagerRelation(MID, GID, Active) "
                + "VALUES(?, ?, 1)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, MID);
        ps.setInt(2, GID);

        ps.execute();
    }

    /**
     * Given a guild, returns an integer of the total number of hours that have
     * been volunteered in that guild.
     *
     * @param guild
     * @param con
     * @return
     * @throws SQLException
     */
    public int getHoursWorkedForGuild(Guild guild, Connection con) throws SQLException
    {
        int returnInt = 0;

        String sql = ""
                + "SELECT Hours "
                + "FROM DateRelation dr, GuildRelation gr "
                + "WHERE dr.GRID = gr.GRID AND gr.GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, guild.getID());

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int hours = rs.getInt("Hours");

            returnInt = returnInt + hours;
        }
        return returnInt;
    }

    /**
     * Returns the total number of hours that have been volunteered across all
     * guilds and volunteers.
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public int getTotalHoursWorked(Connection con) throws SQLException
    {
        int returnInt = 0;

        String sql = ""
                + "SELECT Hours "
                + "FROM DateRelation";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int hours = rs.getInt("Hours");

            returnInt = returnInt + hours;
        }
        return returnInt;
    }

    /**
     * Given a guild ID and a volunteer ID, sets the matching guild relation as
     * either active or inactive.
     *
     * @param GID
     * @param UID
     * @param active
     * @param con
     * @throws SQLException
     */
    public void setGuildRelationStatus(int GID, int UID, boolean active, Connection con) throws SQLException
    {

        String sql = ""
                + "UPDATE GuildRelation "
                + "SET Active = ? "
                + "WHERE UID = ? AND GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setBoolean(1, active);
        ps.setInt(2, UID);
        ps.setInt(3, GID);

        ps.execute();
    }

    /**
     * Given a GID and UID, returns the ID of a matching GuildRelation if it
     * exists, and if not, returns -1. Alternatively, if the relation is
     * inactive, returns 0.
     *
     * @param GID
     * @param UID
     * @param con
     * @return
     * @throws SQLException
     */
    public Integer getGuildRelationID(int GID, int UID, Connection con) throws SQLException
    {
        int returnInt = -1;

        String sql = ""
                + "SELECT * "
                + "FROM GuildRelation "
                + "WHERE GID = ? AND UID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, GID);
        ps.setInt(2, UID);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            if (!rs.getBoolean("Active"))
            {
                returnInt = rs.getInt("GRID");
            } else
            {
                returnInt = 0;
            }
        }

        return returnInt;
    }

    /**
     * Utilizing other methods, marks a guild as inactive, and removes all
     * volunteers from the guild.
     *
     * @param guild
     * @param con
     * @throws SQLException
     */
    public void deactivateGuild(Guild guild, Connection con) throws SQLException
    {
        setGuildStatus(guild, false, con);
        deactivateGuildForAllUsers(guild, con);

    }

    /**
     * Given a guild BE and a boolean, sets the guild status to either active or
     * inactive.
     *
     * @param guild
     * @param active
     * @param con
     * @throws SQLException
     */
    public void setGuildStatus(Guild guild, boolean active, Connection con) throws SQLException
    {
        String sql = ""
                + "UPDATE Guild "
                + "SET Active = ? "
                + "WHERE GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setBoolean(1, active);
        ps.setInt(2, guild.getID());

        ps.execute();
    }

    /**
     * Given a guild, deactivates all guild relations containing its ID.
     *
     * @param guild
     * @param con
     * @throws SQLException
     */
    public void deactivateGuildForAllUsers(Guild guild, Connection con) throws SQLException
    {
        String sql = ""
                + "UPDATE GuildRelation "
                + "Set Active = 0 "
                + "WHERE GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, guild.getID());

        ps.execute();
    }
}
