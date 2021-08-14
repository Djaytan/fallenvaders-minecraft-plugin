package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.modules.CompleteModuleRegisterInitializer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegister;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterException;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterInitializer;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            ModuleRegisterInitializer moduleInitializer = new CompleteModuleRegisterInitializer();
            ModuleRegister moduleRegister = moduleInitializer.initialize();
            System.out.println("FallenVaders plugin enabled.");
        } catch (ModuleRegisterException e) {
            e.printStackTrace();
        }
    }
}
