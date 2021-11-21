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

package fr.fallenvaders.minecraft.justicehands;

import fr.fallenvaders.minecraft.justicehands.view.listeners.AsyncChatListener;
import fr.fallenvaders.minecraft.justicehands.view.listeners.PlayerLoginListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class is in charge of initializing all listeners of the module.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class ListenersInitializer {

  private final JavaPlugin plugin;
  private final PluginManager pluginManager;

  private final AsyncChatListener asyncChatListener;
  private final PlayerLoginListener playerLoginListener;

  /**
   * Constructor.
   *
   * @param plugin The {@link JavaPlugin}.
   * @param pluginManager The {@link PluginManager}.
   * @param asyncChatListener The {@link AsyncChatListener}.
   * @param playerLoginListener The {@link PlayerLoginListener}.
   */
  @Inject
  public ListenersInitializer(
      @NotNull JavaPlugin plugin,
      @NotNull PluginManager pluginManager,
      @NotNull AsyncChatListener asyncChatListener,
      @NotNull PlayerLoginListener playerLoginListener) {
    this.plugin = plugin;
    this.pluginManager = pluginManager;
    this.asyncChatListener = asyncChatListener;
    this.playerLoginListener = playerLoginListener;
  }

  /** Initializes listeners of the module. */
  public void initialize() {
    pluginManager.registerEvents(asyncChatListener, plugin);
    pluginManager.registerEvents(playerLoginListener, plugin);
  }
}
