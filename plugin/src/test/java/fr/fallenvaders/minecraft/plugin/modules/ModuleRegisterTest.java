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
  private boolean isEnableRan;
  private boolean isDisableRan;

  @BeforeEach
  void setUp() {
    moduleRegister = new ModuleRegister();
    isEnableRan = false;
    isDisableRan = false;
  }

  @Test
  @DisplayName("Init the module register")
  void isModuleRegisterWellInit() {
    Assertions.assertFalse(moduleRegister.hasLaunched());
    Assertions.assertTrue(moduleRegister.getModules().isEmpty());
  }

  @Test
  @DisplayName("Register a module declarer")
  void registerModuleDeclarer() {
    String moduleName = "test-module";
    ModuleDeclarer moduleDeclarer = createWithoutBehaviorModuleDeclarer(moduleName);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    Assertions.assertEquals(1, moduleRegister.getModules().size());
    Assertions.assertEquals(moduleName, moduleRegister.getModules().get(0).getModuleName());
  }

  @Test
  @DisplayName("Enable one module declarer")
  void enableOneModuleDeclarer() {
    Assertions.assertFalse(isEnableRan);
    String moduleName = "test-module";
    Runnable onEnable = () -> isEnableRan = true;
    ModuleDeclarer moduleDeclarer = createModuleDeclarer(moduleName, onEnable, null);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    moduleRegister.enableModules();
    Assertions.assertTrue(isEnableRan);
  }

  @Test
  @DisplayName("Disable one module declarer")
  void disableOneModuleDeclarer() {
    Assertions.assertFalse(isDisableRan);
    String moduleName = "test-module";
    Runnable onDisable = () -> isDisableRan = true;
    ModuleDeclarer moduleDeclarer = createModuleDeclarer(moduleName, null, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    moduleRegister.enableModules();
    moduleRegister.disableModules();
    Assertions.assertTrue(isDisableRan);
  }

  @Test
  @DisplayName("Enable then disable one module declarer")
  void enableThenDisableOneModuleDeclarer() {
    Assertions.assertFalse(isEnableRan);
    Assertions.assertFalse(isDisableRan);
    String moduleName = "test-module";
    Runnable onEnable = () -> isEnableRan = true;
    Runnable onDisable = () -> isDisableRan = true;
    ModuleDeclarer moduleDeclarer = createModuleDeclarer(moduleName, onEnable, onDisable);
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    moduleRegister.enableModules();
    moduleRegister.disableModules();
    Assertions.assertTrue(isEnableRan);
    Assertions.assertTrue(isDisableRan);
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
