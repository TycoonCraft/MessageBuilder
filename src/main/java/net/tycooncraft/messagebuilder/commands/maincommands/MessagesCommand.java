package net.tycooncraft.messagebuilder.commands.maincommands;

import lombok.AllArgsConstructor;
import net.tycooncraft.messagebuilder.menus.MenuModule;
import net.tycooncraft.messagebuilder.menus.collections.CollectionMenu;
import net.tycooncraft.messagebuilder.utils.messaging.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class MessagesCommand implements CommandExecutor {

    private final MessageUtil messageUtil;
    private final MenuModule menuModule;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            menuModule.getMenu(CollectionMenu.class, player).open(player);
        } else {
            messageUtil.sendMessage("&cThis command is only available to players.", sender);
        }

        return true;
    }
}
