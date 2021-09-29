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

import fr.fallenvaders.minecraft.test_server.command.TerminalCommand;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Java command builder class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public final class JavaCommandBuilder {

  private static final String JAVA_COMMAND = "java";
  private static final String JAR_SPECIFICATION_ARG = "-jar";

  /**
   * Builds the terminal command.
   *
   * @param jvmArgs The JVM arguments of the Java command to execute.
   * @param jarName The jar file name to execute.
   * @param programArgs The arguments of the Java program to execute.
   * @param workingDirectory The working directory of the program to execute.
   * @return The built terminal command.
   */
  @NotNull
  public TerminalCommand build(
      @NotNull List<String> jvmArgs,
      @NotNull String jarName,
      @NotNull List<String> programArgs,
      @NotNull Path workingDirectory) {
    List<String> commandArgs = new ArrayList<>();
    commandArgs.add(JAVA_COMMAND);
    commandArgs.addAll(jvmArgs);
    commandArgs.addAll(getJarSpecPart(jarName));
    commandArgs.addAll(programArgs);
    return new TerminalCommand(commandArgs, workingDirectory);
  }

  @NotNull
  private List<String> getJarSpecPart(@NotNull String jarFile) {
    return List.of(JAR_SPECIFICATION_ARG, jarFile);
  }
}
