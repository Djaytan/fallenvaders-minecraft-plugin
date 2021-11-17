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

package fr.fallenvaders.minecraft.commons;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * I/O utils class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class IOUtils {

  /**
   * Reads the resource associated with the specified {@link InputStream} passed in arguments
   * of the method.
   *
   * @param is The input stream.
   * @return The content of the stream read in a unique string format.
   * @throws IOException if something went wrong.
   */
  @NotNull
  public String resourceToString(@NotNull InputStream is) throws IOException {
    InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(isr)) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    }
    return sb.toString();
  }
}
