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

import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Helper for {@link Component} class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class ComponentHelper {

  public static final String ERROR_MESSAGE =
      "Something went wrong during the connection. Please contact an administrator to report this error.";

  /**
   * Returns the corresponding component which embed the specified message.
   *
   * @param message The message to be embedded into the component.
   * @return The component which embed the specified message.
   */
  public @NotNull Component getComponent(@NotNull String message) {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
  }

  /**
   * Returns an error component which can be useful in case were something went wrong.
   *
   * @return An error component.
   */
  public @NotNull Component getErrorComponent() {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(ERROR_MESSAGE);
  }
}
