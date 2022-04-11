package fr.fallenvaders.minecraft.plugin;

import javax.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("fallenvaders-plugin")
public class FallenVadersPlugin {

  @Inject
  private Logger logger;

  @Listener
  public void onServerStart(final StartedEngineEvent<Server> event) {
    logger.info("Hello World!");
  }
}
