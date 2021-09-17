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

package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "MinecraftEnhance" module.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public final class MinecraftEnhancerModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "minecraft-enhancer";

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  public MinecraftEnhancerModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, MODULE_NAME);
  }

  @Override
  public void onEnable() {
    getJavaPlugin().getCommand("ping").setExecutor(new PingCmd());
  }

  @Override
  public void onDisable() {
    // Nothing to do
  }
}
