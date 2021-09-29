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

package fr.fallenvaders.minecraft.test_server.services;

import fr.fallenvaders.minecraft.test_server.command.CommandExecutor;
import fr.fallenvaders.minecraft.test_server.command.TerminalCommand;
import fr.fallenvaders.minecraft.test_server.deploy.DeploymentException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * FallenVaders's plugin deployer class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public final class PluginDeployerService {

  private static final String SERVER_PLUGINS_LOCATION = "plugins/";

  private static final Logger logger = LoggerFactory.getLogger(PluginDeployerService.class);

  private final CommandExecutor commandExecutor;

  /**
   * Constructor.
   *
   * @param commandExecutor The command executor.
   */
  public PluginDeployerService(@NotNull CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  /**
   * Deletes the old plugin if it exists in order to deploy the new one. To remove the plugin, his
   * core name is required to found it independently of his version.
   *
   * @param pluginCoreName The core name of the plugin to remove.
   * @throws DeploymentException if an I/O error occurs during the delete file process.
   */
  public void deleteOldPlugin(@NotNull String pluginCoreName)
      throws DeploymentException {
    try (Stream<Path> pluginStreamPath =
        Files.find(
            Paths.get(SERVER_PLUGINS_LOCATION),
            1,
            (path, basicFileAttributes) -> path.getFileName().startsWith(pluginCoreName))) {
      Path pluginPath = pluginStreamPath.findFirst().orElse(null);
      if (pluginPath != null) {
        Files.delete(pluginPath);
        // TODO: precise where the remove happen
        logger.info("Old plugin file successfully deleted.");
      } else {
        logger.info("No old plugin file to delete.");
      }
    } catch (IOException e) {
      throw new DeploymentException("Failed to delete the old plugin file.", e);
    }
  }

  /**
   * Create the FallenVaders plugin in order to deploy it.
   *
   * @param pluginProjectLocation The project location of the plugin to create.
   */
  public void createPlugin(Path pluginProjectLocation, String strMavenCommand) {
    List<String> mavenCommand = List.of(strMavenCommand.split(" "));
    TerminalCommand terminalCommand = new TerminalCommand(mavenCommand, pluginProjectLocation);
    commandExecutor.execute(terminalCommand);
  }

  /**
   * Deploys the plugin into the test-server by moving a copy of the jar file.
   *
   * @param pluginLocation The location of the plugin jar file.
   * @param deployLocation The location where to deploy the plugin.
   * @throws DeploymentException If the deployment of the plugin has failed because of an I/O error.
   */
  public void deployPlugin(Path pluginLocation, Path deployLocation) throws DeploymentException {
    try {
      Files.copy(pluginLocation, deployLocation);
    } catch (IOException e) {
      throw new DeploymentException("The deployment of the plugin has failed.");
    }
  }
}
