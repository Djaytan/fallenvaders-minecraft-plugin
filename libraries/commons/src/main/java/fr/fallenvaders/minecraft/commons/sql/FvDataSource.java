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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is the FallenVaders' data source class in order to obtain a {@link Connection} instantiate
 * for SQL dispatching requests purposes.
 *
 * <p>This data source class have his basis state through a connection pool library. The one used
 * here is the HikariCP connection pool implementation. This tool is the most powerful about
 * performances and fulfil the really basic expectations of the FallenVaders project: permit to
 * recovering multiple connections instances successfully in a fast and easy way.
 *
 * <p>The number of available connection is the same as the default value of the library: 10. In the
 * case where this limit is reached, a connectionTimeout permit determining at the end of how much
 * time the client of the pool (here is us) must stop to wait a connection by receiving and
 * exception.
 *
 * <p>In the context of the project, it's necessary to set the autoCommit option to "false" for 1)
 * performances and 2) transactions reliability purposes.
 *
 * <p>When you want to release the connection, simply close her directly through the method {@link
 * Connection#close()}. The library is in charge of detecting closed connections in order to reopen
 * new ones.
 *
 * <p>For more details about the HikariCP library, refer you to this link: <a
 * href="https://github.com/brettwooldridge/HikariCP">https://github.com/brettwooldridge/HikariCP</a>.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class FvDataSource {

  private final HikariDataSource dataSource;

  /**
   * Constructor of the data source based on the HikariCP connection pool library.
   *
   * <p>During the initialization process, a {@link HikariConfig} is created in order to instantiate
   * the {@link HikariDataSource} class. This one permits then to deliver already well-established
   * DBMS connections.
   *
   * <p>All specific configurations stated here are just the result of following author's tips when
   * using a MySQL DBMS server: <a
   * href="https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration">https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration</a>
   *
   * @param jdbcUrlBuilder The JDBC URL builder.
   * @param dbmsAccessInfo The information about DBMS access.
   * @throws MalformedURLException If the protocol or the port is invalid.
   */
  @Inject
  public FvDataSource(
      @NotNull JdbcUrlBuilder jdbcUrlBuilder, @NotNull DbmsAccessInfo dbmsAccessInfo)
      throws MalformedURLException {
    HikariConfig config = new HikariConfig();
    URL jdbcUrl =
        jdbcUrlBuilder.buildUrl(
            dbmsAccessInfo.dbmsDriver().getDriver(),
            dbmsAccessInfo.host(),
            dbmsAccessInfo.port(),
            dbmsAccessInfo.database());
    config.setJdbcUrl(jdbcUrl.toString());
    config.setUsername(dbmsAccessInfo.username());
    config.setPassword(dbmsAccessInfo.password());
    config.addDataSourceProperty("autoCommit", "true");
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("cacheResultSetMetadata", "true");
    config.addDataSourceProperty("cacheServerConfiguration", "true");
    config.addDataSourceProperty("elideSetAutoCommits", "true");
    config.addDataSourceProperty("maintainTimeStats", "false");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.addDataSourceProperty("rewriteBatchedStatements", "true");
    config.addDataSourceProperty("useServerPrepStmts", "true");
    config.addDataSourceProperty("useLocalSessionState", "true");
    dataSource = new HikariDataSource(config);
  }

  /**
   * Gets through HikariCP a DBMS connection and then return it.
   *
   * <p>When you want to release the connection, simply close her directly through the method {@link
   * Connection#close()}.
   *
   * <p>For more details about connection pool, refer you to this class' Javadoc.
   *
   * @return The obtained DBMS connection though the connection pool system.
   * @throws SQLException If a database access error occurs
   */
  public @NotNull Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
