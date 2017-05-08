package myvolunteer.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import myvolunteer.BE.Guild;

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
                + "SELECT *"
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

            guildList.add(guild);

        }

        sql = ""
                + "SELECT *"
                + "FROM GuildRelation";

        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next())
        {
            int GID = rs.getInt("GID");
            for (Guild guild : guildList)
            {
                if (GID == guild.getID())
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
    public void CreateNewLaug(Guild guild, Connection con) throws SQLException
    {
        String sql = ""
                + "INSERT INTO Guild(GName, Description) VALUES(?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, guild.getName());
        ps.setString(2, guild.getDescription());

        ps.execute();
    }

}
