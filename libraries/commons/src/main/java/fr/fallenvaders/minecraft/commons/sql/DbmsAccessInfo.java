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

/**
 * This record represents information about DBMS access.
 *
 * @author Voltariuss
 * @since 0.3.0
 *
 * @param dbmsDriver The DBMS driver to use during the connection establishment.
 * @param host The host of the DBMS server.
 * @param port The port of the DBMS server.
 * @param database The targeted database for the connection.
 * @param username The name of the user with which to connect.
 * @param password The password of the user with which to connect.
 */
public record DbmsAccessInfo (
  @NotNull DbmsDriver dbmsDriver,
  @NotNull String host,
  @NotNull int port,
  @NotNull String database,
  @NotNull String username,
  @NotNull String password) {}
