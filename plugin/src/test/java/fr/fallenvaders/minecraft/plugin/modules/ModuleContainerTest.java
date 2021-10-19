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

package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.plugin.guice.TestInjector;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

/**
 * Test class of {@link ModuleContainer}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
class ModuleContainerTest {

  @Mock private JavaPlugin javaPlugin;
  @Inject private ModuleUtils moduleUtils;
  private ModuleContainer moduleContainer;

  @BeforeEach
  void setUp() {
    TestInjector.inject(javaPlugin, this);
    moduleContainer = new ModuleContainer();
  }

  @Test
  @DisplayName("Init the module register")
  void isModuleRegisterWellInit() {
    Assertions.assertNull(moduleContainer.getState());
    Assertions.assertTrue(moduleContainer.getModules().isEmpty());
  }

  @Test
  @DisplayName("Add a module")
  void addNullModule() {
    FvModule module = moduleUtils.createWithoutBehaviorModule("test-module");
    Assertions.assertThrows(ModuleException.class, () -> moduleContainer.addModule(module));
  }

  @Test
  @DisplayName("Add two identical modules")
  void addTwoIdenticalModules() {
    String moduleName = "test-module";
    FvModule fvModule1 = moduleUtils.createWithoutBehaviorModule(moduleName);
    FvModule fvModule2 = moduleUtils.createWithoutBehaviorModule(moduleName);
    Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(fvModule1));
    Assertions.assertThrows(ModuleException.class, () -> moduleContainer.addModule(fvModule1));
    Assertions.assertThrows(ModuleException.class, () -> moduleContainer.addModule(fvModule2));
  }
}
