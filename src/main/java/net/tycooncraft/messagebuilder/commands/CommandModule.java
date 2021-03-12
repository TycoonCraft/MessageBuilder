package net.tycooncraft.messagebuilder.commands;

import net.tycooncraft.messagebuilder.MessageBuilder;
import net.tycooncraft.messagebuilder.commands.maincommands.MessagesCommand;
import net.tycooncraft.messagebuilder.menus.MenuModule;
import net.tycooncraft.messagebuilder.utils.messaging.MessageUtil;

public class CommandModule {

    public CommandModule(MessageBuilder instance, MessageUtil messageUtil, MenuModule menuModule) {
        instance.getCommand("messages").setExecutor(new MessagesCommand(messageUtil, menuModule));
    }

}
