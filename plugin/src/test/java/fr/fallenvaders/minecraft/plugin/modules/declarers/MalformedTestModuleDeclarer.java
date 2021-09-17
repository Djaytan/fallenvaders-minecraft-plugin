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

import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;

/**
 * This class represents a malformed test {@link ModuleDeclarer} implementation.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@SuppressWarnings("ConstantConditions")
public class MalformedTestModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "malformed-test-module";

  public MalformedTestModuleDeclarer() {
    super(null, MODULE_NAME); // This code will never be executed
  }

  @Override
  public void onEnable() {
    // Nothing to do
  }

  @Override
  public void onDisable() {
    // Nothing to do
  }
}
