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

import org.jetbrains.annotations.Nullable;

/**
 * This class may be used in situations of critical errors which should never happen in most of the
 * cases with big potential consequences.
 *
 * <p>It is relevant to use it when we can't succeed to found an ideal solution for an exception
 * handling case whereas impact of the error may be important or more. In these situations, a good
 * solution may be to shut down the program until an administrator treat the problem. This is what
 * it is done in the case of the FallenVaders plugin.
 *
 * <p>Example: at the update of one entity in the database, we realized that several entities have
 * been affected by the update... Do we must consider it as an exception which may be a false error?
 * Or should we just mask it and just log an error message? What happen if we throw an exception in
 * case of a false error? Maybe bad behaviors may occur... But is it not the same in case where we
 * only log the error?
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public interface CriticalError {

  /**
   * Raises the critical error by applying a special treatment.
   *
   * @param message The description message of the critical error.
   */
  void raiseError(@Nullable String message);

  /**
   * Raises the critical error by applying a special treatment.
   *
   * @param message The description message of the critical error.
   * @param cause The cause of the critical error.
   */
  void raiseError(@Nullable String message, @Nullable Throwable cause);
}
