package fr.fallenvaders.minecraft.justice_hands.sql;

import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.objects.CJSanction;
import fr.fallenvaders.minecraft.justice_hands.sanctionmanager.objects.Sanction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SqlSanctionManager {
  private Connection connection;

  // Constructeur
  public SqlSanctionManager(Connection connection) {
    this.connection = connection;
  }

  // Ajoute une sanction à la liste des sanction et attribut les points au joueur.
  public void addSanction(
      Player target, Player moderator, Sanction sanction, Timestamp expireDate) {
    int currentPoints = JusticeHands.getSqlPA().getPoints(target.getUniqueId());
    int targetNewPoints = currentPoints + sanction.getPoints();

    try (PreparedStatement q =
           connection.prepareStatement(
             "INSERT INTO sanctions_list(uuid,name,reason,points,expiredate,moderator,type) VALUES (?,?,?,?,?,?,?)")) {
      // Ajoute la sanction à la liste des sanctions dans la base de données
      q.setString(1, target.getUniqueId().toString());
      q.setString(2, sanction.getName());
      q.setString(3, sanction.getReason());
      q.setInt(4, sanction.getPoints());
      q.setTimestamp(5, expireDate);
      q.setString(6, moderator.getUniqueId().toString());
      q.setString(7, sanction.getInitialType());
      q.execute();
    } catch (Exception e) {
     e.printStackTrace();
    }

    try (PreparedStatement q =
           connection.prepareStatement("UPDATE players_points SET points = ? WHERE uuid = ?")) {
      // Ajoute le nombre de point de la sanction au joueur sanctionné
      q.setInt(1, targetNewPoints);
      q.setString(2, target.getUniqueId().toString());
      q.executeUpdate();

      // Il faut récupérer la sanction générée antérieurement pour l'ajouter dans
      // la hashmap pour que si le modérateur ai l'envie, de pouvoir l'annuler via l'ID

      // todo : a revoir
      // setModLastSanctionOnMap(moderator);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Nullable
  public List<CJSanction> getPlayerSanctions(Player player) {
    ArrayList<CJSanction> playerSanctionList = new ArrayList<>();

    try (PreparedStatement q =
           connection.prepareStatement(
             "SELECT * FROM sanctions_list WHERE uuid = ? AND state = ? ORDER BY id DESC")) {
      q.setString(1, player.getUniqueId().toString());
      q.setString(2, "active");

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

        playerSanctionList.add(sanction);
      }
      return playerSanctionList;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Nullable
  public CJSanction getLastSanction(Player player) {
    try (PreparedStatement q =
           connection.prepareStatement(
             "SELECT * FROM sanctions_list WHERE uuid = ? AND state = ? ORDER BY id DESC LIMIT 0,1")) {
      q.setString(1, player.getUniqueId().toString());
      q.setString(2, "active");

      ResultSet rs = q.executeQuery();
      CJSanction sanction = new CJSanction();

      rs.first();
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
      return sanction;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}