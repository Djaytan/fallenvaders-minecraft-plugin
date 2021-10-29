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

package fr.fallenvaders.minecraft.justicehands.model;

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import org.jetbrains.annotations.NotNull;

/**
 * This interface corresponds to a sanction dispatcher. Its role is to translate a specified {@link
 * Sanction} description into a concrete sanction on server (kick, ban, ...).
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public interface SanctionDispatcher {

  /**
   * Dispatches the specified {@link Sanction} on server.
   *
   * @param sanction The sanction to dispatched on server.
   */
  void dispatchSanction(@NotNull Sanction sanction);
}
