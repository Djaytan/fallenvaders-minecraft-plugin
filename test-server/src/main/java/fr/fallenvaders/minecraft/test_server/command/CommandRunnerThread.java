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
import java.util.List;
import java.util.Objects;

/**
 * Thread class which run commands concurrently.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class CommandRunnerThread extends Thread {

  private static final Logger logger = LoggerFactory.getLogger(CommandRunnerThread.class);

  private final List<String> javaCommand;
  private final String workingDirectory;

  /**
   * Constructor.
   *
   * @param javaCommand The Java command to run.
   * @param workingDirectory The working directory of the command to run.
   */
  public CommandRunnerThread(@NotNull List<String> javaCommand, @NotNull String workingDirectory) {
    Objects.requireNonNull(javaCommand);
    Objects.requireNonNull(workingDirectory);
    this.javaCommand = javaCommand;
    this.workingDirectory = workingDirectory;
  }

  @Override
  public void run() {
    ProcessBuilder pb = new ProcessBuilder(javaCommand);
    pb.directory(new File(workingDirectory));
    pb.inheritIO();
    try {
      Process p = pb.start();
      p.waitFor();
    } catch (InterruptedException e) {
      // TODO: fix SonarLint warning
      logger.error("Server interrupted!", e);
    } catch (IOException e) {
      logger.error("Failed to launch test server! Maybe wrong default working directory setup?", e);
    }
  }
}
