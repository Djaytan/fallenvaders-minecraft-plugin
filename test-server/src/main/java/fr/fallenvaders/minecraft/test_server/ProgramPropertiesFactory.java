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

import fr.fallenvaders.minecraft.test_server.deploy.FVPluginJarNameAssembler;
import fr.fallenvaders.minecraft.test_server.guice.ConfigProperties;
import fr.fallenvaders.minecraft.test_server.guice.DebugMode;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Factory of {@link ProgramProperties}.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public final class ProgramPropertiesFactory {

  private final boolean debugMode;
  private final Properties config;

  private final FVPluginJarNameAssembler fvPluginJarNameAssembler;

  /**
   * Constructor.
   *
   * @param debugMode Tells if the program is in debug mode.
   * @param config The config properties of the test server.
   * @param fvPluginJarNameAssembler The FV's plugin jar name assembler.
   */
  @Inject
  public ProgramPropertiesFactory(
      @DebugMode boolean debugMode,
      @NotNull @ConfigProperties Properties config,
      @NotNull FVPluginJarNameAssembler fvPluginJarNameAssembler) {
    Objects.requireNonNull(config);
    Objects.requireNonNull(fvPluginJarNameAssembler);
    this.debugMode = debugMode;
    this.config = config;
    this.fvPluginJarNameAssembler = fvPluginJarNameAssembler;
  }

  /**
   * Creates a {@link ProgramProperties} with the config properties.
   *
   * @return The created {@link ProgramProperties} instance.
   */
  @NotNull
  public ProgramProperties createProgramProperties() {
    String projectVersion = config.getProperty("fr.fallenvaders.version");
    String jarName = config.getProperty("fr.fallenvaders.server.jar.name");
    Path workingDirectory = Path.of(config.getProperty("fv.fallenvaders.server.working_directory"));
    Path pluginsDirectory = getPluginsDirectory(workingDirectory);
    List<String> jvmArgs = getJvmArgs();
    List<String> programArgs = getProgramArgs();
    Path pluginProjectLocation =
        Path.of(config.getProperty("fr.fallenvaders.server.plugin.project.location"));
    String pluginJarName = assembleJarName(projectVersion);
    String mavenCommand = config.getProperty("fr.fallenvaders.server.plugin.maven.command");
    Path mavenArtifactLocation = getMavenArtifactLocation(pluginProjectLocation);
    return new ProgramProperties(
        projectVersion,
        programArgs,
        workingDirectory,
        pluginsDirectory,
        jarName,
        jvmArgs,
        pluginProjectLocation,
        pluginJarName,
        mavenCommand,
        mavenArtifactLocation);
  }

  @NotNull
  private List<String> getJvmArgs() {
    String jvmArgs = config.getProperty("fr.fallenvaders.server.jvm.args");
    if (debugMode) {
      jvmArgs += " " + config.getProperty("fr.fallenvaders.server.jvm.args.debug");
    }
    return List.of(jvmArgs.split(" "));
  }

  @NotNull
  private List<String> getProgramArgs() {
    String programArgs = config.getProperty("fr.fallenvaders.server.program.args");
    return List.of(programArgs.split(" "));
  }

  @NotNull
  private Path getPluginsDirectory(@NotNull Path workingDirectory) {
    String pluginsDirectory = config.getProperty("fr.fallenvaders.server.plugins_directory");
    return workingDirectory.resolve(pluginsDirectory);
  }

  @NotNull
  private String assembleJarName(@NotNull String projectVersion) {
    String baseName = config.getProperty("fr.fallenvaders.server.plugin.jar.name.core");
    String complementName = config.getProperty("fr.fallenvaders.server.plugin.jar.name.complement");
    return fvPluginJarNameAssembler.assemble(baseName, projectVersion, complementName);
  }

  @NotNull
  private Path getMavenArtifactLocation(@NotNull Path pluginProjectLocation) {
    String mavenArtifactLocation =
        config.getProperty("fr.fallenvaders.server.plugin.maven.artifact.location");
    return pluginProjectLocation.resolve(mavenArtifactLocation);
  }
}
