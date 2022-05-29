/*
 * Copyright (c) 2022 - Loïc DUBOIS-TERMOZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.fallenvaders.minecraft.core.plugin.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import fr.fallenvaders.minecraft.core.controller.api.MessageController;
import fr.fallenvaders.minecraft.core.controller.implementation.MessageControllerImpl;
import fr.fallenvaders.minecraft.core.controller.api.PlayerController;
import fr.fallenvaders.minecraft.core.controller.implementation.PlayerControllerImpl;
import java.util.Locale;
import java.util.ResourceBundle;
import org.jetbrains.annotations.NotNull;

/** General Guice module. */
public class GuiceGeneralModule extends AbstractModule {

  @Override
  public void configure() {
    bind(MessageController.class).to(MessageControllerImpl.class);
    bind(PlayerController.class).to(PlayerControllerImpl.class);
  }

  @Provides
  @Singleton
  public @NotNull ResourceBundle provideResourceBundle() {
    return ResourceBundle.getBundle("fallenvaders", Locale.FRANCE);
  }
}
