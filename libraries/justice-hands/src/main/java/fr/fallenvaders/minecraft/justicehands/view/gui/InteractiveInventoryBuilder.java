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

import com.google.common.base.Preconditions;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

/**
 * The interactive inventory builder.
 *
 * <p>An interactive inventory is based on a Minecraft inventory in order to display data and allow
 * players to dispatch actions easily.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class InteractiveInventoryBuilder {

  private final InventoryManager inventoryManager;

  /**
   * Constructor.
   *
   * @param inventoryManager The {@link InventoryManager}.
   */
  @Inject
  public InteractiveInventoryBuilder(@NotNull InventoryManager inventoryManager) {
    this.inventoryManager = inventoryManager;
  }

  /**
   * Builds and returns the {@link SmartInventory} based on the specified {@link InventoryProvider}
   * and additional information.
   *
   * @param inventoryProvider The inventory provider.
   * @param id The ID of the menu to open.
   * @param title The title of the menu to open.
   * @param nbLines The number of lines of the menu to open.
   */
  public @NotNull SmartInventory build(
      @NotNull InventoryProvider inventoryProvider,
      @NotNull String id,
      @NotNull String title,
      int nbLines) {
    Preconditions.checkNotNull(inventoryProvider);
    Preconditions.checkNotNull(id);
    Preconditions.checkNotNull(title);

    SmartInventory.Builder builder = SmartInventory.builder();
    builder.title(title);
    builder.provider(inventoryProvider);
    builder.size(nbLines, 9);
    builder.id(id);
    builder.type(InventoryType.CHEST);
    builder.manager(inventoryManager);
    return builder.build();
  }
}
