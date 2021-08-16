package fr.fallenvaders.minecraft.mail_box.utils;

import fr.fallenvaders.minecraft.mail_box.MailBox;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageUtils {

    public static String PLUGIN_PREFIX = MailBox.main.getConfig().getString("plugin_prefix");

    public static void sendMessage(CommandSender target, MessageLevel level, String msg) {
        if (target != null) {
            String tempMsg = msg.replace(ChatColor.RESET.toString(), level.getColor());
            String toSend = PLUGIN_PREFIX + level.getColor() + tempMsg;

            target.sendMessage(toSend);

        }


    }

    public static void sendMessage(CommandSender target, MessageLevel level, String msg, Object... args) {
        sendMessage(target, level, String.format(msg, args));
    }

}
