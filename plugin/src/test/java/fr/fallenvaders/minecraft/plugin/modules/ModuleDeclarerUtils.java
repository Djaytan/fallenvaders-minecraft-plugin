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

package fr.fallenvaders.minecraft.plugin.modules;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * Utils about creation of {@link ModuleDeclarer} for testing purposes.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public class ModuleDeclarerUtils {

  private final JavaPlugin javaPlugin;

  /**
   * Constructor.
   *
   * @param javaPlugin The mocked Bukkit plugin.
   */
  @Inject
  public ModuleDeclarerUtils(@NotNull JavaPlugin javaPlugin) {
    Objects.requireNonNull(javaPlugin);
    this.javaPlugin = javaPlugin;
  }

  /**
   * Creates a {@link ModuleDeclarer} implementation without any behavior.
   *
   * @param moduleName The module name.
   * @return a {@link ModuleDeclarer} implementation without any behavior.
   */
  public ModuleDeclarer createWithoutBehaviorModuleDeclarer(@NotNull String moduleName) {
    return createModuleDeclarer(moduleName, null, null);
  }

  /**
   * Creates a {@link ModuleDeclarer} implementation.
   *
   * @param moduleName The module name.
   * @param onEnable This one is executed when the module is enabled.
   * @param onDisable This one is executed when the module is disabled.
   * @return a {@link ModuleDeclarer} implementation.
   */
  public ModuleDeclarer createModuleDeclarer(
      @NotNull String moduleName, @Nullable Runnable onEnable, @Nullable Runnable onDisable) {
    Objects.requireNonNull(moduleName);

    ModuleDeclarer moduleDeclarer;
    moduleDeclarer =
        new ModuleDeclarer(javaPlugin, moduleName) {
          @Override
          public void onEnable() {
            if (onEnable != null) {
              onEnable.run();
            }
          }

          @Override
          public void onDisable() {
            if (onDisable != null) {
              onDisable.run();
            }
          }
        };
    return moduleDeclarer;
  }
}