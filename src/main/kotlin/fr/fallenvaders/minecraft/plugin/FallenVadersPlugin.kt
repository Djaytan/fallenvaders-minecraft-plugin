package fr.fallenvaders.minecraft.plugin

import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FallenVadersPlugin() : JavaPlugin() {

  @Autowired
  lateinit var logger: Logger

  @Bean
  fun provideLogger(): Logger {
    return this.slF4JLogger
  }

  override fun onEnable() {
    SpringApplication.run(FallenVadersPlugin::class.java)
    logger.info("Hello world!")
  }
}
