package fr.fallenvaders.minecraft.plugin.modules;

/**
 * This class is in charge to initialize the {@link ModuleRegisterService} singleton instance.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public interface ModuleRegisterInitializer {

  /**
   * Initializes {@link ModuleRegisterService} singleton instance with {@link ModuleDeclarer} instances.
   *
   * @throws ModuleRegisterException if the initialization into the {@link ModuleRegisterService} fail.
   */
  void initialize() throws ModuleRegisterException;
}
