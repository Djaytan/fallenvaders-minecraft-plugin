/*
 *  This file is part of the FallenVaders distribution (https://github.com/FallenVaders).
 *  Copyright © 2021 Loïc DUBOIS-TERMOZ.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package fr.fallenvaders.minecraft.justicehands;

import fr.fallenvaders.minecraft.justicehands.view.commands.CriminalRecordCommand;
import fr.fallenvaders.minecraft.justicehands.view.commands.SanctionManagerCommand;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * This class is in charge of initializing all commands of the module.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class CommandsInitializer {

  private final JavaPlugin plugin;
  private final Logger logger;

  private final CriminalRecordCommand criminalRecordCommand;
  private final SanctionManagerCommand sanctionManagerCommand;

  /**
   * Constructor.
   *
   * @param plugin The Bukkit Java plugin.
   * @param logger The logger.
   * @param criminalRecordCommand The criminal record {@link CommandExecutor}.
   * @param sanctionManagerCommand The sanction manager {@link CommandExecutor}.
   */
  @Inject
  public CommandsInitializer(
      @NotNull JavaPlugin plugin,
      @NotNull Logger logger,
      @NotNull CriminalRecordCommand criminalRecordCommand,
      @NotNull SanctionManagerCommand sanctionManagerCommand) {
    this.plugin = plugin;
    this.logger = logger;
    this.criminalRecordCommand = criminalRecordCommand;
    this.sanctionManagerCommand = sanctionManagerCommand;
  }

  /**
   * Initializes command of the module.
   *
   * <p>Note: if a command fail to be initialized, the others should be able to be initialized
   * anyway.
   */
  public void initialize() {
    initializeCommand("cr", criminalRecordCommand);
    initializeCommand("sm", sanctionManagerCommand);
  }

  private void initializeCommand(
      @NotNull String commandName, @NotNull CommandExecutor commandExecutor) {
    PluginCommand command = plugin.getCommand(commandName);
    if (command != null) {
      command.setExecutor(commandExecutor);
    } else {
      logger.error(
          "The command '{}' can't be initialized: it must be registered in the PluginDescriptionFile previously.",
          commandName);
    }
  }
}
