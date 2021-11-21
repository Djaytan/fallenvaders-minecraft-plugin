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

package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.commons.PropertiesUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class PropertyFactory {

  private static final Map<String, String> propertiesMap = new HashMap<>();

  private PropertyFactory() {}

  public static void initialize() throws IOException {
    Properties properties = PropertiesUtils.getProperties(PropertyFactory.class, "config.properties");
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      String key = entry.getKey().toString();
      String value = entry.getValue().toString();
      propertiesMap.put(key, value);
    }
  }

  public static @Nullable String get(@NotNull String key) {
    return propertiesMap.getOrDefault(key, null);
  }

  public static @NotNull String getNotNull(@NotNull String key) {
    return Objects.requireNonNull(propertiesMap.getOrDefault(key, null));
  }
}
