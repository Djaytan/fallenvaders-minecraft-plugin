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

package fr.fallenvaders.minecraft.test_server.properties;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

/**
 * A record of the program properties.
 *
 * @author Voltariuss
 * @since 0.3.0
 *
 * @param projectVersion The project's version.
 * @param mcServerJvmArgs The JVM arguments of the test-server launch command.
 * @param mcServerProgramArgs The program arguments of the test-server launch command.
 * @param mcServerLocation The Minecraft test-server location.
 * @param mcServerJarName The jar file name of the test-server's program to execute.
 * @param fvPluginProjectLocation The location of the FV plugin project.
 * @param fvPluginJarCoreName The jar file core name of the FV plugin.
 * @param fvPluginJarComplementName The jar file complement name of the FV plugin.
 * @param fvPluginBuildGoals The Maven goals to build the plugin.
 * @param fvPluginArtifactLocation The location of the built plugin artifact.
 */
public record ProgramProperties(
  @NotNull String projectVersion,
  @NotNull List<String> mcServerJvmArgs,
  @NotNull List<String> mcServerProgramArgs,
  @NotNull Path mcServerLocation,
  @NotNull String mcServerJarName,
  @NotNull Path fvPluginProjectLocation,
  @NotNull String fvPluginJarCoreName,
  @NotNull String fvPluginJarComplementName,
  @NotNull String fvPluginBuildGoals,
  @NotNull Path fvPluginArtifactLocation) {}