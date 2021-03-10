package net.tycooncraft.messagebuilder.utils.messaging;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {

    public String replaceColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void sendMessage(String message, Player player) {
        player.sendMessage(replaceColors(message));
    }

}
