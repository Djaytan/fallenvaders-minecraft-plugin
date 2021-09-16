package fr.fallenvaders.minecraft.plugin.modules;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.fallenvaders.minecraft.plugin.guice.TestInjector;
import fr.fallenvaders.minecraft.plugin.modules.declarers.TestModuleDeclarer;
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
 * This is the test class of the {@link ModuleRegisterService} class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
class ModuleRegisterServiceTest {

  private static final Logger logger =
      (Logger) LoggerFactory.getLogger(ModuleRegisterServiceTest.class);

  @Mock private JavaPlugin javaPlugin;
  @Inject private ModuleDeclarerFactory moduleDeclarerFactory;
  @Inject private ModuleDeclarerUtils moduleDeclarerUtils;
  private ModuleRegisterContainer moduleRegisterContainer;
  private ModuleRegisterService moduleRegisterService;
  private int enableStack;
  private int disableStack;

  @BeforeEach
  void setUp() {
    TestInjector.inject(javaPlugin, this);
    logger.setLevel(Level.WARN);
    moduleRegisterContainer = new ModuleRegisterContainer();
    moduleRegisterService =
        new ModuleRegisterService(logger, moduleDeclarerFactory, moduleRegisterContainer);
    enableStack = 0;
    disableStack = 0;
  }

  @Test
  @DisplayName("Register a module")
  void registerModule() {
    ModuleDeclarer moduleDeclarer =
        moduleDeclarerUtils.createWithoutBehaviorModuleDeclarer("test-module");
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer));
    ModuleDeclarer actualModuleDeclarer = moduleRegisterContainer.getModule("test-module");
    Assertions.assertEquals(1, moduleRegisterContainer.getModules().size());
    Assertions.assertEquals(moduleDeclarer, actualModuleDeclarer);
  }

  @Test
  @DisplayName("Enable one module")
  void enableOneModule() {
    Assertions.assertEquals(0, enableStack);
    Runnable onEnable = () -> enableStack++;
    ModuleDeclarer moduleDeclarer =
        moduleDeclarerUtils.createModuleDeclarer("test-module", onEnable, null);
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer));
    moduleRegisterService.enableModules();
    Assertions.assertEquals(1, enableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Disable one module")
  void disableOneModule() {
    Assertions.assertEquals(0, disableStack);
    Runnable onDisable = () -> disableStack++;
    ModuleDeclarer moduleDeclarer =
        moduleDeclarerUtils.createModuleDeclarer("test-module", null, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer));
    moduleRegisterService.enableModules();
    moduleRegisterService.disableModules();
    Assertions.assertEquals(1, disableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Enable then disable one module")
  void enableThenDisableOneModule() {
    Assertions.assertEquals(0, enableStack);
    Assertions.assertEquals(0, disableStack);
    Runnable onEnable = () -> enableStack++;
    Runnable onDisable = () -> disableStack++;
    ModuleDeclarer moduleDeclarer =
        moduleDeclarerUtils.createModuleDeclarer("test-module", onEnable, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer));
    moduleRegisterService.enableModules();
    moduleRegisterService.disableModules();
    Assertions.assertEquals(1, enableStack);
    Assertions.assertEquals(1, disableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Enable then disable several modules")
  void enableThenDisableSeveralModules() {
    Assertions.assertEquals(0, enableStack);
    Assertions.assertEquals(0, disableStack);
    Runnable onEnable = () -> enableStack++;
    Runnable onDisable = () -> disableStack++;
    ModuleDeclarer moduleDeclarer1 =
        moduleDeclarerUtils.createModuleDeclarer("test-module-1", onEnable, onDisable);
    ModuleDeclarer moduleDeclarer2 =
        moduleDeclarerUtils.createModuleDeclarer("test-module-2", onEnable, onDisable);
    ModuleDeclarer moduleDeclarer3 =
        moduleDeclarerUtils.createModuleDeclarer("test-module-3", onEnable, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer1));
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer2));
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer3));
    moduleRegisterService.enableModules();
    moduleRegisterService.disableModules();
    Assertions.assertEquals(3, enableStack);
    Assertions.assertEquals(3, disableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Register module after launch throw exception")
  void registerModuleAfterLaunchThrowException() {
    ModuleDeclarer moduleDeclarer1 =
        moduleDeclarerUtils.createWithoutBehaviorModuleDeclarer("test-module-1");
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer1));
    moduleRegisterService.enableModules();
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
    ModuleDeclarer moduleDeclarer2 =
        moduleDeclarerUtils.createWithoutBehaviorModuleDeclarer("test-module-2");
    Assertions.assertThrows(
        ModuleRegisterException.class, () -> moduleRegisterService.registerModule(moduleDeclarer2));
  }

  @Test
  @DisplayName("Register two identical modules")
  void registerTwoIdenticalModules() {
    Assertions.assertDoesNotThrow(
        () -> moduleRegisterService.registerModule(TestModuleDeclarer.class));
    Assertions.assertThrows(
        ModuleRegisterException.class,
        () -> moduleRegisterService.registerModule(TestModuleDeclarer.class));
  }
}
