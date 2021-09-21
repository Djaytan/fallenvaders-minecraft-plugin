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

import com.google.common.collect.Lists;
import fr.fallenvaders.minecraft.test_server.ProgramProperties;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Builder of terminal's command.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public class TerminalCommandBuilder {

  private final String command;

  /**
   * Constructor.
   *
   * @param command The command to execute (e.g. java, mvn, ...).
   */
  public TerminalCommandBuilder(@NotNull String command) {
    Objects.requireNonNull(command);
    this.command = command;
  }

  /**
   * Builds the terminal's command according to the specified {@link ProgramProperties}.
   *
   * @param programProperties The properties of the program.
   * @return The built terminal's command.
   */
  public TerminalCommand build(@NotNull ProgramProperties programProperties) {
    Objects.requireNonNull(command);
    Objects.requireNonNull(programProperties);
    List<String> commandArgs = Lists.newArrayList(command);
    commandArgs.addAll(getCommandArgs(programProperties));
    Path workingDirectory = Paths.get(programProperties.workingDirectory());
    return new TerminalCommand(commandArgs, workingDirectory);
  }

  /**
   * Defines a command arguments of the terminal's command to build.
   *
   * @param programProperties The program's properties.
   * @return A command arguments list according to the specified {@link ProgramProperties}.
   */
  protected List<String> getCommandArgs(@NotNull ProgramProperties programProperties) {
    Objects.requireNonNull(programProperties);
    return programProperties.programArgs();
  }
}
