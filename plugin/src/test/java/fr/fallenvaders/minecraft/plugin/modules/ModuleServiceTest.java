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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class ModuleServiceTest {

  private static final Logger logger = (Logger) LoggerFactory.getLogger(ModuleServiceTest.class);

  @Mock private JavaPlugin javaPlugin;
  @Inject private ModuleUtils moduleUtils;
  private ModuleContainer moduleContainer;
  private ModuleService moduleService;
  private int enableStack;
  private int disableStack;

  @BeforeEach
  void setUp() {
    TestInjector.inject(javaPlugin, this);
    logger.setLevel(Level.WARN);
    moduleContainer = new ModuleContainer();
    moduleService = new ModuleService(logger, moduleContainer);
    enableStack = 0;
    disableStack = 0;
  }

  @Test
  @DisplayName("Register a module")
  void registerModule() {
    FvModule fvModule = moduleUtils.createWithoutBehaviorModule("test-module");
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
    FvModule actualFvModule = moduleContainer.getModule("test-module");
    Assertions.assertEquals(1, moduleContainer.getModules().size());
    Assertions.assertEquals(fvModule, actualFvModule);
  }

  @Test
  @DisplayName("Enable one module")
  void enableOneModule() {
    Assertions.assertEquals(0, enableStack);
    Runnable onEnable = () -> enableStack++;
    FvModule fvModule = moduleUtils.createModuleDeclarer("test-module", onEnable, null);
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
    moduleService.enableModules();
    Assertions.assertEquals(1, enableStack);
    Assertions.assertTrue(moduleContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Disable one module")
  void disableOneModule() {
    Assertions.assertEquals(0, disableStack);
    Runnable onDisable = () -> disableStack++;
    FvModule fvModule = moduleUtils.createModuleDeclarer("test-module", null, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
    moduleService.enableModules();
    moduleService.disableModules();
    Assertions.assertEquals(1, disableStack);
    Assertions.assertTrue(moduleContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Enable then disable one module")
  void enableThenDisableOneModule() {
    Assertions.assertEquals(0, enableStack);
    Assertions.assertEquals(0, disableStack);
    Runnable onEnable = () -> enableStack++;
    Runnable onDisable = () -> disableStack++;
    FvModule fvModule = moduleUtils.createModuleDeclarer("test-module", onEnable, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule));
    moduleService.enableModules();
    moduleService.disableModules();
    Assertions.assertEquals(1, enableStack);
    Assertions.assertEquals(1, disableStack);
    Assertions.assertTrue(moduleContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Enable then disable several modules")
  void enableThenDisableSeveralModules() {
    Assertions.assertEquals(0, enableStack);
    Assertions.assertEquals(0, disableStack);
    Runnable onEnable = () -> enableStack++;
    Runnable onDisable = () -> disableStack++;
    FvModule fvModule1 = moduleUtils.createModuleDeclarer("test-module-1", onEnable, onDisable);
    FvModule fvModule2 = moduleUtils.createModuleDeclarer("test-module-2", onEnable, onDisable);
    FvModule fvModule3 = moduleUtils.createModuleDeclarer("test-module-3", onEnable, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule1));
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule2));
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule3));
    moduleService.enableModules();
    moduleService.disableModules();
    Assertions.assertEquals(3, enableStack);
    Assertions.assertEquals(3, disableStack);
    Assertions.assertTrue(moduleContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Register module after launch throw exception")
  void registerModuleAfterLaunchThrowException() {
    FvModule fvModule1 = moduleUtils.createWithoutBehaviorModule("test-module-1");
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule1));
    moduleService.enableModules();
    Assertions.assertTrue(moduleContainer.isHasLaunched());
    FvModule fvModule2 = moduleUtils.createWithoutBehaviorModule("test-module-2");
    Assertions.assertThrows(ModuleException.class, () -> moduleService.registerModule(fvModule2));
  }

  @Test
  @DisplayName("Register two identical modules")
  void registerTwoIdenticalModules() {
    FvModule fvModule1 = moduleUtils.createWithoutBehaviorModule("test-module");
    FvModule fvModule2 = moduleUtils.createWithoutBehaviorModule("test-module");
    Assertions.assertDoesNotThrow(() -> moduleService.registerModule(fvModule1));
    Assertions.assertThrows(ModuleException.class, () -> moduleService.registerModule(fvModule2));
  }
}
