package net.tycooncraft.messagebuilder;

import net.tycooncraft.messagebuilder.commands.CommandModule;
import net.tycooncraft.messagebuilder.content.ContentModule;
import net.tycooncraft.messagebuilder.menus.MenuModule;
import net.tycooncraft.messagebuilder.resources.PluginFile;
import net.tycooncraft.messagebuilder.utils.input.InputModule;
import net.tycooncraft.messagebuilder.utils.menus.listeners.InventoryClickListener;
import net.tycooncraft.messagebuilder.utils.messaging.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessageBuilder extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginFile savesFile = new PluginFile(this, "saves.yml");

        MessageUtil messageUtil = new MessageUtil();

        ContentModule contentModule = new ContentModule(savesFile);
        InputModule inputModule = new InputModule(this);
        MenuModule menuModule = new MenuModule(this, contentModule, inputModule);
        CommandModule commandModule = new CommandModule(this, messageUtil, menuModule);

        this.registerListeners(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners(MessageBuilder instance) {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new InventoryClickListener(), instance);
    }
}
