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

import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

/**
 * This is an implementation class {@link CriticalErrorRaiser} in case of FallenVaders' plugin.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class CriticalPluginErrorRaiser implements CriticalErrorRaiser {

  private final JavaPlugin plugin;
  private final Logger logger;
  private final PluginManager pluginManager;

  /**
   * Constructor.
   *
   * @param plugin The Bukkit Java plugin.
   * @param logger The logger.
   * @param pluginManager The Bukkit plugin manager.
   */
  @Inject
  public CriticalPluginErrorRaiser(
      @NotNull JavaPlugin plugin, @NotNull Logger logger, @NotNull PluginManager pluginManager) {
    this.plugin = plugin;
    this.logger = logger;
    this.pluginManager = pluginManager;
  }

  @Override
  public void raiseError(@Nullable String message) {
    raiseError(message, null);
  }

  @Override
  public void raiseError(@Nullable String message, @Nullable Throwable cause) {
    if (message != null) {
      if (cause != null) {
        logger.error(message, cause);
      } else {
        logger.error(message);
      }
    }
    pluginManager.disablePlugin(plugin);
  }
}
