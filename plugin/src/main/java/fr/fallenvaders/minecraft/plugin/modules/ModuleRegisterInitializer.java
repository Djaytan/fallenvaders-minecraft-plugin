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

/**
 * This class is in charge to initialize the {@link ModuleRegisterService} singleton instance.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public interface ModuleRegisterInitializer {

  /**
   * Initializes {@link ModuleRegisterService} singleton instance with {@link ModuleDeclarer}
   * instances.
   *
   * @throws ModuleRegisterException if the initialization into the {@link ModuleRegisterService}
   *     fail.
   */
  void initialize() throws ModuleRegisterException;
}
