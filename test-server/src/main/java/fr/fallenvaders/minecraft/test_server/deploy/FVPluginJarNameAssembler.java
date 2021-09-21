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

import javax.inject.Singleton;
import java.util.StringJoiner;

/**
 * Assembler of the FallenVaders's plugin jar file name.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class FVPluginJarNameAssembler {

  private static final String DELIMITER = "-";
  private static final String FILE_EXTENSION = ".jar";

  /**
   * Assembles the FallenVaders's plugin jar file name according to the specified
   * values.
   *
   * @param baseName The base name of the jar file (=plugin's name).
   * @param version The version of the plugin.
   * @param complement The complement name of the jar file.
   * @return The assembled FallenVaders's plugin jar file name.
   */
  @NotNull
  public String assemble(
      @NotNull String baseName, @NotNull String version, @NotNull String complement) {
    StringJoiner sb = new StringJoiner(DELIMITER);
    sb.add(baseName);
    sb.add(version);
    sb.add(complement);
    sb.add(FILE_EXTENSION);
    return sb.toString();
  }
}
