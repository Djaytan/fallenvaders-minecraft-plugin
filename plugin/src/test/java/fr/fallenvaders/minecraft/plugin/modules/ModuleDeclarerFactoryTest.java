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

import fr.fallenvaders.minecraft.plugin.modules.declarers.MalformedTestModuleDeclarer;
import fr.fallenvaders.minecraft.plugin.modules.declarers.TestModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class of {@link ModuleDeclarerFactory}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
class ModuleDeclarerFactoryTest {

  @Mock private JavaPlugin javaPlugin;
  private ModuleDeclarerFactory moduleDeclarerFactory;

  @BeforeEach
  void setUp() {
    moduleDeclarerFactory = new ModuleDeclarerFactory(javaPlugin);
  }

  @Test
  @DisplayName("Create the test module does not throw")
  void createTestModuleNotThrow() {
    Assertions.assertDoesNotThrow(
        () -> moduleDeclarerFactory.createModule(TestModuleDeclarer.class));
  }

  @Test
  @DisplayName("Create the test module")
  void createTestModule() throws ModuleRegisterException {
    ModuleDeclarer moduleDeclarer = moduleDeclarerFactory.createModule(TestModuleDeclarer.class);
    Assertions.assertNotNull(moduleDeclarer);
    Assertions.assertEquals(TestModuleDeclarer.MODULE_NAME, moduleDeclarer.getModuleName());
    Assertions.assertSame(moduleDeclarer.getClass(), TestModuleDeclarer.class);
  }

  @Test
  @DisplayName("Create a malformed module must throw")
  void createMalformedModuleMustThrow() {
    Assertions.assertThrows(
        ModuleRegisterException.class,
        () -> moduleDeclarerFactory.createModule(MalformedTestModuleDeclarer.class));
  }
}
