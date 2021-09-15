package fr.fallenvaders.minecraft.plugin.modules;

/**
 * This class is in charge to initialize the {@link ModuleRegister} singleton instance.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public interface ModuleRegisterInitializer {

  /**
   * Initializes {@link ModuleRegister} singleton instance with {@link ModuleDeclarer} instances.
   *
   * @throws ModuleRegisterException if the initialization into the {@link ModuleRegister} fail.
   */
  void initialize() throws ModuleRegisterException;
}
