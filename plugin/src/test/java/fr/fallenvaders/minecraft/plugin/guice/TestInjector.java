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

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Test injector class from Guice.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class TestInjector {

  /** Constructor. */
  private TestInjector() {}

  /**
   * Injects dependencies through Guice.
   *
   * @param javaPlugin The mocked Bukkit plugin.
   * @param testObject The test object to inject dependencies.
   */
  public static void inject(@NotNull JavaPlugin javaPlugin, @NotNull Object testObject) {
    FallenVadersModule module = new FallenVadersModule(javaPlugin);
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(testObject);
  }
}
