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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Command executor class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public final class CommandExecutor {

  private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

  // TODO: make it configurable
  private static final String SERVER_LOCATION = "server/";

  // TODO: execute in another thread
  /**
   * Executes the specified Java command.
   *
   * @param javaCommand The Java command to execute.
   * @throws CommandExecutionException if something went wrong during the execution.
   */
  public void execute(List<String> javaCommand) throws CommandExecutionException {
    ProcessBuilder pb = new ProcessBuilder(javaCommand);
    String strCommand = String.join(" ", pb.command());
    logger.info("Executed command: {}", strCommand);
    pb.directory(new File(SERVER_LOCATION));
    pb.inheritIO();
    try {
      Process p = pb.start();
      p.waitFor();
    } catch (InterruptedException e) {
      // TODO: fix SonarLint warning
      throw new CommandExecutionException("Server interrupted!", e);
    } catch (IOException e) {
      throw new CommandExecutionException("Failed to launch test server! Maybe wrong default working directory setup?", e);
    }
  }
}
