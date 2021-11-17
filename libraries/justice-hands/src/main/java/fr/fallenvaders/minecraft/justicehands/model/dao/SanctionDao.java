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

import fr.fallenvaders.minecraft.commons.CriticalErrorRaiser;
import fr.fallenvaders.minecraft.commons.dao.Dao;
import fr.fallenvaders.minecraft.commons.dao.DaoException;
import fr.fallenvaders.minecraft.commons.sql.FvDataSource;
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
 * DAO class about manipulation of {@link Sanction}s in the model.
 *
 * @author Voltariuss
 * @since 0.3.0
 * @see Dao
 */
@Singleton
public class SanctionDao implements Dao<Sanction> {

  // TODO: FV-116, FV-117 - optimisation with cache

  private final CriticalErrorRaiser criticalErrorRaiser;
  private final FvDataSource fvDataSource;
  private final Server server;

  /**
   * Constructor.
   *
   * @param criticalErrorRaiser The critical error raiser.
   * @param fvDataSource The FallenVaders' data source.
   * @param server The Bukkit server.
   */
  @Inject
  public SanctionDao(
      @NotNull CriticalErrorRaiser criticalErrorRaiser,
      @NotNull FvDataSource fvDataSource,
      @NotNull Server server) {
    this.fvDataSource = fvDataSource;
    this.criticalErrorRaiser = criticalErrorRaiser;
    this.server = server;
  }

  @Override
  public @NotNull Optional<Sanction> get(@NotNull String strId) throws DaoException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
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
   */
  @Override
  public @NotNull Set<Sanction> getAll() throws DaoException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM fv_jh_sanction")) {
      return getSanctions(stmt);
    } catch (SQLException e) {
      throw new DaoException("Failed to read the sanctions.", e);
    }
  }

  /**
   * Gets and returns all existing {@link Sanction}s where the specified {@link OfflinePlayer} is
   * associated with them as an inculpated player.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @param player The Bukkit offline player.
   * @return The list of all existing sanctions for the specified {@link OfflinePlayer}.
   */
  public @NotNull Set<Sanction> getFromPlayer(@NotNull OfflinePlayer player) throws DaoException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(
                "SELECT * FROM fv_jh_sanction WHERE sctn_inculpated_player_uuid = ?")) {
      stmt.setString(1, player.getUniqueId().toString());
      return getSanctions(stmt);
    } catch (SQLException e) {
      throw new DaoException("Failed to read the sanctions.", e);
    }
  }

  @Override
  public void save(@NotNull Sanction sanction) throws DaoException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(
                "INSERT INTO fv_jh_sanction (sctn_inculpated_player_uuid, sctn_name, "
                    + "sctn_reason, sctn_points, sctn_beginning_date, sctn_ending_date, "
                    + "sctn_author_player_uuid, sctn_type)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
      setSanction(sanction, stmt, false);
      int rowCount = stmt.executeUpdate();
      if (rowCount == 0) {
        throw new DaoException("No sanction registered in database.");
      }
      if (rowCount > 1) {
        criticalErrorRaiser.raise("More than one sanction have been registered!");
      }
    } catch (SQLException | DaoException e) {
      throw new DaoException("Failed to save the sanction.", e);
    }
  }

  @Override
  public void update(@NotNull Sanction sanction) throws DaoException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(
                "UPDATE fv_jh_sanction SET sctn_inculpated_player_uuid = ?, sctn_name = ?, "
                    + "sctn_reason = ?, sctn_points = ?, sctn_beginning_date = ?, "
                    + "sctn_ending_date = ?, sctn_author_player_uuid = ?, sctn_type = ? "
                    + "WHERE sctn_id = ?")) {
      setSanction(sanction, stmt, true);
      int rowCount = stmt.executeUpdate();
      if (rowCount == 0) {
        throw new DaoException("No sanction updated in database.");
      }
      if (rowCount > 1) {
        criticalErrorRaiser.raise("More than one sanction have been updated!");
      }
    } catch (SQLException e) {
      throw new DaoException("Failed to update the sanction.", e);
    }
  }

  @Override
  public void delete(@NotNull Sanction sanction) throws DaoException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement("DELETE FROM fv_jh_sanction WHERE sctn_id = ?")) {
      stmt.setInt(1, sanction.id());
      int rowCount = stmt.executeUpdate();
      if (rowCount == 0) {
        throw new DaoException("No sanction deleted in database.");
      }
      if (rowCount > 1) {
        criticalErrorRaiser.raise("More than one sanction have been deleted!");
      }
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
    String authorPlayerUuid = rs.getString("sctn_author_player_uuid");
    String typeStr = rs.getString("sctn_type");

    OfflinePlayer inculpatedPlayer = server.getOfflinePlayer(UUID.fromString(inculpatedPlayerUuid));
    OfflinePlayer authorPlayer =
        authorPlayerUuid != null
            ? server.getOfflinePlayer(UUID.fromString(authorPlayerUuid))
            : null;
    SanctionType type = SanctionType.valueOf(typeStr);

    return new Sanction(
        id, inculpatedPlayer, name, reason, points, beginningDate, endingDate, authorPlayer, type);
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
      @NotNull Sanction sanction, @NotNull PreparedStatement stmt, boolean hasId)
      throws SQLException {
    int id = sanction.id();
    OfflinePlayer inculpatedPlayer = sanction.inculpatedPlayer();
    String name = sanction.name();
    String reason = sanction.reason();
    int points = sanction.points();
    Timestamp beginningDate = sanction.beginningDate();
    Timestamp endingDate = sanction.endingDate();
    OfflinePlayer authorPlayer = sanction.authorPlayer();
    SanctionType type = sanction.type();

    String inculpatedPlayerUuid = inculpatedPlayer.getUniqueId().toString();
    String authorPlayerUuid = authorPlayer != null ? authorPlayer.getUniqueId().toString() : null;
    String typeStr = type.name();

    int index = 1;
    stmt.setString(index++, inculpatedPlayerUuid);
    stmt.setString(index++, name);
    stmt.setString(index++, reason);
    stmt.setInt(index++, points);
    stmt.setTimestamp(index++, beginningDate);
    stmt.setTimestamp(index++, endingDate);
    stmt.setString(index++, authorPlayerUuid);
    stmt.setString(index, typeStr);
    if (hasId) {
      stmt.setInt(++index, id);
    }
  }
}
