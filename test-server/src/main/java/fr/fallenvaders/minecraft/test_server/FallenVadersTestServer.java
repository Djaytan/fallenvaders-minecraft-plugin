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

package fr.fallenvaders.minecraft.test_server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.fallenvaders.minecraft.test_server.deploy.DeploymentException;
import fr.fallenvaders.minecraft.test_server.guice.TestServerModule;
import fr.fallenvaders.minecraft.test_server.services.MinecraftServerService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the test-server program.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public final class FallenVadersTestServer {

  private static final Logger logger = LoggerFactory.getLogger(FallenVadersTestServer.class);

  /**
   * Main method.
   *
   * @param args The program arguments.
   */
  public static void main(@NotNull String[] args) {
    boolean debugMode = isDebugMode(args);
    Injector injector = Guice.createInjector(new TestServerModule(debugMode));
    MinecraftServerService minecraftServerService =
        injector.getInstance(MinecraftServerService.class);
    try {
      minecraftServerService.initServer();
      minecraftServerService.launchServer();
    } catch (DeploymentException e) {
      logger.error("An error occurs during the launch of the Minecraft test-server.", e);
    }
  }

  private static boolean isDebugMode(@NotNull String[] args) {
    boolean debugMode = false;
    if (args.length > 0) {
      if ("debug".equalsIgnoreCase(args[0])) {
        debugMode = true;
      } else {
        throw new IllegalArgumentException("Wrong program arguments: only 'debug' is allowed.");
      }
    }
    return debugMode;
  }
}
