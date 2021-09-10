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

@ExtendWith(MockitoExtension.class)
class ModuleRegisterTest {

  private ModuleRegister moduleRegister;
  private int enableStack;
  private int disableStack;

  @BeforeEach
  void setUp() {
    moduleRegister = new ModuleRegister();
    enableStack = 0;
    disableStack = 0;
  }

  @Test
  @DisplayName("Init the module register")
  void isModuleRegisterWellInit() {
    Assertions.assertFalse(moduleRegister.hasLaunched());
    Assertions.assertTrue(moduleRegister.getModules().isEmpty());
  }

  @Test
  @DisplayName("Register a module")
  void registerModule() {
    String moduleName = "test-module";
    ModuleDeclarer moduleDeclarer = createWithoutBehaviorModuleDeclarer(moduleName);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    Assertions.assertEquals(1, moduleRegister.getModules().size());
    Assertions.assertEquals(moduleName, moduleRegister.getModules().get(0).getModuleName());
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
    Assertions.assertTrue(moduleRegister.hasLaunched());
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
    Assertions.assertTrue(moduleRegister.hasLaunched());
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
    Assertions.assertTrue(moduleRegister.hasLaunched());
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
    Assertions.assertTrue(moduleRegister.hasLaunched());
  }

  private ModuleDeclarer createWithoutBehaviorModuleDeclarer(@NotNull String moduleName) {
    return createModuleDeclarer(moduleName, null, null);
  }

  private ModuleDeclarer createModuleDeclarer(
      @NotNull String moduleName,
      @Nullable Runnable onEnable,
      @Nullable Runnable onDisable) {
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
