package fr.fallenvaders.minecraft.mail_box.player_manager;

import fr.fallenvaders.minecraft.mail_box.sql.SQLConnection;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInfoSQL {

    private static final String TABLE_NAME = "MailBox_PlayerInfo";
    private final SQLConnection sqlConnection = new SQLConnection();

    public PlayerInfoSQL() {
        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS	" + TABLE_NAME
                + " (uuid VARCHAR(255), name VARCHAR(255))");
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public PlayerInfo tryRegister(Player player) {
        PlayerInfo playerInfo = new PlayerInfo(player);
        int check = this.check(playerInfo);

        if (check == -1) {
            this.create(playerInfo);

        } else if (check == 0) {
            this.update(playerInfo);

        }

        return playerInfo;
    }

    public List<PlayerInfo> getAll() {
        List<PlayerInfo> res = new ArrayList<>();

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");

                PlayerInfo playerInfo = new PlayerInfo(name, uuid);

                res.add(playerInfo);
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return res;
    }

    public PlayerInfo create(PlayerInfo obj) {
        PlayerInfo res = null;

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("INSERT INTO " + TABLE_NAME + " (uuid, name) VALUES(?, ?)");
            query.setString(1, obj.getUuid().toString());
            query.setString(2, obj.getName());

            query.execute();
            query.close();
            res = obj;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return res;
    }

    /**
     * @param obj
     * @return -1: n'existe pas<br> 0: existe mais pas a jour<br> 1: existe tout est bon
     */
    public int check(PlayerInfo obj) {
        int res = -1;

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE uuid = ? ");
            query.setString(1, obj.getUuid().toString());
            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("name").equals(obj.getName())) {
                    res = 1;

                } else {
                    res = 0;
                }
            }
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return res;
    }

    public PlayerInfo update(PlayerInfo obj) {
        PlayerInfo res = null;

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("UPDATE " + TABLE_NAME + " SET name = ? WHERE id = ?");
            query.setString(1, obj.getName());
            query.setString(2, obj.getUuid().toString());

            query.executeUpdate();
            query.close();
            res = obj;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public void delete(PlayerInfo obj) {
        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE uuid = ?");
            query.setString(1, obj.getUuid().toString());
            query.execute();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
