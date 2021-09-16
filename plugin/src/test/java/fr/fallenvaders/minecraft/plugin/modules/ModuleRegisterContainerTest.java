package fr.fallenvaders.minecraft.plugin.modules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ModuleRegisterContainerTest {

  private ModuleRegisterContainer moduleRegisterContainer;

  @BeforeEach
  void setUp() {
    moduleRegisterContainer = new ModuleRegisterContainer();
  }

  @Test
  @DisplayName("Init the module register")
  void isModuleRegisterWellInit() {
    Assertions.assertFalse(moduleRegisterContainer.isHasLaunched());
    Assertions.assertTrue(moduleRegisterContainer.getModules().isEmpty());
  }

  @Test
  @DisplayName("Register two identical modules")
  void registerTwoIdenticalModules() {
    String moduleName = "test-module";
    ModuleDeclarer moduleDeclarer1 = createWithoutBehaviorModuleDeclarer(moduleName);
    ModuleDeclarer moduleDeclarer2 = createWithoutBehaviorModuleDeclarer(moduleName);
    Assertions.assertDoesNotThrow(() -> moduleRegisterService.registerModule(moduleDeclarer1));
    Assertions.assertThrows(
      ModuleRegisterException.class, () -> moduleRegisterService.registerModule(moduleDeclarer1));
    Assertions.assertThrows(
      ModuleRegisterException.class, () -> moduleRegisterService.registerModule(moduleDeclarer2));
  }
}
