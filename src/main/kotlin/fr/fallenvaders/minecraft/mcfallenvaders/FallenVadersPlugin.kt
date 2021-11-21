package fr.fallenvaders.minecraft.mcfallenvaders

import org.slf4j.Logger
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameStartedServerEvent
import org.spongepowered.api.plugin.Plugin
import javax.inject.Inject

@Plugin(
  id = "fallenvaders",
  name = "FallenVaders",
  version = "0.4.0-SNAPSHOT",
  description = "FallenVaders' Minecraft plugin"
)
class FallenVadersPlugin() {

  @Inject
  lateinit var logger: Logger;

  @Listener
  fun onServerStart(even: GameStartedServerEvent) {
    logger.info("Hello world!")
  }
}
