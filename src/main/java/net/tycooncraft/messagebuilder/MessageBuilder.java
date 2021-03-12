package net.tycooncraft.messagebuilder;

import net.tycooncraft.messagebuilder.resources.PluginFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessageBuilder extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginFile savesFile = new PluginFile(this, "saves.yml");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
