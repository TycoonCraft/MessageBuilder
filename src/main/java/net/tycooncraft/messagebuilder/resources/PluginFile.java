package net.tycooncraft.messagebuilder.resources;

import lombok.Getter;
import net.tycooncraft.messagebuilder.MessageBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginFile {

    @Getter private FileConfiguration configuration;
    private final File file;
    private final String fileName;

    public PluginFile(MessageBuilder instance, String fileName) {
        this.loadFile(instance, fileName);

        this.fileName = fileName;
        this.file = new File(instance.getDataFolder(), fileName);
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void loadFile(MessageBuilder instance, String fileName) {
        if (!instance.getDataFolder().exists()) instance.getDataFolder().mkdir();

        File file = new File(instance.getDataFolder(), fileName);

        if (!file.exists()) {
            instance.saveResource(fileName, true);
        }
    }

}
