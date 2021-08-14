package fr.fallenvaders.minecraft.plugin.modules;

public abstract class ModuleRegister {

    private final String moduleName;

    public ModuleRegister(String moduleName) {
        this.moduleName = moduleName;
    }

    public void onEnable() {
        System.out.println("Enabling of the module " + moduleName);
    }

    public void onDisable() {
        System.out.println("Disabling of the module " + moduleName);
    }

    public final String getModuleName() {
        return moduleName;
    }
}
