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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * This is a factory to instantiate the {@link ModuleDeclarer} class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public final class ModuleDeclarerFactory {

  private final JavaPlugin javaPlugin;

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  @Inject
  public ModuleDeclarerFactory(@NotNull JavaPlugin javaPlugin) {
    Objects.requireNonNull(javaPlugin);
    this.javaPlugin = javaPlugin;
  }

  /**
   * Creates a {@link ModuleDeclarer} according to the specified module's class.
   *
   * @param moduleClass The module's class to instantiate.
   * @return a {@link ModuleDeclarer} according to the specified module's class.
   */
  @NotNull
  public ModuleDeclarer createModule(@NotNull Class<? extends ModuleDeclarer> moduleClass)
      throws ModuleRegisterException {
    Objects.requireNonNull(moduleClass);

    try {
      return moduleClass.getDeclaredConstructor(JavaPlugin.class).newInstance(javaPlugin);
    } catch (Exception e) {
      throw new ModuleRegisterException(
          String.format(
              "Something went wrong during the instantiation of the module class '%s'.",
              moduleClass.getName()),
          e);
    }
  }
}
