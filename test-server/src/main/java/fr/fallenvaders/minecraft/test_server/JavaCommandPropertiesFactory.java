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

import fr.fallenvaders.minecraft.test_server.guice.ConfigProperties;
import fr.fallenvaders.minecraft.test_server.guice.DebugMode;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Factory of {@link JavaCommandProperties}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public class JavaCommandPropertiesFactory {

  /** This arg tells Bukkit to not show GUI on server execution. */
  private static final String BUKKIT_NO_GUI_ARG = "nogui";

  private final boolean debugMode;
  private final Properties config;

  /**
   * Constructor.
   *
   * @param debugMode Tells if the program is in debug mode.
   * @param config The config properties of the test server.
   */
  @Inject
  public JavaCommandPropertiesFactory(
      @DebugMode boolean debugMode, @NotNull @ConfigProperties Properties config) {
    Objects.requireNonNull(config);
    this.debugMode = debugMode;
    this.config = config;
  }

  /**
   * Creates a {@link JavaCommandProperties} with the config properties.
   *
   * @return The created {@link JavaCommandProperties} instance.
   */
  @NotNull
  public JavaCommandProperties createProgramProperties() {
    String jarName = config.getProperty("fr.fallenvaders.server.jar.name");
    List<String> jvmArgs = getJvmArgs();
    List<String> programArgs = getProgramArgs();
    return new JavaCommandProperties(jarName, jvmArgs, programArgs);
  }

  @NotNull
  private List<String> getJvmArgs() {
    String jvmArgs;
    if (!debugMode) {
      jvmArgs = config.getProperty("fr.fallenvaders.server.jvm.args");
    } else {
      jvmArgs = config.getProperty("fr.fallenvaders.server.jvm.args.debug");
    }
    return List.of(jvmArgs.split(" "));
  }

  @NotNull
  private List<String> getProgramArgs() {
    List<String> programArgs;
    boolean guiActive =
        Boolean.parseBoolean(config.getProperty("fr.fallenvaders.server.gui.active"));
    if (!guiActive) {
      programArgs = List.of(BUKKIT_NO_GUI_ARG);
    } else {
      programArgs = List.of();
    }
    return programArgs;
  }
}
