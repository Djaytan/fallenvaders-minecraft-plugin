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
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.PredefinedSanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * DAO class about manipulation of {@link SanctionCategory}s in the model.
 *
 * @author Voltariuss
 * @see ReadOnlyDao
 * @since 0.3.0
 */
@Singleton
public class SanctionCategoryDao implements ReadOnlyDao<SanctionCategory> {

  private final FileConfiguration config;

  /**
   * Constructor.
   *
   * @param config The {@link FileConfiguration} of the plugin.
   */
  @Inject
  public SanctionCategoryDao(@NotNull FileConfiguration config) {
    this.config = config;
  }

  @Override
  public @NotNull Optional<SanctionCategory> get(@NotNull String id) throws DaoException {
    return getSanctionCategories().stream()
        .filter(sanctionCategory -> sanctionCategory.id().equals(id))
        .findFirst();
  }

  @Override
  public @NotNull Set<SanctionCategory> getAll() throws DaoException {
    return getSanctionCategories();
  }

  private @NotNull Set<SanctionCategory> getSanctionCategories() throws DaoException {
    Set<SanctionCategory> sanctionCategories = new LinkedHashSet<>();
    try {
      ConfigurationSection categories =
          Objects.requireNonNull(
              config.getConfigurationSection("justicehands.sanctions-scale.categories"));

      for (String categoryKey : categories.getKeys(false)) {
        ConfigurationSection category =
            Objects.requireNonNull(categories.getConfigurationSection(categoryKey));
        SanctionCategory sanctionCategory = getSanctionCategory(category, categoryKey);
        sanctionCategories.add(sanctionCategory);
      }
    } catch (NullPointerException e) {
      throw new DaoException("Failed to read the config file.", e);
    }
    return sanctionCategories;
  }

  private @NotNull SanctionCategory getSanctionCategory(
      @NotNull ConfigurationSection category, @NotNull String categoryKey) {
    // TODO: simplify it with getObject use maybe
    Set<PredefinedSanction> predefinedSanctions = new LinkedHashSet<>();

    String categoryName = Objects.requireNonNull(category.getString("name"));
    String categoryDescription = Objects.requireNonNull(category.getString("description"));
    ConfigurationSection sanctions =
        Objects.requireNonNull(category.getConfigurationSection("sanctions"));

    for (String sanctionKey : sanctions.getKeys(false)) {
      ConfigurationSection sanction =
          Objects.requireNonNull(sanctions.getConfigurationSection(sanctionKey));
      PredefinedSanction predefinedSanction = getPredefinedSanction(sanction, sanctionKey);
      predefinedSanctions.add(predefinedSanction);
    }

    return new SanctionCategory(
        categoryKey, categoryName, categoryDescription, predefinedSanctions);
  }

  private @NotNull PredefinedSanction getPredefinedSanction(
      @NotNull ConfigurationSection sanction, @NotNull String sanctionKey) {
    String sanctionName = Objects.requireNonNull(sanction.getString("name"));
    String sanctionDescription = Objects.requireNonNull(sanction.getString("description"));
    int sanctionPoints = sanction.getInt("points");
    String sanctionTypeStr = Objects.requireNonNull(sanction.getString("type"));

    SanctionType sanctionType = SanctionType.valueOf(sanctionTypeStr);

    return new PredefinedSanction(
        sanctionKey, sanctionName, sanctionDescription, sanctionPoints, sanctionType);
  }
}
