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
 * @param programArgs The program arguments.
 * @param workingDirectory The directory from where the command must be launched.
 * @param pluginsDirectory The location of the plugins of the server.
 * @param jarName The jar file name.
 * @param jvmArgs The JVM arguments.
 * @param pluginProjectLocation The location of the FV plugin project.
 * @param pluginJarName The jar file name of the FV plugin.
 * @param mavenCommand The Maven command to execute in order to build the plugin.
 * @param mavenArtifactLocation The location of the Maven's built artifact (=plugin).
 */
public record ProgramProperties(
  @NotNull String projectVersion,
  @NotNull List<String> programArgs,
  @NotNull Path workingDirectory,
  @NotNull Path pluginsDirectory,
  @NotNull String jarName,
  @NotNull List<String> jvmArgs,
  @NotNull Path pluginProjectLocation,
  @NotNull String pluginJarName,
  @NotNull String mavenCommand,
  @NotNull Path mavenArtifactLocation) {}
