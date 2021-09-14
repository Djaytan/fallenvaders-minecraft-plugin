package fr.fallenvaders.minecraft.plugin.modules;

import com.google.inject.ImplementedBy;

import javax.inject.Singleton;

@Singleton
@ImplementedBy(CompleteModuleRegisterInitializer.class)
public interface ModuleRegisterInitializer {
  
  ModuleRegister initialize() throws ModuleRegisterException;
}
