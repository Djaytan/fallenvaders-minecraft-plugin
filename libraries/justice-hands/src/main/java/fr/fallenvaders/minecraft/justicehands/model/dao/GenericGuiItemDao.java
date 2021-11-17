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
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiItemLocation;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Generic {@link GuiItem} DAO class.
 *
 * <p>Note: here, only GUI items described under the section "clickable-items" are recovered.
 * Embedded items of {@link GuiInventory}s are ignored.
 *
 * @author Voltariuss
 * @see ReadOnlyDao
 * @since 0.3.0
 */
@Singleton
public class GenericGuiItemDao implements ReadOnlyDao<GuiItem> {

  private final FileConfiguration config;

  /**
   * Constructor.
   *
   * @param config The {@link FileConfiguration} of the plugin.
   */
  @Inject
  public GenericGuiItemDao(@NotNull FileConfiguration config) {
    this.config = config;
  }

  @Override
  public @NotNull Optional<GuiItem> get(@NotNull String id) throws DaoException {
    // TODO: optimize it
    return getGenericGuiItems().stream().filter(guiItem -> guiItem.id().equals(id)).findFirst();
  }

  @Override
  public @NotNull Set<GuiItem> getAll() throws DaoException {
    return getGenericGuiItems();
  }

  private @NotNull Set<GuiItem> getGenericGuiItems() throws DaoException {
    Set<GuiItem> guiItems = new LinkedHashSet<>();
    try {
      ConfigurationSection clickableItems =
          Objects.requireNonNull(
              config.getConfigurationSection("justicehands.gui.clickable-items"));

      for (String clickableItemKey : clickableItems.getKeys(false)) {
        ConfigurationSection clickableItem =
            Objects.requireNonNull(config.getConfigurationSection(clickableItemKey));
        GuiItem guiItem = getGuiItem(clickableItem, clickableItemKey);
        guiItems.add(guiItem);
      }
    } catch (NullPointerException e) {
      throw new DaoException("Failed to read the config file.", e);
    }
    return guiItems;
  }

  @NotNull
  GuiItem getGuiItem(@NotNull ConfigurationSection parentSection, @NotNull String itemKey) {
    GuiItemLocation location = parentSection.getObject("location", GuiItemLocation.class);
    ItemStack item = parentSection.getItemStack("item");
    return new GuiItem(itemKey, location, item);
  }
}
