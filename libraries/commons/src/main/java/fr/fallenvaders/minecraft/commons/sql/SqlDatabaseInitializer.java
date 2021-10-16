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

package fr.fallenvaders.minecraft.commons.sql;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is in charge of initialization of the database in the DBMS server. This is useful for
 * charset and collate definition when it's not well-stated in database server side.
 *
 * @author FallenVaders' dev team.
 * @since 0.3.0
 */
@Singleton
public class SqlDatabaseInitializer {

  private final FvDataSource fvDataSource;
  private final DbmsAccessInfo dbmsAccessInfo;

  /**
   * Constructor.
   *
   * @param fvDataSource The DBMS connection deliver.
   */
  @Inject
  public SqlDatabaseInitializer(
      @NotNull FvDataSource fvDataSource, @NotNull DbmsAccessInfo dbmsAccessInfo) {
    this.fvDataSource = fvDataSource;
    this.dbmsAccessInfo = dbmsAccessInfo;
  }

  /**
   * Ensures that the FallenVaders' actually used database through established connections is well
   * configured.
   *
   * <p>In all cases, the Charset is defined on 'utf8' and the collate to 'utf8_general_ci'.
   *
   * @throws SQLException if an SQL error occurs.
   */
  public void initialize() throws SQLException {
    try (Connection connection = fvDataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(
                "ALTER DATABASE `?` CHARACTER SET = 'utf8' COLLATE = 'utf8_general_ci';")) {
      stmt.setString(1, dbmsAccessInfo.database());
      stmt.executeUpdate();
    }
  }
}
