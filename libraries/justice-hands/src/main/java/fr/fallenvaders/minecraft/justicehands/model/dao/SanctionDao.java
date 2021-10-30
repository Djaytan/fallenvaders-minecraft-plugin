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

import fr.fallenvaders.minecraft.commons.dao.Dao;
import fr.fallenvaders.minecraft.commons.dao.DaoException;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

/**
 * JusticeHands' DAO class about manipulation of sanctions in the model.
 *
 * <p>For more information about DAO pattern, see {@link Dao} interface Javadoc.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 * @see Dao
 */
@Singleton
public class SanctionDao implements Dao<Sanction> {

  // TODO: FV-116, FV-117 - optimisation with cache

  private final Server server;

  /**
   * Constructor.
   *
   * @param server The Bukkit server in which the JusticeHands' module is executed.
   */
  @Inject
  public SanctionDao(@NotNull Server server) {
    this.server = server;
  }

  /**
   * Gets and returns the {@link Sanction} which correspond to the specified ID from the model.
   *
   * @param connection The connection to the DBMS.
   * @param strId The ID of the sought sanction.
   * @return The JusticeHands' sanction associated to the specified ID if it exists.
   * @throws DaoException if something went wrong during database access or stuffs like this.
   */
  @Override
  public @NotNull Optional<Sanction> get(@NotNull Connection connection, @NotNull String strId)
      throws DaoException {
    try (PreparedStatement stmt =
        connection.prepareStatement("SELECT * FROM fv_jh_sanction WHERE sctn_id = ?")) {
      stmt.setInt(1, Integer.parseInt(strId));
      return getSanctions(stmt).stream().findFirst();
    } catch (SQLException e) {
      throw new DaoException("Failed to read the sanction.", e);
    }
  }

