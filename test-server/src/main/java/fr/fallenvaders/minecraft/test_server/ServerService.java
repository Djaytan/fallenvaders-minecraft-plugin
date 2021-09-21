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

package fr.fallenvaders.minecraft.test_server;

import fr.fallenvaders.minecraft.test_server.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * The service to manage the server.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public final class ServerService {

  private static final Logger logger = LoggerFactory.getLogger(ServerService.class);

  private final CommandExecutor commandExecutor;
  private final ProgramPropertiesFactory programPropertiesFactory;

  /**
   * Constructor.
   *
   * @param commandExecutor The command executor.
   * @param programPropertiesFactory The {@link ProgramProperties}'s factory.
   */
  @Inject
  public ServerService(
      @NotNull CommandExecutor commandExecutor,
      @NotNull ProgramPropertiesFactory programPropertiesFactory) {
    Objects.requireNonNull(commandExecutor);
    Objects.requireNonNull(programPropertiesFactory);
    this.commandExecutor = commandExecutor;
    this.programPropertiesFactory = programPropertiesFactory;
  }

  /**
   * Starts the server according to the config properties. If an exception is thrown during the
   * execution of the Java command, an error is logged and then the program exits with code error
   * -1.
   */
  public void startServer() {
    ProgramProperties programProperties = programPropertiesFactory.createProgramProperties();
    logger.info("Launching test server...");
    commandExecutor.execute(programProperties);
  }
}