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
import fr.fallenvaders.minecraft.plugin.command.AnvilCommand;
import fr.fallenvaders.minecraft.plugin.command.BroadcastCommand;
import fr.fallenvaders.minecraft.plugin.command.CartographyTableCommand;
import fr.fallenvaders.minecraft.plugin.command.CraftingTableCommand;
import fr.fallenvaders.minecraft.plugin.command.EnderChestCommand;
import fr.fallenvaders.minecraft.plugin.command.FeedCommand;
import fr.fallenvaders.minecraft.plugin.command.FlyCommand;
import fr.fallenvaders.minecraft.plugin.command.GodCommand;
import fr.fallenvaders.minecraft.plugin.command.GrindstoneCommand;
import fr.fallenvaders.minecraft.plugin.command.HealCommand;
import fr.fallenvaders.minecraft.plugin.command.LoomCommand;
import fr.fallenvaders.minecraft.plugin.command.PingCommand;
import fr.fallenvaders.minecraft.plugin.command.SmithingTableCommand;
import fr.fallenvaders.minecraft.plugin.command.SpawnCommand;
import fr.fallenvaders.minecraft.plugin.command.StonecutterCommand;
import fr.fallenvaders.minecraft.plugin.command.SuicideCommand;
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

  private final AnvilCommand anvilCommand;
  private final BroadcastCommand broadcastCommand;
  private final CartographyTableCommand cartographyTableCommand;
  private final CraftingTableCommand craftingTableCommand;
  private final EnderChestCommand enderChestCommand;
  private final FeedCommand feedCommand;
  private final FlyCommand flyCommand;
  private final GodCommand godCommand;
  private final GrindstoneCommand grindstoneCommand;
  private final HealCommand healCommand;
  private final LoomCommand loomCommand;
  private final PingCommand pingCommand;
  private final SmithingTableCommand smithingTableCommand;
  private final SpawnCommand spawnCommand;
  private final StonecutterCommand stonecutterCommand;
  private final SuicideCommand suicideCommand;

  @Inject
  public CommandRegister(
      @NotNull PaperCommandManager paperCommandManager,
      @NotNull Server server,
      @NotNull AnvilCommand anvilCommand,
      @NotNull BroadcastCommand broadcastCommand,
      @NotNull CartographyTableCommand cartographyTableCommand,
      @NotNull CraftingTableCommand craftingTableCommand,
      @NotNull EnderChestCommand enderChestCommand,
      @NotNull FeedCommand feedCommand,
      @NotNull FlyCommand flyCommand,
      @NotNull GodCommand godCommand,
      @NotNull GrindstoneCommand grindstoneCommand,
      @NotNull HealCommand healCommand,
      @NotNull LoomCommand loomCommand,
      @NotNull PingCommand pingCommand,
      @NotNull SmithingTableCommand smithingTableCommand,
      @NotNull SpawnCommand spawnCommand,
      @NotNull StonecutterCommand stonecutterCommand,
      @NotNull SuicideCommand suicideCommand) {
    this.paperCommandManager = paperCommandManager;
    this.server = server;

    this.anvilCommand = anvilCommand;
    this.broadcastCommand = broadcastCommand;
    this.cartographyTableCommand = cartographyTableCommand;
    this.craftingTableCommand = craftingTableCommand;
    this.enderChestCommand = enderChestCommand;
    this.feedCommand = feedCommand;
    this.flyCommand = flyCommand;
    this.godCommand = godCommand;
    this.grindstoneCommand = grindstoneCommand;
    this.healCommand = healCommand;
    this.loomCommand = loomCommand;
    this.pingCommand = pingCommand;
    this.smithingTableCommand = smithingTableCommand;
    this.spawnCommand = spawnCommand;
    this.stonecutterCommand = stonecutterCommand;
    this.suicideCommand = suicideCommand;
  }

  public void registerCommands() {
    paperCommandManager.registerCommand(anvilCommand);
    paperCommandManager.registerCommand(broadcastCommand);
    paperCommandManager.registerCommand(cartographyTableCommand);
    paperCommandManager.registerCommand(craftingTableCommand);
    paperCommandManager.registerCommand(enderChestCommand);
    paperCommandManager.registerCommand(feedCommand);
    paperCommandManager.registerCommand(flyCommand);
    paperCommandManager.registerCommand(godCommand);
    paperCommandManager.registerCommand(grindstoneCommand);
    paperCommandManager.registerCommand(healCommand);
    paperCommandManager.registerCommand(loomCommand);
    paperCommandManager.registerCommand(pingCommand);
    paperCommandManager.registerCommand(smithingTableCommand);
    paperCommandManager.registerCommand(spawnCommand);
    paperCommandManager.registerCommand(stonecutterCommand);
    paperCommandManager.registerCommand(suicideCommand);
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
