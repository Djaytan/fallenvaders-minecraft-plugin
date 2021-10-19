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

package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.justice_hands.JusticeHandsModule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This is an implementation class of {@link PluginInitializer} interface.
 *
 * <p>The purpose of this initializer is to assemble a full version of the plugin by injecting all
 * the existing {@link FvModule}s.
 *
 * @author FallenVaders' dev team
 * @since 0.1.0
 */
@Singleton
public final class FullPluginInitializer implements PluginInitializer {

  private final Logger logger;
  private final ModuleService moduleService;

  /* FallenVaders' modules declaration */
  private final JusticeHandsModule justiceHandsModule;

  /**
   * Constructor.
   *
   * @param logger The SLF4J project's logger.
   * @param moduleService The module register service.
   */
  @Inject
  public FullPluginInitializer(
      @NotNull Logger logger,
      @NotNull ModuleService moduleService,
      @NotNull JusticeHandsModule justiceHandsModule) {
    this.logger = logger;
    this.moduleService = moduleService;

    /* FallenVaders' modules declaration */
    this.justiceHandsModule = justiceHandsModule;
  }

  @Override
  public void initialize() {
    moduleService.registerModule(justiceHandsModule);
  }
}
