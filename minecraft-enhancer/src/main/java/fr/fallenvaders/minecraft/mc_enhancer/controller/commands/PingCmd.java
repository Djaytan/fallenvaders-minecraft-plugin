package fr.fallenvaders.minecraft.mc_enhancer.controller.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;

@CommandAlias("ping | ms")
public class PingCmd extends BaseCommand {

    @Default
    @Syntax("[player]")
    @Description("Test de connexion.")
    public void onPing(Player sender, @Optional Player target) {
        sender.sendMessage("Test");
    }
}
