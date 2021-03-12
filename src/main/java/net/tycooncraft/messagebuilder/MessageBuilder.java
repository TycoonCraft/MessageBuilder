package net.tycooncraft.messagebuilder;

import net.tycooncraft.messagebuilder.commands.CommandModule;
import net.tycooncraft.messagebuilder.menus.MenuModule;
import net.tycooncraft.messagebuilder.resources.PluginFile;
import net.tycooncraft.messagebuilder.utils.messaging.MessageUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessageBuilder extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginFile savesFile = new PluginFile(this, "saves.yml");

        MessageUtil messageUtil = new MessageUtil();

        MenuModule menuModule = new MenuModule(savesFile);
        CommandModule commandModule = new CommandModule(this, messageUtil, menuModule);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
