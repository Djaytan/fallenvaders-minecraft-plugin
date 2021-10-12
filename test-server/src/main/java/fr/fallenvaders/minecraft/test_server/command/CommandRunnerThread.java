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

package fr.fallenvaders.minecraft.test_server.command;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Thread class which run commands concurrently by creating a {@link Process}.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public class CommandRunnerThread extends Thread {

  private static final Logger logger = LoggerFactory.getLogger(CommandRunnerThread.class);

  private final TerminalCommand terminalCommand;

  /**
   * Constructor.
   *
   * @param terminalCommand The terminal's command to run.
   */
  public CommandRunnerThread(@NotNull TerminalCommand terminalCommand) {
    this.terminalCommand = terminalCommand;
  }

  @Override
  public void run() {
    ProcessBuilder pb = new ProcessBuilder(terminalCommand.args());
    pb.directory(new File(terminalCommand.workingDirectory().toAbsolutePath().toString()));
    pb.inheritIO();
    try {
      Process p = pb.start();
      p.waitFor();
    } catch (InterruptedException e) {
      logger.error("Server interrupted!", e);
      interrupt();
    } catch (IOException e) {
      logger.error("Failed to launch test server! Maybe wrong default working directory setup?", e);
    }
  }
}
