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

package fr.fallenvaders.minecraft.justice_hands.model.dao;

import fr.fallenvaders.minecraft.commons.sql.FvDao;
import fr.fallenvaders.minecraft.commons.sql.FvDataSource;
import fr.fallenvaders.minecraft.justice_hands.SanctionType;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhSanction;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  private final FvDataSource fvDataSource;

  /**
   * Constructor.
   *
   * @param server The Bukkit server in which the JusticeHands' module is executed.
   * @param fvDataSource The FallenVaders' data source.
   */
  @Inject
  public JhSanctionDao(@NotNull Server server, @NotNull FvDataSource fvDataSource) {
    this.server = server;
    this.fvDataSource = fvDataSource;
  }

  /**
   * Gets and returns the {@link JhSanction} which correspond to the specified ID from the model.
   *
   * @param strId The ID of the sought sanction.
   * @return The JusticeHands' sanction associated to the specified ID if it exists.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public @NotNull Optional<JhSanction> get(@NotNull String strId) throws SQLException {
    int id = Integer.parseInt(strId);
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement("SELECT * FROM fv_jh_sanction WHERE sctn_id = ?")) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      JhSanction jhSanction = null;
      if (rs.first()) {
        jhSanction = getSanction(rs);
      }
      return Optional.ofNullable(jhSanction);
    }
  }

  /**
   * Gets and returns all existing {@link JhSanction}s from the model.
   *
   * @return The list of all existing JusticeHands' sanctions.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public @NotNull List<JhSanction> getAll() throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM fv_jh_sanction")) {
      ResultSet rs = stmt.executeQuery();
      List<JhSanction> jhSanctions = new ArrayList<>(rs.getFetchSize());
      while (rs.next()) {
        JhSanction jhSanction = getSanction(rs);
        jhSanctions.add(jhSanction);
      }
      return jhSanctions;
    }
  }

  /**
   * Saves a new {@link JhSanction} into the model.
   *
   * @param jhSanction The JusticeHands' sanction to save.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public void save(@NotNull JhSanction jhSanction) throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(
                "INSERT INTO fv_jh_sanction (sctn_inculpated_player_uuid, sctn_name, "
                    + "sctn_reason, sctn_points, sctn_beginning_date, sctn_ending_date, "
                    + "sctn_author_player_uuid, sctn_type, sctn_state)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      setSanction(jhSanction, stmt, false);
      stmt.executeUpdate();
    }
  }

  /**
   * Updates the specified {@link JhSanction} in the model.
   *
   * @param jhSanction The updates instance of the JusticeHands' sanction to update.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public void update(@NotNull JhSanction jhSanction) throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(
                "UPDATE fv_jh_sanction SET sctn_inculpated_player_uuid = ?, sctn_name = ?, "
                    + "sctn_reason = ?, sctn_points = ?, sctn_beginning_date = ?, "
                    + "sctn_ending_date = ?, sctn_author_player_uuid = ?, sctn_type = ?, "
                    + "sctn_state = ? WHERE sctn_id = ?")) {
      setSanction(jhSanction, stmt, true);
      int rowCount = stmt.executeUpdate();
      if (rowCount == 0) {
        throw new SQLException(
            String.format(
                "The JusticeHands' sanction with ID '%d' doesn't exists and then can't be updated.",
                jhSanction.getSctnId()));
      }
    }
  }

  /**
   * Deletes the specified {@link JhSanction} from the model.
   *
   * @param jhSanction The JusticeHands' sanction to delete from the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public void delete(@NotNull JhSanction jhSanction) throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
         PreparedStatement stmt =
           connection.prepareStatement("DELETE FROM fv_jh_sanction WHERE sctn_id = ?")) {
      stmt.setInt(1, jhSanction.getSctnId());
      int rowCount = stmt.executeUpdate();
      if (rowCount == 0) {
        throw new SQLException(
          String.format(
            "The JusticeHands' sanction with ID '%d' doesn't exists and then can't be deleted.",
            jhSanction.getSctnId()));
      }
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
    jhSanction.setSctnState(rs.getString("sctn_state"));
    return jhSanction;
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
    stmt.setString(index++, jhSanction.getSctnState());
    if (idIsLast) {
      stmt.setInt(index, jhSanction.getSctnId());
    }
  }
}
