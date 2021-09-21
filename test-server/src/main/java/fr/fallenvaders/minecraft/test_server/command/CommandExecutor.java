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

import fr.fallenvaders.minecraft.test_server.ProgramProperties;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.nio.file.Paths;
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

  private final JavaCommandBuilder javaCommandBuilder;

  /**
   * Constructor.
   *
   * @param javaCommandBuilder The Java command builder.
   */
  @Inject
  public CommandExecutor(@NotNull JavaCommandBuilder javaCommandBuilder) {
    Objects.requireNonNull(javaCommandBuilder);
    this.javaCommandBuilder = javaCommandBuilder;
  }

  /**
   * Executes the specified Java command.
   *
   * @param programProperties The program's properties.
   */
  public void execute(@NotNull ProgramProperties programProperties) {
    Objects.requireNonNull(programProperties);
    TerminalCommand javaCommand = javaCommandBuilder.build(programProperties);
    String strJavaCommand = String.join(" ", javaCommand.args());
    Path path = Paths.get(programProperties.workingDirectory());
    logger.info("Executed command: {}", strJavaCommand);
    logger.info("Working directory: {}", path.toAbsolutePath());
    CommandRunnerThread thread = new CommandRunnerThread(javaCommand);
    thread.start();
  }
}