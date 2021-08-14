package fr.fallenvaders.minecraft.plugin.modules;

import java.util.ArrayList;
import java.util.List;

public abstract class ModuleRegister {

    private final List<ModuleDeclarer> modules;
    private boolean hasLaunched;

    public ModuleRegister() {
        modules = new ArrayList<>();
    }

    public void registerModule(ModuleDeclarer moduleDeclarer) throws ModuleRegisterException {
        if (hasLaunched) {
            throw new ModuleRegisterException("Module registration rejected: the module registration process has already been launched.");
        }
        modules.add(moduleDeclarer);
    }

    public void enableModules() {
        modules.forEach((module) -> {
            module.onEnable();
            hasLaunched = true;
            System.out.println("Module \"" + module.getModuleName() + "\" enabled.");
        });
    }

    public void disableModules() {
        modules.forEach((module) -> {
            module.onDisable();
            System.out.println("Module \"" + module.getModuleName() + "\" disabled.");
        });
    }

    public boolean hasLaunched() {
        return hasLaunched;
    }
}
