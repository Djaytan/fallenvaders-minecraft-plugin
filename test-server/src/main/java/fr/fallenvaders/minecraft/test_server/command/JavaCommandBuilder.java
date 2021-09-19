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

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Java command builder class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public final class JavaCommandBuilder extends TerminalCommandBuilder {

  private static final String JAVA_COMMAND = "java";
  private static final String JAR_SPECIFICATION_ARG = "-jar";

  /** Constructor. */
  public JavaCommandBuilder() {
    super(JAVA_COMMAND);
  }

  @Override
  protected List<String> getCommandArgs(@NotNull ProgramProperties programProperties) {
    Objects.requireNonNull(programProperties);
    List<String> commandArgs = new ArrayList<>();
    commandArgs.addAll(programProperties.jvmArgs());
    commandArgs.addAll(getJarSpecPart(programProperties.jarName()));
    commandArgs.addAll(programProperties.programArgs());
    return commandArgs;
  }

  @NotNull
  private List<String> getJarSpecPart(String jarFile) {
    return List.of(JAR_SPECIFICATION_ARG, jarFile);
  }
}
