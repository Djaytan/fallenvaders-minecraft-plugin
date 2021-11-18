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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.plugin.guice.TestInjector;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Test class of {@link ModuleService}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ModuleServiceTest {

  private static final Logger logger = (Logger) LoggerFactory.getLogger(ModuleServiceTest.class);

  @Mock private JavaPlugin javaPlugin;
  @Inject private ModuleUtils moduleUtils;
  private ModuleContainer moduleContainer;
  private ModuleService moduleService;
  private int stack;

  @BeforeEach
  void setUp() {
    TestInjector.inject(javaPlugin, this);
    logger.setLevel(Level.WARN);
    moduleContainer = new ModuleContainer();
    moduleService = new ModuleService(logger, moduleContainer);
    stack = 0;
  }

  /** Test cases: {@link ModuleService#registerModule(FvModule)} */
  @Nested
  class register_module {

    @Test
    void register_one_module_when_no_other_one_is_registered_shall_work() {
      FvModule fvModule = moduleUtils.createWithoutBehaviorModule("test-module");
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      FvModule actualFvModule = moduleContainer.getModule("test-module").orElse(null);
      Assertions.assertEquals(1, moduleContainer.getModules().size());
      Assertions.assertSame(fvModule, actualFvModule);
    }

    @Test
    void register_module_after_loading_shall_not_work() {
      FvModule fvModule1 = moduleUtils.createWithoutBehaviorModule("test-module-1");
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule1));
      moduleService.loadModules();
      Assertions.assertSame(PluginModulesState.LOADED, moduleContainer.getState());
      FvModule fvModule2 = moduleUtils.createWithoutBehaviorModule("test-module-2");
      Assertions.assertThrows(ModuleException.class, () -> moduleService.registerModule(fvModule2));
    }

    @Test
    void register_two_identical_modules_shall_not_work() {
      FvModule fvModule1 = moduleUtils.createWithoutBehaviorModule("test-module");
      FvModule fvModule2 = moduleUtils.createWithoutBehaviorModule("test-module");
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule1));
      Assertions.assertThrows(ModuleException.class, () -> moduleService.registerModule(fvModule1));
      Assertions.assertThrows(ModuleException.class, () -> moduleService.registerModule(fvModule2));
    }
  }

  /** Test cases: {@link ModuleService#loadModules()} */
  @Nested
  class load_modules {

    @Test
    void load_modules_before_loading_shall_work() {
      Runnable onLoad = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", onLoad, null, null);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.loadModules();
      Assertions.assertEquals(1, stack);
      Assertions.assertSame(PluginModulesState.LOADED, moduleContainer.getState());
    }

    @Test
    void load_modules_after_loading_shall_not_work() {
      Runnable onLoad = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", onLoad, null, null);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.loadModules();
      moduleService.loadModules();
      Assertions.assertEquals(1, stack);
      Assertions.assertSame(PluginModulesState.LOADED, moduleContainer.getState());
    }
  }

  /** Test cases: {@link ModuleService#enableModules()} */
  @Nested
  class enable_modules {

    @Test
    void enable_modules_after_loading_shall_work() {
      Runnable onEnable = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", null, onEnable, null);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.loadModules();
      moduleService.enableModules();
      Assertions.assertEquals(1, stack);
      Assertions.assertSame(PluginModulesState.ENABLED, moduleContainer.getState());
    }

    @Test
    void enable_modules_before_loading_shall_not_work() {
      Runnable onEnable = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", null, onEnable, null);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.enableModules();
      Assertions.assertEquals(0, stack);
      Assertions.assertNull(moduleContainer.getState());
    }

    @Test
    void enable_modules_after_enabling_shall_not_work() {
      Runnable onEnable = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", null, onEnable, null);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.enableModules();
      moduleService.enableModules();
      Assertions.assertEquals(1, stack);
      Assertions.assertSame(PluginModulesState.ENABLED, moduleContainer.getState());
    }
  }

  /** Test cases: {@link ModuleService#disableModules()} */
  @Nested
  class disable_modules {

    @Test
    void disable_modules_after_enabling_shall_work() {
      Runnable onDisable = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", null, null, onDisable);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.loadModules();
      moduleService.enableModules();
      moduleService.disableModules();
      Assertions.assertEquals(1, stack);
      Assertions.assertSame(PluginModulesState.DISABLED, moduleContainer.getState());
    }

    @Test
    void disable_modules_before_enabling_shall_not_work() {
      Runnable onDisable = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", null, null, onDisable);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.loadModules();
      moduleService.disableModules();
      Assertions.assertEquals(0, stack);
      Assertions.assertSame(PluginModulesState.LOADED, moduleContainer.getState());
    }

    @Test
    void disable_modules_after_disabling_shall_not_work() {
      Runnable onDisable = () -> stack++;
      FvModule fvModule = moduleUtils.createModule("test-module", null, null, onDisable);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.loadModules();
      moduleService.enableModules();
      moduleService.disableModules();
      moduleService.disableModules();
      Assertions.assertEquals(1, stack);
      Assertions.assertSame(PluginModulesState.DISABLED, moduleContainer.getState());
    }
  }

  /** Integration test cases */
  @Nested
  class integration_test_cases {

    @Test
    void load_enable_and_disable_one_module() {
      Runnable stackIncrement = () -> stack++;
      FvModule fvModule =
          moduleUtils.createModule("test-module", stackIncrement, stackIncrement, stackIncrement);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
      moduleService.loadModules();
      moduleService.enableModules();
      moduleService.disableModules();
      Assertions.assertEquals(3, stack);
      Assertions.assertSame(PluginModulesState.DISABLED, moduleContainer.getState());
    }

    @Test
    void load_enable_and_disable_several_module() {
      Runnable stackIncrement = () -> stack++;
      FvModule fvModule1 =
          moduleUtils.createModule("test-module-1", stackIncrement, stackIncrement, stackIncrement);
      FvModule fvModule2 =
          moduleUtils.createModule("test-module-2", stackIncrement, stackIncrement, stackIncrement);
      FvModule fvModule3 =
          moduleUtils.createModule("test-module-3", stackIncrement, stackIncrement, stackIncrement);
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule1));
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule2));
      Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule3));
      moduleService.loadModules();
      moduleService.enableModules();
      moduleService.disableModules();
      Assertions.assertEquals(9, stack);
      Assertions.assertSame(PluginModulesState.DISABLED, moduleContainer.getState());
    }
  }
}
