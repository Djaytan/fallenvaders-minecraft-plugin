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

import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.commons.IOUtils;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class permits to initialize the database according to each FallenVaders' modules' needs.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class ModuleDatabaseInitializer {

  private static final String SQL_INIT_SCRIPT_NAME = "initialize.sql";

  private final FvDataSource fvDataSource;
  private final IOUtils ioUtils;

  /**
   * Constructor.
   *
   * @param fvDataSource The {@link FvDataSource}.
   * @param ioUtils The {@link IOUtils}.
   */
  @Inject
  public ModuleDatabaseInitializer(@NotNull FvDataSource fvDataSource, @NotNull IOUtils ioUtils) {
    this.fvDataSource = fvDataSource;
    this.ioUtils = ioUtils;
  }

  /**
   * Initializes the database by executing an SQL script located in the resources' folder of the
   * caller module.
   *
   * @param classModule The {@link FvModule} implementation class of the FallenVaders' module which
   *     call this method in order to recover the specified SQL script.
   * @param sqlScriptResource The SQL script located in the 'resources/' folder of the module
   *     project.
   * @throws IOException If something went wrong or the SQL init script can't be found.
   * @throws SQLException If something went wrong when dispatching the script to the database.
   */
  public void initialize(
      @NotNull Class<? extends FvModule> classModule, @NotNull String sqlScriptResource)
      throws IOException, SQLException {
    try (InputStream is = classModule.getClassLoader().getResourceAsStream(sqlScriptResource)) {
      if (is != null) {
        String sqlInitScript = ioUtils.resourceToString(is);
        try (Connection connection = fvDataSource.getConnection();
            Statement stmt = connection.createStatement()) {
          stmt.addBatch(sqlInitScript);
          stmt.executeBatch();
        }
      } else {
        throw new IOException(
            String.format("SQL init script '%s' not found.", SQL_INIT_SCRIPT_NAME));
      }
    }
  }
}
