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

package fr.fallenvaders.minecraft.test_server.properties;

import fr.fallenvaders.minecraft.test_server.guice.ConfigProperties;
import fr.fallenvaders.minecraft.test_server.guice.DebugMode;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.List;
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

  /**
   * Constructor.
   *
   * @param debugMode Tells if the program is in debug mode.
   * @param config The config properties of the test server.
   */
  @Inject
  public ProgramPropertiesFactory(
      @DebugMode boolean debugMode, @NotNull @ConfigProperties Properties config) {
    this.debugMode = debugMode;
    this.config = config;
  }

  /**
   * Creates a {@link ProgramProperties} with the config properties.
   *
   * @return The created {@link ProgramProperties} instance.
   */
  @NotNull
  public ProgramProperties createProgramProperties() {
    Path fvPluginProjectLocation = getFvPluginProjectLocation();
    return new ProgramProperties(
        getProjectVersion(),
        getMcServerJvmArgs(),
        getMcServerProgramArgs(),
        getMcServerLocation(),
        getMcServerJarName(),
        fvPluginProjectLocation,
        getFvPluginJarCoreName(),
        getFvPluginJarComplementName(),
        getFvPluginBuildGoals(),
        getFvPluginArtifactLocation(fvPluginProjectLocation));
  }

  @NotNull
  private String getProjectVersion() {
    return config.getProperty("fr.fallenvaders.test-server.version");
  }

  @NotNull
  private List<String> getMcServerJvmArgs() {
    String jvmArgs =
        config.getProperty("fr.fallenvaders.minecraft.test-server.server.command.args.jvm");
    if (debugMode) {
      jvmArgs +=
          " "
              + config.getProperty(
                  "fr.fallenvaders.minecraft.test-server.server.command.args.jvm.debug");
    }
    return List.of(jvmArgs.split(" "));
  }

  @NotNull
  private List<String> getMcServerProgramArgs() {
    String programArgs =
        config.getProperty("fr.fallenvaders.minecraft.test-server.server.command.args.program");
    return List.of(programArgs.split(" "));
  }

  @NotNull
  private Path getMcServerLocation() {
    return Path.of(config.getProperty("fr.fallenvaders.minecraft.test-server.server.location"));
  }

  @NotNull
  private String getMcServerJarName() {
    return config.getProperty("fr.fallenvaders.minecraft.test-server.server.jar_name");
  }

  @NotNull
  private Path getFvPluginProjectLocation() {
    return Path.of(
        config.getProperty("fr.fallenvaders.minecraft.test-server.plugin.project.location"));
  }

  @NotNull
  private String getFvPluginJarCoreName() {
    return config.getProperty("fr.fallenvaders.minecraft.test-server.plugin.jar.core_name");
  }

  @NotNull
  private String getFvPluginJarComplementName() {
    return config.getProperty("fr.fallenvaders.minecraft.test-server.plugin.jar.complement_name");
  }

  @NotNull
  private String getFvPluginBuildGoals() {
    return config.getProperty("fr.fallenvaders.minecraft.test-server.plugin.project.build.goals");
  }

  @NotNull
  private Path getFvPluginArtifactLocation(@NotNull Path pluginProjectLocation) {
    String mavenArtifactLocation =
        config.getProperty(
            "fr.fallenvaders.minecraft.test-server.plugin.project.build.artifact.location");
    return pluginProjectLocation.resolve(mavenArtifactLocation);
  }
}
