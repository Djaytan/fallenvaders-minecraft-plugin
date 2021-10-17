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

package fr.fallenvaders.minecraft.commons;

/**
 * This is the interface for a FallenVaders' module declaration. It is mandatory to implement this
 * interface in each module of the project in order to make possible the registration of the module
 * in the plugin properly.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public interface FvModule {

  /** Defines the logic needed to initialize and make operational the FallenVaders' module. */
  void enable();

  /** Defines the logic need to properly disable the FallenVaders' module. */
  void disable();
}
