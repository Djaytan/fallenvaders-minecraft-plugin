package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.bukkit.plugin.java.JavaPlugin;
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

  @BeforeEach
  void init() {
    moduleRegister = new ModuleRegister();
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
    ModuleDeclarer moduleDeclarer;
    // TODO: make it better when Guice will be setup by refactoring the source main code
    try (MockedStatic<JavaPlugin> javaPlugin = Mockito.mockStatic(JavaPlugin.class)) {
      javaPlugin.when(() -> JavaPlugin.getPlugin(FallenVadersPlugin.class)).thenReturn(null);
      moduleDeclarer =
        new ModuleDeclarer(moduleName) {
          @Override
          public void onEnable() {
            // nothing to test here
          }

          @Override
          public void onDisable() {
            // nothing to test here
          }
        };
    }
    Assertions.assertDoesNotThrow(() -> moduleRegister.registerModule(moduleDeclarer));
    Assertions.assertEquals(1, moduleRegister.getModules().size());
    Assertions.assertEquals(moduleName, moduleRegister.getModules().get(0).getModuleName());
  }
}
