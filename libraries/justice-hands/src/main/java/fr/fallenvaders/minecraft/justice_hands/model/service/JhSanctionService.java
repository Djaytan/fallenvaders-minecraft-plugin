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

package fr.fallenvaders.minecraft.justice_hands.model.service;

import fr.fallenvaders.minecraft.justice_hands.model.dao.JhSanctionDao;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhPlayer;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhSanction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class which offers services about the manipulation of {@link JhSanction}s in the model.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class JhSanctionService {

  private final Logger logger;
  private final JhSanctionDao jhSanctionDao;

  /**
   * Constructor.
   *
   * @param logger The logger of FallenVaders' plugin.
   * @param jhSanctionDao The {@link JhSanction} DAO.
   */
  @Inject
  public JhSanctionService(@NotNull Logger logger, @NotNull JhSanctionDao jhSanctionDao) {
    this.logger = logger;
    this.jhSanctionDao = jhSanctionDao;
  }
}
