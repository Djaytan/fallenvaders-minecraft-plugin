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

import java.util.List;

/**
 * A record of the program properties.
 *
 * @author Voltariuss
 * @since 0.3.0
 *
 * @param programArgs The program arguments.
 * @param workingDirectory The directory from where the command must be launched.
 * @param jarName The jar file name.
 * @param jvmArgs The JVM arguments.
 * @param pluginJarLocation The location of the FV's plugin jar file.
 * @param pluginJarCoreName The core name of the FV's plugin jar file.
 * @param pluginJarVersion The version of the FV's plugin jar file.
 * @param pluginJarComplementName The complement name of the FV's plugin jar file.
 * @param buildCommand The FV's plugin jar build command.
 */
public record ProgramProperties(
  @NotNull List<String> programArgs,
  @NotNull String workingDirectory,
  @NotNull String jarName,
  @NotNull List<String> jvmArgs,
  @NotNull String pluginJarLocation,
  @NotNull String pluginJarCoreName,
  @NotNull String pluginJarVersion,
  @NotNull String pluginJarComplementName,
  @NotNull String buildCommand) {}
