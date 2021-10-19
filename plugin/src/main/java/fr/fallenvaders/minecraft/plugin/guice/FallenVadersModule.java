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

package fr.fallenvaders.minecraft.plugin.guice;

import co.aikar.commands.PaperCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import fr.fallenvaders.minecraft.plugin.modules.FullPluginInitializer;
import fr.fallenvaders.minecraft.plugin.modules.PluginInitializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Guice module of the project.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public final class FallenVadersModule extends AbstractModule {

  private final JavaPlugin plugin;

  /**
   * Constructor.
   *
   * @param plugin The Bukkit plugin.
   */
  public FallenVadersModule(@NotNull JavaPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void configure() {
    bind(PluginInitializer.class).to(FullPluginInitializer.class);
  }

  @Provides
  @Singleton
  public @NotNull JavaPlugin providesPlugin() {
    return plugin;
  }

  @Provides
  @Singleton
  public @NotNull PluginManager providesPluginManager() {
    return plugin.getServer().getPluginManager();
  }

  @Provides
  @Singleton
  public @NotNull FileConfiguration providesFileConfiguration() {
    return plugin.getConfig();
  }

  @Provides
  @Singleton
  public @NotNull Logger getSLF4JLogger() {
    return plugin.getSLF4JLogger();
  }

  @Provides
  @Singleton
  public @NotNull PaperCommandManager getPaperCommandManager() {
    return new PaperCommandManager(plugin);
  }
}
