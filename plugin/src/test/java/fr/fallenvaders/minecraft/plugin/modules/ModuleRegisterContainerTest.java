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
 * Test class of {@link ModuleRegisterContainer}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
class ModuleRegisterContainerTest {

  @Mock private JavaPlugin javaPlugin;
  @Inject private ModuleDeclarerUtils moduleDeclarerUtils;
  private ModuleRegisterContainer moduleRegisterContainer;

  @BeforeEach
  void setUp() {
    TestInjector.inject(javaPlugin, this);
    moduleRegisterContainer = new ModuleRegisterContainer();
  }

  @Test
  @DisplayName("Init the module register")
  void isModuleRegisterWellInit() {
    Assertions.assertFalse(moduleRegisterContainer.isHasLaunched());
    Assertions.assertTrue(moduleRegisterContainer.getModules().isEmpty());
  }

  @Test
  @DisplayName("Add two identical modules")
  void addTwoIdenticalModules() {
    String moduleName = "test-module";
    ModuleDeclarer moduleDeclarer1 =
        moduleDeclarerUtils.createWithoutBehaviorModuleDeclarer(moduleName);
    ModuleDeclarer moduleDeclarer2 =
        moduleDeclarerUtils.createWithoutBehaviorModuleDeclarer(moduleName);
    Assertions.assertDoesNotThrow(() -> moduleRegisterContainer.addModule(moduleDeclarer1));
    Assertions.assertThrows(
        ModuleRegisterException.class, () -> moduleRegisterContainer.addModule(moduleDeclarer1));
    Assertions.assertThrows(
        ModuleRegisterException.class, () -> moduleRegisterContainer.addModule(moduleDeclarer2));
  }
}
