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

package fr.fallenvaders.minecraft.test_server.deploy;

import javax.inject.Singleton;
import java.nio.file.Path;

/**
 * FallenVaders's plugin deployer class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public final class FVPluginDeployer {

  /**
   * Deletes the old plugin if it exists in order to deploy the new one. To remove the plugin, his
   * core name is required to found it independently of his version.
   *
   * @param pluginsDirectory The server plugins directory.
   * @param pluginCoreName The core name of the plugin to remove.
   */
  public void deleteOldPlugin(Path pluginsDirectory, String pluginCoreName) {}

  /**
   * Create the FallenVaders plugin in order to deploy it.
   *
   * @param pluginProjectLocation The project location of the plugin to create.
   */
  public void createPlugin(Path pluginProjectLocation) {}

  /**
   * Deploys the plugin into the test-server by moving a copy of the jar file.
   *
   * @param pluginLocation The location of the plugin jar file.
   * @param deployLocation The location where to deploy the plugin.
   */
  public void deployPlugin(Path pluginLocation, Path deployLocation) {}
}
