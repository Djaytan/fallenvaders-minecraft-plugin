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

package fr.fallenvaders.minecraft.test_server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.fallenvaders.minecraft.test_server.PropertiesUtils;
import fr.fallenvaders.minecraft.test_server.deploy.FVPluginJarNameAssembler;
import fr.fallenvaders.minecraft.test_server.deploy.MavenJarNameAssembler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Properties;

/**
 * Guice module for test server program.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public final class TestServerModule extends AbstractModule {

  private static final String CONFIG_FILE = "config.properties";

  private final boolean debugMode;

  /**
   * Constructor.
   *
   * @param debugMode Tells if the program is in debug mode.
   */
  public TestServerModule(boolean debugMode) {
    this.debugMode = debugMode;
  }

  @Override
  public void configure() {
    bind(FVPluginJarNameAssembler.class).to(MavenJarNameAssembler.class);
  }

  @Provides
  @DebugMode
  public boolean provideDebugMode() {
    return debugMode;
  }

  @Provides
  @ConfigProperties
  @NotNull
  public Properties provideConfigProperties() throws IOException {
    return PropertiesUtils.getProperties(CONFIG_FILE);
  }
}
