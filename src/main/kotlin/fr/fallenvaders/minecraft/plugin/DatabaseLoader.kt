package fr.fallenvaders.minecraft.plugin

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseLoader(
  @Autowired
  val logger: Logger
) {


  @Bean
  fun initDatabase(sanctionRepository: SanctionRepository): CommandLineRunner {
    return CommandLineRunner {
      logger.info("TESTTTTT")
    }
  }
}
