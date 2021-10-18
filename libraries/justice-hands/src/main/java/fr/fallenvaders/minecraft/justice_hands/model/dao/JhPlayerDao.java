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
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhPlayer;
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
 * DAO class of the {@link JhPlayer} entity class.
 *
 * <p>For more information about DAO pattern, see {@link FvDao} interface Javadoc.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 * @see FvDao
 */
@Singleton
public class JhPlayerDao implements FvDao<JhPlayer> {

  // TODO: FV-116, FV-117 - optimisation with cache

  private final FvDataSource fvDataSource;

  /**
   * Constructor.
   *
   * @param fvDataSource The FallenVaders' data source.
   */
  @Inject
  public JhPlayerDao(@NotNull FvDataSource fvDataSource) {
    this.fvDataSource = fvDataSource;
  }

  /**
   * Gets and returns the {@link JhPlayer} from the model which match with the specified UUID.
   *
   * @param strUuid The UUID of the sought JusticeHands' player.
   * @return The found JusticeHands' player if it exists.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  @NotNull
  public Optional<JhPlayer> get(@NotNull String strUuid) throws SQLException {
    UUID uuid = UUID.fromString(strUuid);
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement("SELECT points FROM fv_jh_player WHERE uuid = ?")) {
      stmt.setString(1, uuid.toString());
      ResultSet rs = stmt.executeQuery();
      JhPlayer jhPlayer = null;
      if (rs.first()) {
        jhPlayer = new JhPlayer(uuid, rs.getInt("points"));
      }
      return Optional.ofNullable(jhPlayer);
    }
  }

  /**
   * Gets and returns all the {@link JhPlayer}s from the model.
   *
   * @return All the JusticeHands' players from the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  @NotNull
  public List<JhPlayer> getAll() throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement("SELECT uuid, points FROM fv_jh_player")) {
      ResultSet rs = stmt.executeQuery();
      List<JhPlayer> jhPlayers = new ArrayList<>(rs.getFetchSize());
      while (rs.next()) {
        UUID uuid = UUID.fromString(rs.getString("uuid"));
        JhPlayer jhPlayer = new JhPlayer(uuid, rs.getInt("points"));
        jhPlayers.add(jhPlayer);
      }
      return jhPlayers;
    }
  }

  /**
   * Saves the {@link JhPlayer} into the model.
   *
   * @param jhPlayer The JusticeHands' player to save.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public void save(@NotNull JhPlayer jhPlayer) throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(
                "INSERT INTO fv_jh_player (uuid, points) VALUES (?, ?)")) {
      stmt.setString(1, jhPlayer.getUuid().toString());
      stmt.setInt(2, jhPlayer.getPoints());
      stmt.executeUpdate();
    }
  }

  /**
   * Updates the specified {@link JhPlayer}.
   *
   * @param jhPlayer The FallenVaders' player to update.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public void update(@NotNull JhPlayer jhPlayer) throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement("UPDATE fv_jh_player SET points = ? WHERE uuid = ?")) {
      stmt.setInt(1, jhPlayer.getPoints());
      stmt.setString(2, jhPlayer.getUuid().toString());
      int rowCount = stmt.executeUpdate();
      if (rowCount == 0) {
        throw new SQLException(
            String.format(
                "The FallenVaders' player with UUID '%s' is not registered and then can't be updated.",
                jhPlayer.getUuid().toString()));
      }
    }
  }

  /**
   * Deletes the specified {@link JhPlayer}.
   *
   * @param jhPlayer The FallenVaders' player to delete.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @Override
  public void delete(@NotNull JhPlayer jhPlayer) throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement("DELETE FROM fv_jh_player WHERE uuid = ?")) {
      stmt.setString(1, jhPlayer.getUuid().toString());
      int rowCount = stmt.executeUpdate();
      if (rowCount == 0) {
        throw new SQLException(
            String.format(
                "The FallenVaders' player with UUID '%s' is not registered in the model and then can't be deleted.",
                jhPlayer.getUuid().toString()));
      }
    }
  }
}
