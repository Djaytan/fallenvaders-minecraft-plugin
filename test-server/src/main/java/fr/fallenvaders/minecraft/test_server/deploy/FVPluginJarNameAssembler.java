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

package fr.fallenvaders.minecraft.test_server.deploy;

import org.jetbrains.annotations.NotNull;

/**
 * Assembler of the FallenVaders's plugin jar file name.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public interface FVPluginJarNameAssembler {

  /**
   * Assembles the FallenVaders's plugin jar file name according to the specified values.
   *
   * @param fvPluginJarCoreName The core name of the jar file (=plugin's name).
   * @param projectVersion The version of the project (and by extend the version of the plugin).
   * @param fvPluginJarComplementName The complement name of the jar file.
   * @return The assembled FallenVaders's plugin jar file name.
   */
  @NotNull
  String assemble(
      @NotNull String fvPluginJarCoreName,
      @NotNull String projectVersion,
      @NotNull String fvPluginJarComplementName);
}
