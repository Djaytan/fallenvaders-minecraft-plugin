package fr.fallenvaders.minecraft.justice_hands.sql;

import fr.fallenvaders.minecraft.justice_hands.criminalrecords.objects.CJSanction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SqlKeysKeeper {

  // Englobe toutes les méthodes en lien avec KeysKepper et la base de données
  private Connection connection;

  // Constructeur
  public SqlKeysKeeper(Connection connection) {
    this.connection = connection;
  }

  // Permet de récupérer tous les mutes d'un joueur et enregistrer leur timestamp
  @Nullable
  public List<Long> getPlayerMutesEDLong(Player player) {
    ArrayList<Long> playerMuteList = new ArrayList<>();
    try {
      PreparedStatement q =
          connection.prepareStatement(
              "SELECT * FROM sanctions_list WHERE uuid = ? AND type = ? AND state = ?");
      q.setString(1, player.getUniqueId().toString());
      q.setString(2, "mute");
      q.setString(3, "active");

      ResultSet rs = q.executeQuery();
      while (rs.next()) {
        Long expireDateLong = rs.getTimestamp("expiredate").getTime();
        playerMuteList.add(expireDateLong);
      }
      q.close();
      return playerMuteList;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Nullable
  public List<CJSanction> getPlayerBans(Player player) {
    ArrayList<CJSanction> playerBansList = new ArrayList<>();

    try {
      PreparedStatement q =
          connection.prepareStatement(
              "SELECT * FROM sanctions_list WHERE uuid = ? AND (type = ? OR type = ? OR type = ?) AND state = ? ORDER BY id DESC");
      q.setString(1, player.getUniqueId().toString());
      q.setString(2, "risingban");
      q.setString(3, "ban");
      q.setString(4, "bandef");
      q.setString(5, "active");

      ResultSet rs = q.executeQuery();
      while (rs.next()) {
        CJSanction sanction = new CJSanction();
        sanction.setID("#" + String.format("%05d", (rs.getInt("id"))));
        sanction.setPlayer(Bukkit.getPlayer(UUID.fromString(rs.getString("uuid"))));
        sanction.setName(rs.getString("name"));
        sanction.setReason(rs.getString("reason"));
        sanction.setPoints(rs.getInt("points"));
        sanction.setTSDate(rs.getTimestamp("date"));
        sanction.setTSExpireDate(rs.getTimestamp("expiredate"));
        sanction.setModerator(Bukkit.getPlayer(UUID.fromString(rs.getString("moderator"))));
        sanction.setType(rs.getString("type"));
        sanction.setState(rs.getString("state"));

        playerBansList.add(sanction);
      }
      q.close();
      return playerBansList;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
