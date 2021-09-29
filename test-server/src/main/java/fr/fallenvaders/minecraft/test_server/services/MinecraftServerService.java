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

import fr.fallenvaders.minecraft.test_server.JavaCommandBuilder;
import fr.fallenvaders.minecraft.test_server.command.CommandExecutor;
import fr.fallenvaders.minecraft.test_server.command.TerminalCommand;
import fr.fallenvaders.minecraft.test_server.deploy.DeploymentException;
import fr.fallenvaders.minecraft.test_server.deploy.FVPluginJarNameAssembler;
import fr.fallenvaders.minecraft.test_server.properties.ProgramProperties;
import fr.fallenvaders.minecraft.test_server.properties.ProgramPropertiesRegister;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;

/**
 * The service to manage the server.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public final class MinecraftServerService {

  private static final Logger logger = LoggerFactory.getLogger(MinecraftServerService.class);

  private final CommandExecutor commandExecutor;
  private final FVPluginJarNameAssembler fvPluginJarNameAssembler;
  private final JavaCommandBuilder javaCommandBuilder;
  private final PluginDeployerService pluginDeployerService;
  private final ProgramPropertiesRegister programPropertiesRegister;

  /**
   * Constructor.
   *
   * @param commandExecutor The command executor.
   * @param fvPluginJarNameAssembler The assembler of the jar name of FV plugin.
   * @param javaCommandBuilder The Java command builder.
   * @param pluginDeployerService The FV plugin deployer.
   * @param programPropertiesRegister The {@link ProgramProperties}'s register.
   */
  @Inject
  public MinecraftServerService(
      @NotNull CommandExecutor commandExecutor,
      @NotNull FVPluginJarNameAssembler fvPluginJarNameAssembler,
      @NotNull JavaCommandBuilder javaCommandBuilder,
      @NotNull PluginDeployerService pluginDeployerService,
      @NotNull ProgramPropertiesRegister programPropertiesRegister) {
    this.commandExecutor = commandExecutor;
    this.pluginDeployerService = pluginDeployerService;
    this.fvPluginJarNameAssembler = fvPluginJarNameAssembler;
    this.javaCommandBuilder = javaCommandBuilder;
    this.programPropertiesRegister = programPropertiesRegister;
  }

  /**
   * Starts the server according to the config properties. If an exception is thrown during the
   * execution of the Java command, an error is logged and then the program exits with code error
   * -1.
   *
   * @throws DeploymentException If the deployment of the FallenVaders plugin has failed.
   */
  public void launchServer() throws DeploymentException {
    ProgramProperties programProperties = programPropertiesRegister.getProgramProperties();
    logger.info("Preparing test server...");
    prepareServer(programProperties);
    logger.info("Preparing test server -> done.");
    logger.info("Launching test server...");
    TerminalCommand terminalCommand =
        javaCommandBuilder.build(
            programProperties.mcServerJvmArgs(),
            programProperties.mcServerJarName(),
            programProperties.mcServerProgramArgs(),
            programProperties.mcServerLocation());
    commandExecutor.execute(terminalCommand);
  }

  private void prepareServer(@NotNull ProgramProperties programProperties)
      throws DeploymentException {
    pluginDeployerService.deleteOldPlugin(
        programProperties.mcServerLocation(), programProperties.fvPluginJarCoreName());
    pluginDeployerService.createPlugin(
        programProperties.fvPluginProjectLocation(), programProperties.fvPluginBuildCommand());
    pluginDeployerService.deployPlugin(
        getFvPluginLocation(programProperties), programProperties.mcServerLocation());
  }

  private Path getFvPluginLocation(@NotNull ProgramProperties programProperties) {
    String fvPluginJarName =
        fvPluginJarNameAssembler.assemble(
            programProperties.fvPluginJarCoreName(),
            programProperties.projectVersion(),
            programProperties.fvPluginJarComplementName());
    return programProperties.fvPluginArtifactLocation().resolve(fvPluginJarName);
  }
}
