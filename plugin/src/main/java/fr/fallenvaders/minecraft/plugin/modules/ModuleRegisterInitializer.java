package fr.fallenvaders.minecraft.plugin.modules;

import javax.inject.Singleton;

@Singleton
public interface ModuleRegisterInitializer {

  void initialize() throws ModuleRegisterException;
}
