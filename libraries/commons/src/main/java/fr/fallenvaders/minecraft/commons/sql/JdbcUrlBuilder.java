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

import javax.inject.Singleton;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class permits the build of a JDBC URL.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class JdbcUrlBuilder {

  public static final String JDBC_PROTOCOL = "jdbc";

  /**
   * Builds the JDBC URL properly according to the specified arguments.
   *
   * @param dbmsDriver The DBMS driver.
   * @param host The DBMS server host.
   * @param port The DBMS server port.
   * @param database The database in which to connect.
   * @return The well-formed JDBC URL.
   * @throws MalformedURLException If the protocol or the port is invalid.
   */
  public @NotNull URL buildUrl(
      @NotNull String dbmsDriver, @NotNull String host, int port, @NotNull String database)
      throws MalformedURLException {
    String protocol = String.join(":", JDBC_PROTOCOL, dbmsDriver);
    return new URL(protocol, host, port, database);
  }
}