  /**
   * Gets and returns all existing {@link Sanction}s from the model.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @param connection The connection to the DBMS.
   * @return The list of all existing JusticeHands' sanctions.
   * @throws DaoException if something went wrong during database access or stuffs like this.
   */
  @Override
  public @NotNull Set<Sanction> getAll(@NotNull Connection connection) throws DaoException {
    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM fv_jh_sanction")) {
      return getSanctions(stmt);
    } catch (SQLException e) {
      throw new DaoException("Failed to read the sanctions.", e);
    }
  }

  /**
   * Gets and returns all existing {@link Sanction}s where the specified {@link OfflinePlayer} is
   * associated with them as an inculpated player.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last *
   * position.
   *
   * @param connection The connection to the DBMS.
   * @param player The Bukkit offline player.
   * @return The list of all existing JusticeHands' sanctions for the specified {@link
   *     OfflinePlayer}.
   * @throws DaoException if something went wrong during database access or stuffs like this.
   */
  public @NotNull Set<Sanction> getFromPlayer(
      @NotNull Connection connection, @NotNull OfflinePlayer player) throws DaoException {
    try (PreparedStatement stmt =
        connection.prepareStatement(
            "SELECT * FROM fv_jh_sanction WHERE sctn_inculpated_player_uuid = ?")) {
      stmt.setString(1, player.getUniqueId().toString());
      return getSanctions(stmt);
    } catch (SQLException e) {
      throw new DaoException("Failed to read the sanctions.", e);
    }
  }

  /**
   * Saves a new {@link Sanction} into the model.
   *
   * @param connection The connection to the DBMS.
   * @param sanction The JusticeHands' sanction to save.
   * @return The number of affected rows.
   * @throws DaoException if something went wrong during database access or stuffs like this.
   */
  @Override
  public int save(@NotNull Connection connection, @NotNull Sanction sanction) throws DaoException {
    try (PreparedStatement stmt =
        connection.prepareStatement(
            "INSERT INTO fv_jh_sanction (sctn_inculpated_player_uuid, sctn_name, "
                + "sctn_reason, sctn_points, sctn_beginning_date, sctn_ending_date, "
                + "sctn_author_player_uuid, sctn_type)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
      setSanction(sanction, stmt, false);
      return stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DaoException("Failed to save the sanction.", e);
    }
  }

  /**
   * Updates the specified {@link Sanction} in the model.
   *
   * @param connection The connection to the DBMS.
   * @param sanction The updates instance of the JusticeHands' sanction to update.
   * @return The number of affected rows.
   * @throws DaoException if something went wrong during database access or stuffs like this.
   */
  @Override
  public int update(@NotNull Connection connection, @NotNull Sanction sanction)
      throws DaoException {
    try (PreparedStatement stmt =
        connection.prepareStatement(
            "UPDATE fv_jh_sanction SET sctn_inculpated_player_uuid = ?, sctn_name = ?, "
                + "sctn_reason = ?, sctn_points = ?, sctn_beginning_date = ?, "
                + "sctn_ending_date = ?, sctn_author_player_uuid = ?, sctn_type = ? "
                + "WHERE sctn_id = ?")) {
      setSanction(sanction, stmt, true);
      return stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DaoException("Failed to update the sanction.", e);
    }
  }

  /**
   * Deletes the specified {@link Sanction} from the model.
   *
   * @param connection The connection to the DBMS.
   * @param sanction The JusticeHands' sanction to delete from the model.
   * @return The number of affected rows.
   * @throws DaoException if something went wrong during database access or stuffs like this.
   */
  @Override
  public int delete(@NotNull Connection connection, @NotNull Sanction sanction)
      throws DaoException {
    try (PreparedStatement stmt =
        connection.prepareStatement("DELETE FROM fv_jh_sanction WHERE sctn_id = ?")) {
      stmt.setInt(1, sanction.getId());
      return stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DaoException("Failed to delete the sanction.", e);
    }
  }

  private @NotNull Sanction getSanction(@NotNull ResultSet rs) throws SQLException {
    int id = rs.getInt("sctn_id");
    String inculpatedPlayerUuid = rs.getString("sctn_inculpated_player_uuid");
    String name = rs.getString("sctn_name");
    String reason = rs.getString("sctn_reason");
    int points = rs.getInt("sctn_points");
    Timestamp beginningDate = rs.getTimestamp("sctn_beginning_date");
    Timestamp endingDate = rs.getTimestamp("sctn_ending_date");
    String authorUuid = rs.getString("sctn_author_player_uuid");
    String type = rs.getString("sctn_type");

    Sanction sanction = new Sanction(id);
    sanction.setInculpatedPlayer(server.getOfflinePlayer(UUID.fromString(inculpatedPlayerUuid)));
    sanction.setName(name);
    sanction.setReason(reason);
    sanction.setPoints(points);
    sanction.setBeginningDate(beginningDate);
    sanction.setEndingDate(endingDate);
    if (authorUuid != null) {
      sanction.setAuthorPlayer(server.getOfflinePlayer(UUID.fromString(authorUuid)));
    }
    sanction.setType(SanctionType.valueOf(type));
    return sanction;
  }

  private @NotNull Set<Sanction> getSanctions(@NotNull PreparedStatement stmt) throws SQLException {
    ResultSet rs = stmt.executeQuery();
    Set<Sanction> sanctions = new LinkedHashSet<>(rs.getFetchSize());
    while (rs.next()) {
      Sanction sanction = getSanction(rs);
      sanctions.add(sanction);
    }
    return sanctions;
  }

  private void setSanction(
      @NotNull Sanction sanction, @NotNull PreparedStatement stmt, boolean idIsLast)
      throws SQLException {
    int index = 1;
    boolean idIsFirst = !idIsLast;
    if (idIsFirst) {
      stmt.setInt(index++, sanction.getId());
    }
    stmt.setString(index++, sanction.getInculpatedPlayer().getUniqueId().toString());
    stmt.setString(index++, sanction.getName());
    stmt.setString(index++, sanction.getReason());
    stmt.setInt(index++, sanction.getPoints());
    stmt.setTimestamp(index++, sanction.getBeginningDate());
    stmt.setTimestamp(index++, sanction.getEndingDate());
    stmt.setString(index++, sanction.getAuthorPlayer().getUniqueId().toString());
    stmt.setString(index++, sanction.getType().name());
    if (idIsLast) {
      stmt.setInt(index, sanction.getId());
    }
  }
}
