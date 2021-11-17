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

package fr.fallenvaders.minecraft.justicehands.model.dao;

import fr.fallenvaders.minecraft.commons.dao.DaoException;
import fr.fallenvaders.minecraft.commons.dao.ReadOnlyDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiItem;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link GuiInventory} DAO class.
 *
 * @author Voltariuss
 * @see ReadOnlyDao
 * @since 0.3.0
 */
@Singleton
public class GuiInventoryDao implements ReadOnlyDao<GuiInventory> {

  private final FileConfiguration config;
  private final GenericGuiItemDao genericGuiItemDao;

  /**
   * Constructor.
   *
   * @param config The {@link FileConfiguration} of the plugin.
   * @param genericGuiItemDao The {@link GenericGuiItemDao}.
   */
  @Inject
  public GuiInventoryDao(
      @NotNull FileConfiguration config,
      @NotNull GenericGuiItemDao genericGuiItemDao) {
    this.config = config;
    this.genericGuiItemDao = genericGuiItemDao;
  }

  @Override
  public @NotNull Optional<GuiInventory> get(@NotNull String id) throws DaoException {
    return getGuiInventories().stream()
        .filter(guiInventory -> guiInventory.id().equals(id))
        .findFirst();
  }

  @Override
  public @NotNull Set<GuiInventory> getAll() throws DaoException {
    return getGuiInventories();
  }

  private @NotNull Set<GuiInventory> getGuiInventories() throws DaoException {
    // TODO: simplify it with getObject use maybe
    Set<GuiInventory> guiInventories = new LinkedHashSet<>();
    try {
      ConfigurationSection inventories =
          Objects.requireNonNull(config.getConfigurationSection("justicehands.gui.inventories"));

      for (String inventoryKey : inventories.getKeys(false)) {
        ConfigurationSection inventory =
            Objects.requireNonNull(config.getConfigurationSection(inventoryKey));
        GuiInventory guiInventory = getGuiInventory(inventory, inventoryKey);
        guiInventories.add(guiInventory);
      }
    } catch (NullPointerException e) {
      throw new DaoException("Failed to read the config file.", e);
    }
    return guiInventories;
  }

  private @NotNull GuiInventory getGuiInventory(
      @NotNull ConfigurationSection inventory, @NotNull String inventoryKey) {
    int nbLines = Integer.parseInt(Objects.requireNonNull(inventory.getString("number-lines")));
    List<GuiItem> items = new ArrayList<>();

    ConfigurationSection inventoryItems =
        Objects.requireNonNull(inventory.getConfigurationSection("items"));

    for (String inventoryItemKey : inventoryItems.getKeys(false)) {
      ConfigurationSection inventoryItem =
          Objects.requireNonNull(inventoryItems.getConfigurationSection(inventoryItemKey));
      GuiItem guiItem =
          genericGuiItemDao.getGuiItem(inventoryItem, inventoryItemKey);
      items.add(guiItem);
    }

    return new GuiInventory(inventoryKey, nbLines, items);
  }
}
