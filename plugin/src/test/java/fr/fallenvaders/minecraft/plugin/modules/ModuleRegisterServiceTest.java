package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the test class of the {@link ModuleRegisterService} class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
class ModuleRegisterServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(ModuleRegisterServiceTest.class);

  private ModuleRegisterContainer moduleRegisterContainer;
  private ModuleRegisterService moduleRegister;
  private int enableStack;
  private int disableStack;

  @BeforeEach
  void setUp() {
    moduleRegisterContainer = new ModuleRegisterContainer();
    moduleRegister = new ModuleRegisterService(logger, moduleRegisterContainer);
    enableStack = 0;
    disableStack = 0;
  }

  @Test
  @DisplayName("Init the module register")
  void isModuleRegisterWellInit() {
    Assertions.assertFalse(moduleRegisterContainer.isHasLaunched());
    Assertions.assertTrue(moduleRegisterContainer.getModules().isEmpty());
  }

  @Test
  @DisplayName("Register a module")
  void registerModule() {
    String moduleName = "test-module";
    ModuleDeclarer moduleDeclarer = createWithoutBehaviorModuleDeclarer(moduleName);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    ModuleDeclarer actualModuleDeclarer = moduleRegisterContainer.getModule(moduleName);
    Assertions.assertEquals(1, moduleRegisterContainer.getModules().size());
    Assertions.assertEquals(moduleDeclarer, actualModuleDeclarer);
  }

  @Test
  @DisplayName("Enable one module")
  void enableOneModule() {
    Assertions.assertEquals(0, enableStack);
    String moduleName = "test-module";
    Runnable onEnable = () -> enableStack++;
    ModuleDeclarer moduleDeclarer = createModuleDeclarer(moduleName, onEnable, null);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    moduleRegister.enableModules();
    Assertions.assertEquals(1, enableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Disable one module")
  void disableOneModule() {
    Assertions.assertEquals(0, disableStack);
    String moduleName = "test-module";
    Runnable onDisable = () -> disableStack++;
    ModuleDeclarer moduleDeclarer = createModuleDeclarer(moduleName, null, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    moduleRegister.enableModules();
    moduleRegister.disableModules();
    Assertions.assertEquals(1, disableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Enable then disable one module")
  void enableThenDisableOneModule() {
    Assertions.assertEquals(0, enableStack);
    Assertions.assertEquals(0, disableStack);
    String moduleName = "test-module";
    Runnable onEnable = () -> enableStack++;
    Runnable onDisable = () -> disableStack++;
    ModuleDeclarer moduleDeclarer = createModuleDeclarer(moduleName, onEnable, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    moduleRegister.enableModules();
    moduleRegister.disableModules();
    Assertions.assertEquals(1, enableStack);
    Assertions.assertEquals(1, disableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Enable then disable several modules")
  void enableThenDisableSeveralModules() {
    Assertions.assertEquals(0, enableStack);
    Assertions.assertEquals(0, disableStack);
    String moduleName = "test-module";
    Runnable onEnable = () -> enableStack++;
    Runnable onDisable = () -> disableStack++;
    ModuleDeclarer moduleDeclarer1 = createModuleDeclarer(moduleName, onEnable, onDisable);
    ModuleDeclarer moduleDeclarer2 = createModuleDeclarer(moduleName, onEnable, onDisable);
    ModuleDeclarer moduleDeclarer3 = createModuleDeclarer(moduleName, onEnable, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer1));
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer2));
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer3));
    moduleRegister.enableModules();
    moduleRegister.disableModules();
    Assertions.assertEquals(3, enableStack);
    Assertions.assertEquals(3, disableStack);
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
  }

  @Test
  @DisplayName("Register module after launch throw exception")
  void registerModuleAfterLaunchThrowException() {
    String moduleName = "test-module";
    ModuleDeclarer moduleDeclarer1 = createWithoutBehaviorModuleDeclarer(moduleName);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer1));
    moduleRegister.enableModules();
    Assertions.assertTrue(moduleRegisterContainer.isHasLaunched());
    ModuleDeclarer moduleDeclarer2 = createWithoutBehaviorModuleDeclarer(moduleName);
    Assertions.assertThrows(
        ModuleRegisterException.class, () -> moduleRegister.registerModule(moduleDeclarer2));
  }

  private ModuleDeclarer createWithoutBehaviorModuleDeclarer(@NotNull String moduleName) {
    return createModuleDeclarer(moduleName, null, null);
  }

  private ModuleDeclarer createModuleDeclarer(
      @NotNull String moduleName, @Nullable Runnable onEnable, @Nullable Runnable onDisable) {
    ModuleDeclarer moduleDeclarer;
    // TODO: make it better when Guice will be setup by refactoring the source main code
    try (MockedStatic<JavaPlugin> javaPlugin = Mockito.mockStatic(JavaPlugin.class)) {
      javaPlugin.when(() -> JavaPlugin.getPlugin(FallenVadersPlugin.class)).thenReturn(null);
      moduleDeclarer =
          new ModuleDeclarer(moduleName) {
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
    }
    return moduleDeclarer;
  }
}
