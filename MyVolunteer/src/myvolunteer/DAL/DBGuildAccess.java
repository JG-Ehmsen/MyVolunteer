package myvolunteer.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import myvolunteer.BE.Guild;
import myvolunteer.BE.Manager;
import myvolunteer.BE.Volunteer;

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
    public List<Guild> getGuilds(Connection con) throws SQLException
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

            Guild guild = new Guild(ID, name);
            guild.setDescription(description);
            guild.setIsActive(rs.getBoolean("Active"));

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
    public void CreateNewLaug(Guild guild, int MID, Connection con) throws SQLException
    {
        String sql = ""
                + "INSERT INTO Guild(GName, Description, Active) "
                + "VALUES(?, ?, 1)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, guild.getName());
        ps.setString(2, guild.getDescription());

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

    public void UpdateGuild(Guild guild, Manager manager, List<Integer> in, List<Integer> out, Connection con) throws SQLException
    {
        setGuildInfo(guild, con);
        changeGuildManager(guild, manager, con);
        addOrActivateGuildRelations(guild, in, con);
        deactivateGuildRelations(guild, out, con);
    }

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

    public void deactivateGuildRelations(Guild guild, List<Integer> out, Connection con) throws SQLException
    {
        int GID = guild.getID();
        for (Integer UID : out)
        {
            setGuildRelationStatus(GID, UID, false, con);
        }
    }

    public void setGuildInfo(Guild guild, Connection con) throws SQLException
    {
        String sql = ""
                + "UPDATE Guild "
                + "SET GName = ?, Description = ? "
                + "WHERE GID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, guild.getName());
        ps.setString(2, guild.getDescription());
        ps.setInt(3, guild.getID());

        ps.execute();
    }

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

    private void createManagerRelation(int GID, int MID, Connection con) throws SQLException
    {
        String sql = ""
                + "INSERT INTO ManagerRelation(MID, GID) "
                + "VALUES(?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, MID);
        ps.setInt(2, GID);

        ps.execute();
    }

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
}
