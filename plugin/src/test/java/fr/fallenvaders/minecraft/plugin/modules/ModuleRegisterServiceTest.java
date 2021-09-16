package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.guice.TestInjector;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;

/**
 * This is the test class of the {@link ModuleRegisterService} class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
class ModuleRegisterServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(ModuleRegisterServiceTest.class);

  @Mock private JavaPlugin javaPlugin;
  @Inject private ModuleDeclarerFactory moduleDeclarerFactory;
  private ModuleRegisterContainer moduleRegisterContainer;
  private ModuleRegisterService moduleRegisterService;
  private int enableStack;
  private int disableStack;

  @BeforeEach
  void setUp() {
    TestInjector.inject(javaPlugin, this);
    moduleRegisterContainer = new ModuleRegisterContainer();
    moduleRegisterService =
        new ModuleRegisterService(logger, moduleDeclarerFactory, moduleRegisterContainer);
    enableStack = 0;
    disableStack = 0;
  }

  @Test
  @DisplayName("Register a module")
  void registerModule() {
    ModuleDeclarer moduleDeclarer = createWithoutBehaviorModuleDeclarer("test-module");
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
    ModuleDeclarer moduleDeclarer = createModuleDeclarer("test-module", onEnable, null);
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
    ModuleDeclarer moduleDeclarer = createModuleDeclarer("test-module", null, onDisable);
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
    ModuleDeclarer moduleDeclarer = createModuleDeclarer("test-module", onEnable, onDisable);
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
    ModuleDeclarer moduleDeclarer1 = createModuleDeclarer("test-module-1", onEnable, onDisable);
    ModuleDeclarer moduleDeclarer2 = createModuleDeclarer("test-module-2", onEnable, onDisable);
    ModuleDeclarer moduleDeclarer3 = createModuleDeclarer("test-module-3", onEnable, onDisable);
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
    ModuleDeclarer moduleDeclarer1 = createWithoutBehaviorModuleDeclarer("test-module-1");
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer1));
    moduleRegisterService.enableModules();
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
    ModuleDeclarer moduleDeclarer2 = createWithoutBehaviorModuleDeclarer("test-module-2");
    Assertions.assertThrows(
        ModuleRegisterException.class, () -> moduleRegisterService.registerModule(moduleDeclarer2));
  }

  private ModuleDeclarer createWithoutBehaviorModuleDeclarer(@NotNull String moduleName) {
    return createModuleDeclarer(moduleName, null, null);
  }

  private ModuleDeclarer createModuleDeclarer(
      @NotNull String moduleName, @Nullable Runnable onEnable, @Nullable Runnable onDisable) {
    Objects.requireNonNull(moduleName);

    ModuleDeclarer moduleDeclarer;
    moduleDeclarer =
        new ModuleDeclarer(javaPlugin, moduleName) {
          @Override
          public void onEnable() {
            if (onEnable != null) {
              onEnable.run();
            }
          }

          @Override
          public void onDisable() {
            if (onDisable != null) {
              onDisable.run();
            }
          }
        };
    return moduleDeclarer;
  }
}
