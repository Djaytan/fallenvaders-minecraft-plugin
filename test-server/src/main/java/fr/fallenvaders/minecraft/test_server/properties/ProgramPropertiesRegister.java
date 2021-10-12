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

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * Register class of {@link ProgramProperties}.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class ProgramPropertiesRegister {

  private final ProgramPropertiesFactory programPropertiesFactory;

  private ProgramProperties programProperties;

  /**
   * Constructor.
   *
   * @param programPropertiesFactory The {@link ProgramProperties}'s factory.
   */
  @Inject
  public ProgramPropertiesRegister(@NotNull ProgramPropertiesFactory programPropertiesFactory) {
    this.programPropertiesFactory = programPropertiesFactory;
  }

  /**
   * Returns the stored {@link ProgramProperties} if exits, otherwise create it before store him is
   * this singleton instance.
   *
   * @return The {@link ProgramProperties} of the application.
   */
  public ProgramProperties getProgramProperties() {
    if (programProperties == null) {
      programProperties = programPropertiesFactory.createProgramProperties();
    }
    return programProperties;
  }
}
