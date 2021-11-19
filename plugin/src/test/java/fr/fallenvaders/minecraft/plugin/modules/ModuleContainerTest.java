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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

/**
 * Test class of {@link ModuleContainer}.
 *
 * <p>The following trivial methods are no tested: {@link ModuleContainer#getModules()} (a pure
 * getter), {@link ModuleContainer#getState()} (a pure getter)
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ModuleContainerTest {

  @Mock private JavaPlugin javaPlugin;
  @Inject private ModuleUtils moduleUtils;
  private ModuleContainer moduleContainer;

  @BeforeEach
  void setUp() {
    TestInjector.inject(javaPlugin, this);
    moduleContainer = new ModuleContainer();
  }

  /** Global test cases * */
  @Test
  void initialization() {
    Assertions.assertSame(PluginModulesState.UNLOADED, moduleContainer.getState());
    Assertions.assertTrue(moduleContainer.getModules().isEmpty());
  }

  /** Test cases: {@link ModuleContainer#addModule(FvModule)} */
  @Nested
  class add_module {

    @Test
    void add_a_module_in_empty_container_shall_work() {
      FvModule module = moduleUtils.createWithoutBehaviorModule("test-module");
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(module));
    }

    @Test
    void add_a_module_when_there_is_a_state_shall_not_work() {
      FvModule module = moduleUtils.createWithoutBehaviorModule("test-module");
      Assertions.assertDoesNotThrow(() -> moduleContainer.setState(PluginModulesState.LOADED));
      Assertions.assertThrows(ModuleException.class, () -> moduleContainer.addModule(module));
    }

    @Test
    void add_two_identical_modules_shall_not_work() {
      String moduleName = "test-module";
      FvModule fvModule1 = moduleUtils.createWithoutBehaviorModule(moduleName);
      FvModule fvModule2 = moduleUtils.createWithoutBehaviorModule(moduleName);
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(fvModule1));
      Assertions.assertThrows(ModuleException.class, () -> moduleContainer.addModule(fvModule1));
      Assertions.assertThrows(ModuleException.class, () -> moduleContainer.addModule(fvModule2));
    }

    @Test
    void add_several_modules_shall_work() {
      FvModule module1 = moduleUtils.createWithoutBehaviorModule("test-module-1");
      FvModule module2 = moduleUtils.createWithoutBehaviorModule("test-module-2");
      FvModule module3 = moduleUtils.createWithoutBehaviorModule("test-module-3");
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(module1));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(module2));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(module3));
    }
  }

  /** Test cases: {@link ModuleContainer#getModule(String)} * */
  @Nested
  class get_module {

    @Test
    void get_module_when_no_ones_are_registered_shall_be_null() {
      Assertions.assertNull(moduleContainer.getModule("test-module").orElse(null));
    }

    @Test
    void get_module_when_only_one_is_registered_shall_work() {
      String moduleName = "test-module";
      FvModule module = moduleUtils.createWithoutBehaviorModule(moduleName);
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(module));
      Assertions.assertSame(module, moduleContainer.getModule(moduleName).orElse(null));
    }

    @Test
    void get_middle_module_when_several_ones_are_registered_shall_found_the_correct_one() {
      String targetedModuleName = "targeted-test-module";
      FvModule targetedModule = moduleUtils.createWithoutBehaviorModule(targetedModuleName);
      FvModule otherModule1 = moduleUtils.createWithoutBehaviorModule("other-test-module-1");
      FvModule otherModule2 = moduleUtils.createWithoutBehaviorModule("other-test-module-2");
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(otherModule1));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(targetedModule));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(otherModule2));
      Assertions.assertSame(
          targetedModule, moduleContainer.getModule(targetedModuleName).orElse(null));
    }

    @Test
    void get_first_module_when_several_ones_are_registered_shall_found_the_correct_one() {
      String targetedModuleName = "targeted-test-module";
      FvModule targetedModule = moduleUtils.createWithoutBehaviorModule(targetedModuleName);
      FvModule otherModule1 = moduleUtils.createWithoutBehaviorModule("other-test-module-1");
      FvModule otherModule2 = moduleUtils.createWithoutBehaviorModule("other-test-module-2");
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(targetedModule));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(otherModule1));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(otherModule2));
      Assertions.assertSame(
          targetedModule, moduleContainer.getModule(targetedModuleName).orElse(null));
    }

    @Test
    void get_last_module_when_several_ones_are_registered_shall_found_the_correct_one() {
      String targetedModuleName = "targeted-test-module";
      FvModule targetedModule = moduleUtils.createWithoutBehaviorModule(targetedModuleName);
      FvModule otherModule1 = moduleUtils.createWithoutBehaviorModule("other-test-module-1");
      FvModule otherModule2 = moduleUtils.createWithoutBehaviorModule("other-test-module-2");
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(otherModule1));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(otherModule2));
      Assertions.assertDoesNotThrow(() -> moduleContainer.addModule(targetedModule));
      Assertions.assertSame(
          targetedModule, moduleContainer.getModule(targetedModuleName).orElse(null));
    }
  }

  /** Test cases: {@link ModuleContainer#setState(PluginModulesState)} */
  @Nested
  class set_state {

    @Test
    void set_loaded_state_when_no_state_shall_work() {
      Assertions.assertDoesNotThrow(() -> moduleContainer.setState(PluginModulesState.LOADED));
    }

    @Test
    void set_loaded_state_after_higher_state_shall_not_work() {
      Assertions.assertDoesNotThrow(() -> moduleContainer.setState(PluginModulesState.ENABLED));
      Assertions.assertThrows(
          ModuleException.class, () -> moduleContainer.setState(PluginModulesState.LOADED));
      Assertions.assertDoesNotThrow(() -> moduleContainer.setState(PluginModulesState.DISABLED));
      Assertions.assertThrows(
          ModuleException.class, () -> moduleContainer.setState(PluginModulesState.LOADED));
      Assertions.assertThrows(
          ModuleException.class, () -> moduleContainer.setState(PluginModulesState.ENABLED));
    }
  }
}
