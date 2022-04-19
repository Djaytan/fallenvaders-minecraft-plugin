/*
 * Copyright (c) 2022 - Loïc DUBOIS-TERMOZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.fallenvaders.minecraft.plugin;

import co.aikar.commands.PaperCommandManager;
import fr.fallenvaders.minecraft.plugin.command.HealCommand;
import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
public class CommandRegister {

  private final PaperCommandManager paperCommandManager;
  private final Server server;

  private final HealCommand healCommand;

  @Inject
  public CommandRegister(
      @NotNull PaperCommandManager paperCommandManager,
      @NotNull Server server,
      @NotNull HealCommand healCommand) {
    this.paperCommandManager = paperCommandManager;
    this.server = server;
    this.healCommand = healCommand;
  }

  public void registerCommands() {
    paperCommandManager.registerCommand(healCommand);
  }

  public void registerCommandCompletions() {
    paperCommandManager
        .getCommandCompletions()
        .registerAsyncCompletion(
            "allplayers",
            context -> {
              long size = Long.parseLong(context.getConfig("size", "100"));
              return Arrays.stream(server.getOfflinePlayers())
                  .limit(size)
                  .map(OfflinePlayer::getName)
                  .toList();
            });

    paperCommandManager
        .getCommandCompletions()
        .registerAsyncCompletion(
            "playernames",
            context -> {
              long size = Long.parseLong(context.getConfig("size", "100"));
              return server.getOnlinePlayers().stream().limit(size).map(Player::getName).toList();
            });
  }
}
