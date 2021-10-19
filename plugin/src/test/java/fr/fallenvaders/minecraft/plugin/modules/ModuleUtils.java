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

import fr.fallenvaders.minecraft.commons.FvModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Singleton;

/**
 * Utils about creation of {@link FvModule} for testing purposes.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public class ModuleUtils {

  /**
   * Creates a {@link FvModule} implementation without any behavior.
   *
   * @param moduleName The module name.
   * @return a {@link FvModule} implementation without any behavior.
   */
  public @NotNull FvModule createWithoutBehaviorModule(@NotNull String moduleName) {
    return createModule(moduleName, null, null, null);
  }

  /**
   * Creates a {@link FvModule} implementation.
   *
   * @param moduleName The module name.
   * @param onLoad This one is executed when the module is loaded.
   * @param onEnable This one is executed when the module is enabled.
   * @param onDisable This one is executed when the module is disabled.
   * @return a {@link FvModule} test-implementation.
   */
  public @NotNull FvModule createModule(
      @NotNull String moduleName, @Nullable Runnable onLoad, @Nullable Runnable onEnable, @Nullable Runnable onDisable) {
    return new FvModule(moduleName) {
      @Override
      public void onLoad() {
        if (onLoad != null) {
          onLoad.run();
        }
      }

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
  }
}
