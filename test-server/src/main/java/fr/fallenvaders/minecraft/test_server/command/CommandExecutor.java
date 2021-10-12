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

import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Command executor class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public final class CommandExecutor {

  private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

  /**
   * Executes the specified terminal command.
   *
   * @param terminalCommand The terminal command to execute.
   * @return The thread in charge of executing the {@link TerminalCommand}.
   */
  public Thread execute(@NotNull TerminalCommand terminalCommand) {
    String strJavaCommand = String.join(" ", terminalCommand.args());
    Path path = terminalCommand.workingDirectory();
    logger.info("Executed command: {}", strJavaCommand);
    logger.info("Working directory: {}", path.toAbsolutePath());
    CommandRunnerThread thread = new CommandRunnerThread(terminalCommand);
    thread.start();
    return thread;
  }
}
