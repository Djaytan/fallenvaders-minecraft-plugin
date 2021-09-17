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

import fr.fallenvaders.minecraft.plugin.modules.declarers.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * This is an implementation class of {@link ModuleRegisterInitializer} interface. The purpose of
 * this initializer is to assemble a full version of the plugin by injecting all the existing {@link
 * ModuleDeclarer}s.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
@Singleton
public final class CompleteModuleRegisterInitializer implements ModuleRegisterInitializer {

  private final Logger logger;
  private final ModuleRegisterService moduleRegisterService;

  /**
   * Constructor.
   *
   * @param logger The SLF4J project's logger.
   * @param moduleRegisterService The module register service.
   */
  @Inject
  public CompleteModuleRegisterInitializer(
      @NotNull Logger logger, @NotNull ModuleRegisterService moduleRegisterService) {
    Objects.requireNonNull(logger);
    Objects.requireNonNull(moduleRegisterService);
    this.logger = logger;
    this.moduleRegisterService = moduleRegisterService;
  }

  @Override
  public void initialize() throws ModuleRegisterException {
    logger.info("Start modules registration.");
    moduleRegisterService.registerModule(FallenVadersCoreModuleDeclarer.class);
    moduleRegisterService.registerModule(JusticeHandsModuleDeclarer.class);
    moduleRegisterService.registerModule(MailBoxModuleDeclarer.class);
    moduleRegisterService.registerModule(MinecraftEnhancerModuleDeclarer.class);
    moduleRegisterService.registerModule(MiniEventsModuleDeclarer.class);
    logger.info("Modules registration done.");
  }
}
