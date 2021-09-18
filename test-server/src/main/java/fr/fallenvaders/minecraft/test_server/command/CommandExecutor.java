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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Command executor class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public final class CommandExecutor {

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

  // TODO: execute in another thread
  /**
   * Executes the specified Java command.
   *
   * @param javaCommandProperties The Java command properties of the command to execute.
   * @throws CommandExecutionException if something went wrong during the execution.
   */
  public void execute(@NotNull JavaCommandProperties javaCommandProperties) throws CommandExecutionException {
    Objects.requireNonNull(javaCommandProperties);
    List<String> javaCommand = javaCommandBuilder.build(javaCommandProperties);
    ProcessBuilder pb = new ProcessBuilder(javaCommand);
    pb.directory(new File(javaCommandProperties.workingDirectory()));
    pb.inheritIO();
    try {
      Process p = pb.start();
      p.waitFor();
    } catch (InterruptedException e) {
      // TODO: fix SonarLint warning
      throw new CommandExecutionException("Server interrupted!", e);
    } catch (IOException e) {
      throw new CommandExecutionException(
          "Failed to launch test server! Maybe wrong default working directory setup?", e);
    }
  }
}
