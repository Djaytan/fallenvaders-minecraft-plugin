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

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Represents a terminal's command.
 *
 * @author Voltariuss
 * @since 0.3.0
 *
 * @param args The command arguments.
 * @param workingDirectory The directory where the command must be executed.
 */
public record TerminalCommand (@NotNull List<String> args, @NotNull Path workingDirectory) {

  /**
   * Constructor.
   *
   * @param args The command arguments.
   * @param workingDirectory The directory where the command must be executed.
   */
  public TerminalCommand(@NotNull List<String> args, @NotNull Path workingDirectory) {
    Objects.requireNonNull(args);
    Objects.requireNonNull(workingDirectory);
    this.args = args;
    this.workingDirectory = workingDirectory;
  }
}
