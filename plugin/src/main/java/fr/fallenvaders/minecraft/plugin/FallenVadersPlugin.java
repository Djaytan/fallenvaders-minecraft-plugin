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

import fr.fallenvaders.minecraft.plugin.guice.FallenVadersInjector;
import fr.fallenvaders.minecraft.plugin.modules.ModuleException;
import fr.fallenvaders.minecraft.plugin.modules.ModuleService;
import fr.fallenvaders.minecraft.plugin.modules.PluginInitializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class represents the Bukkit plugin.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
@Singleton
public final class FallenVadersPlugin extends JavaPlugin {

  @Inject private PluginInitializer moduleRegInit;
  @Inject private ModuleService moduleService;

  @Override
  public void onLoad() {
    // Guice setup
    FallenVadersInjector.inject(this);

    // Modules initialization
    moduleRegInit.initialize();
    moduleService.loadModules();
  }

  @Override
  public void onEnable() {
    // Config preparation
    this.saveDefaultConfig();

    // Enable modules
    moduleService.enableModules();
  }

  @Override
  public void onDisable() {
    moduleService.disableModules();
  }
}
