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

package fr.fallenvaders.minecraft.justicehands.view.gui;

import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import javax.inject.Singleton;
import org.jetbrains.annotations.Nullable;

/**
 * The container of {@link SanctionManagerView}.
 *
 * <p>When the filter is null, this mean that no filter is applied and all {@link Sanction}s are
 * displayed.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class CriminalRecordViewContainer {

  private SanctionType filter;

  /** Constructor. */
  public CriminalRecordViewContainer() {
    this.filter = null;
  }

  /**
   * Getter.
   *
   * @return The type of sanction to only keep on display.
   */
  public @Nullable SanctionType getFilter() {
    return filter;
  }

  /**
   * Setter.
   *
   * @param filter The type of sanction to only keep on display.
   */
  public void setFilter(@Nullable SanctionType filter) {
    this.filter = filter;
  }
}
