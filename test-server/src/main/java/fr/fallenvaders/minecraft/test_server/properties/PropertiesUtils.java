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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Utils class about properties manipulations.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public final class PropertiesUtils {

  private PropertiesUtils() {}

  /**
   * Gets the well-formed {@link Properties} according to the specified property file.
   *
   * @param propertyFile The property file to convert into {@link Properties}.
   * @return The well-formed {@link Properties} according to the specified property file.
   * @throws IOException if something went wrong during the read of the property file.
   */
  @NotNull
  public static Properties getProperties(@NotNull String propertyFile) throws IOException {
    Properties properties = new Properties();
    try (InputStream inputStream =
        PropertiesUtils.class.getClassLoader().getResourceAsStream(propertyFile)) {
      if (inputStream != null) {
        properties.load(inputStream);
      } else {
        throw new FileNotFoundException(
            String.format("Property file '%s' not found in classpath.", propertyFile));
      }
    }
    return properties;
  }
}
