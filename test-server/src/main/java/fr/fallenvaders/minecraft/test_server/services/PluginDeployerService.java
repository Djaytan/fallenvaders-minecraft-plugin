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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
  @Inject
  public PluginDeployerService(@NotNull CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  /**
   * Deletes the old plugin if it exists in order to deploy the new one. To remove the plugin, his
   * core name is required to found it independently of his version.
   *
   * @param mcServerLocation The location of the Minecraft test-server.
   * @param fvPluginJarCoreName The core name of the plugin to remove.
   * @throws DeploymentException if an I/O error occurs during the delete file process.
   */
  public void deleteOldPlugin(@NotNull Path mcServerLocation, @NotNull String fvPluginJarCoreName)
      throws DeploymentException {
    Path mcServerPluginsLocation = mcServerLocation.resolve(SERVER_PLUGINS_LOCATION);
    try (Stream<Path> pluginStreamPath =
        Files.find(
            mcServerPluginsLocation,
            1,
            (path, basicFileAttributes) -> path.getFileName().startsWith(fvPluginJarCoreName))) {
      List<Path> result = pluginStreamPath.toList();
      if (result.isEmpty()) {
        logger.info("No old plugin file to delete in the Minecraft test-server.");
      } else {
        logger.info("Old plugin file(s) detected in the Minecraft test-server.");
        for (Path pluginPath : result) {
          Files.delete(pluginPath);
          logger.info("Plugin '{}' deleted.", pluginPath);
        }
      }
    } catch (IOException e) {
      throw new DeploymentException(
          "Failed to delete old plugin file(s) from the Minecraft test-server.", e);
    }
  }

  /**
   * Create the FallenVaders plugin in order to deploy it.
   *
   * @param fvPluginProjectLocation The project location of the plugin to create.
   * @param fvPluginBuildCommand The build command to create the plugin.
   * @throws DeploymentException If the build command execution is interrupted.
   */
  public void createPlugin(
      @NotNull Path fvPluginProjectLocation, @NotNull String fvPluginBuildCommand) throws DeploymentException {
    List<String> buildCommand = List.of(fvPluginBuildCommand.split(" "));
    TerminalCommand terminalCommand = new TerminalCommand(buildCommand, fvPluginProjectLocation);
    logger.info("Launching build of the FallenVaders plugin...");
    Thread thread = commandExecutor.execute(terminalCommand);
    try {
      // TODO: apply SonarLint returns
      thread.wait();
    } catch (InterruptedException e) {
      throw new DeploymentException("The build command execution was interrupted.", e);
    }
    logger.info("Launching build of the FallenVaders plugin -> done.");
  }

  /**
   * Deploys the plugin into the test-server by moving a copy of the jar file.
   *
   * @param fvPluginLocation The location of the plugin jar file.
   * @param mcServerLocation The location of the Minecraft test-server.
   * @throws DeploymentException If the deployment of the plugin has failed because of an I/O error.
   */
  public void deployPlugin(@NotNull Path fvPluginLocation, @NotNull Path mcServerLocation)
      throws DeploymentException {
    try {
      logger.info("Deployment of the FallenVaders plugin in the Minecraft test-server...");
      Files.copy(fvPluginLocation, mcServerLocation.resolve(SERVER_PLUGINS_LOCATION));
      logger.info("Deployment of the FallenVaders plugin in the Minecraft test-server -> done.");
    } catch (IOException e) {
      throw new DeploymentException("The deployment of the plugin has failed.", e);
    }
  }
}
