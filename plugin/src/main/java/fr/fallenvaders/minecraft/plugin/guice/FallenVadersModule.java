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
import fr.fallenvaders.minecraft.commons.CriticalErrorRaiser;
import fr.fallenvaders.minecraft.commons.CriticalPluginErrorRaiser;
import fr.fallenvaders.minecraft.commons.sql.DbmsAccessInfo;
import fr.fallenvaders.minecraft.commons.sql.DbmsDriver;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionDispatcher;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionDispatcherImpl;
import fr.fallenvaders.minecraft.plugin.PropertyFactory;
import fr.fallenvaders.minecraft.plugin.modules.FullPluginInitializer;
import fr.fallenvaders.minecraft.plugin.modules.PluginInitializer;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginLoader;
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
   * @param plugin The {@link JavaPlugin}.
   */
  public FallenVadersModule(@NotNull JavaPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void configure() {
    bind(PluginInitializer.class).to(FullPluginInitializer.class);

    /* Commons */
    bind(CriticalErrorRaiser.class).to(CriticalPluginErrorRaiser.class);

    /* JusticeHands */
    bind(SanctionDispatcher.class).to(SanctionDispatcherImpl.class);
  }

  @Provides
  @Singleton
  public @NotNull JavaPlugin providesPlugin() {
    return plugin;
  }

  @Provides
  @Singleton
  public @NotNull Server providesServer() {
    return plugin.getServer();
  }

  @Provides
  @Singleton
  public @NotNull PluginLoader providesPluginLoader() {
    return plugin.getPluginLoader();
  }

  @Provides
  @Singleton
  public @NotNull ConsoleCommandSender providesConsoleSender() {
    return plugin.getServer().getConsoleSender();
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
  public @NotNull Logger providesSLF4JLogger() {
    return plugin.getSLF4JLogger();
  }

  @Provides
  @Singleton
  public @NotNull PaperCommandManager providesPaperCommandManager() {
    return new PaperCommandManager(plugin);
  }

  @Provides
  @Singleton
  public @NotNull InventoryManager providesInventoryManager() {
    return new InventoryManager(plugin);
  }

  @Provides
  @Singleton
  public @NotNull DbmsAccessInfo providesDbmsAccessInfo() {
    String dbmsDriver = PropertyFactory.getNotNull("fr.fallenvaders.database.driver");
    String host = PropertyFactory.getNotNull("fr.fallenvaders.database.host");
    int port = Integer.parseInt(PropertyFactory.getNotNull("fr.fallenvaders.database.port"));
    String database = PropertyFactory.getNotNull("fr.fallenvaders.database.database");
    String username = PropertyFactory.getNotNull("fr.fallenvaders.database.username");
    String password = PropertyFactory.getNotNull("fr.fallenvaders.database.password");
    return new DbmsAccessInfo(
        DbmsDriver.valueOf(dbmsDriver.toUpperCase()), host, port, database, username, password);
  }
}
