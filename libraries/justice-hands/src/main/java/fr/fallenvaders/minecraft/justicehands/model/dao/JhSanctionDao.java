/*
 *  This file is part of the FallenVaders distribution (https://github.com/FallenVaders).
 *  Copyright © 2021 Loïc DUBOIS-TERMOZ.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package fr.fallenvaders.minecraft.justicehands.model.dao;

import fr.fallenvaders.minecraft.commons.sql.FvDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.JhSanction;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * JusticeHands' DAO class about manipulation of sanctions in the model.
 *
 * <p>For more information about DAO pattern, see {@link FvDao} interface Javadoc.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 * @see FvDao
 */
@Singleton
public class JhSanctionDao implements FvDao<JhSanction> {

  // TODO: FV-116, FV-117 - optimisation with cache

  private final Server server;

  /**
   * Constructor.
   *
   * @param server The Bukkit server in which the JusticeHands' module is executed.
   */
  @Inject
  public JhSanctionDao(@NotNull Server server) {
    this.server = server;
  }

  /**
   * Gets and returns the {@link JhSanction} which correspond to the specified ID from the model.
   *
   * @param connection The connection to the DBMS.
   * @param strId The ID of the sought sanction.
   * @return The JusticeHands' sanction associated to the specified ID if it exists.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public @NotNull Optional<JhSanction> get(@NotNull Connection connection, @NotNull String strId)
      throws SQLException {
    try (PreparedStatement stmt =
        connection.prepareStatement("SELECT * FROM fv_jh_sanction WHERE sctn_id = ?")) {
      stmt.setInt(1, Integer.parseInt(strId));
      return getSanctions(stmt).stream().findFirst();
    }
  }

  /**
   * Gets and returns all existing {@link JhSanction}s from the model.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @param connection The connection to the DBMS.
   * @return The list of all existing JusticeHands' sanctions.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public @NotNull Set<JhSanction> getAll(@NotNull Connection connection) throws SQLException {
    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM fv_jh_sanction")) {
      return getSanctions(stmt);
    }
  }

  /**
   * Gets and returns all existing {@link JhSanction}s where the specified {@link OfflinePlayer} is
   * associated with them as an inculpated player.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last *
   * position.
   *
   * @param connection The connection to the DBMS.
   * @param player The Bukkit offline player.
   * @return The list of all existing JusticeHands' sanctions for the specified {@link
   *     OfflinePlayer}.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  public @NotNull Set<JhSanction> getFromPlayer(
      @NotNull Connection connection, @NotNull OfflinePlayer player) throws SQLException {
    try (PreparedStatement stmt =
        connection.prepareStatement(
            "SELECT * FROM fv_jh_sanction WHERE sctn_inculpated_player_uuid = ?")) {
      stmt.setString(1, player.getUniqueId().toString());
      return getSanctions(stmt);
    }
  }

  /**
   * Saves a new {@link JhSanction} into the model.
   *
   * @param connection The connection to the DBMS.
   * @param jhSanction The JusticeHands' sanction to save.
   * @return The number of affected rows.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public int save(@NotNull Connection connection, @NotNull JhSanction jhSanction)
      throws SQLException {
    try (PreparedStatement stmt =
        connection.prepareStatement(
            "INSERT INTO fv_jh_sanction (sctn_inculpated_player_uuid, sctn_name, "
                + "sctn_reason, sctn_points, sctn_beginning_date, sctn_ending_date, "
                + "sctn_author_player_uuid, sctn_type)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
      setSanction(jhSanction, stmt, false);
      return stmt.executeUpdate();
    }
  }

  /**
   * Updates the specified {@link JhSanction} in the model.
   *
   * @param connection The connection to the DBMS.
   * @param jhSanction The updates instance of the JusticeHands' sanction to update.
   * @return The number of affected rows.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public int update(@NotNull Connection connection, @NotNull JhSanction jhSanction)
      throws SQLException {
    try (PreparedStatement stmt =
        connection.prepareStatement(
            "UPDATE fv_jh_sanction SET sctn_inculpated_player_uuid = ?, sctn_name = ?, "
                + "sctn_reason = ?, sctn_points = ?, sctn_beginning_date = ?, "
                + "sctn_ending_date = ?, sctn_author_player_uuid = ?, sctn_type = ? "
                + "WHERE sctn_id = ?")) {
      setSanction(jhSanction, stmt, true);
      return stmt.executeUpdate();
    }
  }

  /**
   * Deletes the specified {@link JhSanction} from the model.
   *
   * @param connection The connection to the DBMS.
   * @param jhSanction The JusticeHands' sanction to delete from the model.
   * @return The number of affected rows.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public int delete(@NotNull Connection connection, @NotNull JhSanction jhSanction)
      throws SQLException {
    try (PreparedStatement stmt =
        connection.prepareStatement("DELETE FROM fv_jh_sanction WHERE sctn_id = ?")) {
      stmt.setInt(1, jhSanction.getSctnId());
      return stmt.executeUpdate();
    }
  }

  private @NotNull JhSanction getSanction(@NotNull ResultSet rs) throws SQLException {
    JhSanction jhSanction = new JhSanction(rs.getInt("sctn_id"));
    jhSanction.setSctnInculpatedPlayer(
        server.getOfflinePlayer(UUID.fromString(rs.getString("sctn_inculpated_player_uuid"))));
    jhSanction.setSctnName(rs.getString("sctn_name"));
    jhSanction.setSctnReason(rs.getString("sctn_reason"));
    jhSanction.setSctnPoints(rs.getInt("sctn_points"));
    jhSanction.setSctnBeginningDate(rs.getTimestamp("sctn_beginning_date"));
    jhSanction.setSctnEndingDate(rs.getTimestamp("sctn_ending_date"));
    jhSanction.setSctnAuthorPlayer(
        server.getOfflinePlayer(UUID.fromString(rs.getString("sctn_author_player_uuid"))));
    jhSanction.setSctnType(SanctionType.valueOf(rs.getString("sctn_type")));
    return jhSanction;
  }

  private @NotNull Set<JhSanction> getSanctions(@NotNull PreparedStatement stmt)
      throws SQLException {
    ResultSet rs = stmt.executeQuery();
    Set<JhSanction> jhSanctions = new LinkedHashSet<>(rs.getFetchSize());
    while (rs.next()) {
      JhSanction jhSanction = getSanction(rs);
      jhSanctions.add(jhSanction);
    }
    return jhSanctions;
  }

  private void setSanction(
      @NotNull JhSanction jhSanction, @NotNull PreparedStatement stmt, boolean idIsLast)
      throws SQLException {
    int index = 1;
    boolean idIsFirst = !idIsLast;
    if (idIsFirst) {
      stmt.setInt(index++, jhSanction.getSctnId());
    }
    stmt.setString(index++, jhSanction.getSctnInculpatedPlayer().getUniqueId().toString());
    stmt.setString(index++, jhSanction.getSctnName());
    stmt.setString(index++, jhSanction.getSctnReason());
    stmt.setInt(index++, jhSanction.getSctnPoints());
    stmt.setTimestamp(index++, jhSanction.getSctnBeginningDate());
    stmt.setTimestamp(index++, jhSanction.getSctnEndingDate());
    stmt.setString(index++, jhSanction.getSctnAuthorPlayer().getUniqueId().toString());
    stmt.setString(index++, jhSanction.getSctnType().name());
    if (idIsLast) {
      stmt.setInt(index, jhSanction.getSctnId());
    }
  }
}