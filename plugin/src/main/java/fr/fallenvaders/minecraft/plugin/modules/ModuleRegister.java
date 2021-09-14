package fr.fallenvaders.minecraft.plugin.modules;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// TODO: rename to ModuleRegisterService & create ModuleRegisterContainer
@Singleton
public class ModuleRegister {

  private final Logger logger;

  private final List<ModuleDeclarer> modules = new ArrayList<>();
  private boolean hasLaunched = false;

  @Inject
  public ModuleRegister(Logger logger) {
    this.logger = logger;
  }

  public void registerModule(ModuleDeclarer moduleDeclarer) throws ModuleRegisterException {
    if (hasLaunched) {
      throw new ModuleRegisterException(
          "Module registration rejected: the module registration process has already been launched.");
    }
    modules.add(moduleDeclarer);
  }

  public void enableModules() {
    modules.forEach(
        (module) -> {
          module.onEnable();
          hasLaunched = true;
          logger.info("Module \"" + module.getModuleName() + "\" enabled.");
        });
  }

  public void disableModules() {
    modules.forEach(
        (module) -> {
          module.onDisable();
          logger.info("Module \"" + module.getModuleName() + "\" disabled.");
        });
  }

  public boolean hasLaunched() {
    return hasLaunched;
  }

  public List<ModuleDeclarer> getModules() {
    // TODO: return clone version of the list
    return modules;
  }
}
