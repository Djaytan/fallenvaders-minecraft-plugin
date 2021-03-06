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

package fr.fallenvaders.minecraft.core.controller.implementation;

import fr.fallenvaders.minecraft.core.controller.api.MessageController;
import fr.fallenvaders.minecraft.core.controller.api.parameter.MessageType;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

@Singleton
public class MessageControllerImpl implements MessageController {

  private final ConsoleCommandSender consoleCommandSender;
  private final MiniMessage miniMessage;
  private final ResourceBundle resourceBundle;
  private final Server server;

  @Inject
  public MessageControllerImpl(
      @NotNull ConsoleCommandSender consoleCommandSender,
      @NotNull MiniMessage miniMessage,
      @NotNull ResourceBundle resourceBundle,
      @NotNull Server server) {
    this.consoleCommandSender = consoleCommandSender;
    this.miniMessage = miniMessage;
    this.resourceBundle = resourceBundle;
    this.server = server;
  }

  @Override
  public void sendInfoMessage(@NotNull Audience audience, @NotNull Component message) {
    sendMessage(audience, MessageType.INFO, message);
  }

  @Override
  public void sendSuccessMessage(@NotNull Audience audience, @NotNull Component message) {
    sendMessage(audience, MessageType.SUCCESS, message);
  }

  @Override
  public void sendFailureMessage(@NotNull Audience audience, @NotNull Component message) {
    sendMessage(audience, MessageType.FAILURE, message);
  }

  @Override
  public void sendWarningMessage(@NotNull Audience audience, @NotNull Component message) {
    sendMessage(audience, MessageType.WARNING, message);
  }

  @Override
  public void sendErrorMessage(@NotNull Audience audience, @NotNull Component message) {
    sendMessage(audience, MessageType.ERROR, message);
  }

  public void sendConsoleMessage(@NotNull Component message) {
    consoleCommandSender.sendMessage(message, net.kyori.adventure.audience.MessageType.SYSTEM);
  }

  @Override
  public void broadcastMessage(@NotNull Component message) {
    sendMessage(Audience.audience(server.getOnlinePlayers()), MessageType.BROADCAST, message);
  }

  private void sendMessage(
      @NotNull Audience audience, @NotNull MessageType messageType, @NotNull Component message) {
    Component formattedMessage = formatMessage(messageType, message);
    audience.sendMessage(formattedMessage, net.kyori.adventure.audience.MessageType.SYSTEM);
  }

  private @NotNull Component formatMessage(
      @NotNull MessageType messageType, @NotNull Component message) {
    // We don't simplify it to let IDE recognizes automatically used keys
    String messageFormatKey =
        switch (messageType) {
          case INFO -> "fallenvaders.common.format.message.info";
          case SUCCESS -> "fallenvaders.common.format.message.success";
          case FAILURE -> "fallenvaders.common.format.message.failure";
          case WARNING -> "fallenvaders.common.format.message.warning";
          case ERROR -> "fallenvaders.common.format.message.error";
          case BROADCAST -> "fallenvaders.common.format.message.broadcast";
          case DEBUG -> "fallenvaders.common.format.message.debug";
        };

    return miniMessage
        .deserialize(
            resourceBundle.getString(messageFormatKey),
            TagResolver.resolver(Placeholder.component("fv_message_content", message)))
        .decoration(TextDecoration.ITALIC, false);
  }
}
