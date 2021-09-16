package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.modules.declarers.MalformedTestModuleDeclarer;
import fr.fallenvaders.minecraft.plugin.modules.declarers.TestModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class of {@link ModuleDeclarerFactory}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@ExtendWith(MockitoExtension.class)
class ModuleDeclarerFactoryTest {

  @Mock private JavaPlugin javaPlugin;
  private ModuleDeclarerFactory moduleDeclarerFactory;

  @BeforeEach
  void setUp() {
    moduleDeclarerFactory = new ModuleDeclarerFactory(javaPlugin);
  }

  @Test
  @DisplayName("Create the test module does not throw")
  void createTestModuleNotThrow() {
    Assertions.assertDoesNotThrow(
        () -> moduleDeclarerFactory.createModule(TestModuleDeclarer.class));
  }

  @Test
  @DisplayName("Create the test module")
  void createTestModule() throws ModuleRegisterException {
    ModuleDeclarer moduleDeclarer = moduleDeclarerFactory.createModule(TestModuleDeclarer.class);
    Assertions.assertNotNull(moduleDeclarer);
    Assertions.assertEquals(TestModuleDeclarer.MODULE_NAME, moduleDeclarer.getModuleName());
    Assertions.assertSame(moduleDeclarer.getClass(), TestModuleDeclarer.class);
  }

  @Test
  @DisplayName("Create a malformed module must throw")
  void createMalformedModuleMustThrow() {
    Assertions.assertThrows(
        ModuleRegisterException.class,
        () -> moduleDeclarerFactory.createModule(MalformedTestModuleDeclarer.class));
  }
}
