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

import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "JusticeHands" module.
 *
 * @author Glynix
 * @since 0.1.0
 */
public final class JusticeHandsModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "justice-hands";

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  public JusticeHandsModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, MODULE_NAME);
  }

  @Override
  public void onEnable() {
    JusticeHands.enableModule(getJavaPlugin());
  }

  @Override
  public void onDisable() {
    JusticeHands.disableModule(getJavaPlugin());
  }
}
