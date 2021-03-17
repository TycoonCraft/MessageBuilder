package net.tycooncraft.messagebuilder.content;

import lombok.Getter;
import net.tycooncraft.messagebuilder.content.collections.Collection;
import net.tycooncraft.messagebuilder.content.collections.CollectionAttribute;
import net.tycooncraft.messagebuilder.content.objects.ValidationResponse;
import net.tycooncraft.messagebuilder.resources.PluginFile;
import net.tycooncraft.messagebuilder.utils.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContentModule {

    private final PluginFile savesFile;
    @Getter private final Map<String, Collection> collections;

    public ContentModule(PluginFile savesFile) {
        this.savesFile = savesFile;
        this.collections = new HashMap<>();

        this.loadCollections(savesFile);
    }

    private void loadCollections(PluginFile savesFile) {
        ConfigurationSection saves = savesFile.getConfiguration().getConfigurationSection("collections");
        if (saves != null) {
            Set<String> keys = saves.getKeys(false);

            for (String key : keys) {
                ConfigurationSection collectionData = saves.getConfigurationSection(key);
                if (collectionData == null) continue;

                Collection collection = new Collection();
                collection.setAttribute(CollectionAttribute.NAME, key); // use key instead of custom display name, since that might cause some confusing when creating new collections
                collection.setAttribute(CollectionAttribute.DESCRIPTION, collectionData.get("description"));

                ValidationResponse valid = collection.isValid();
                if (!valid.isValid()) {
                    Logger.console("Error while loading collection '" + key + "': " + valid.getMessage());
                    continue;
                }

                ValidationResponse validMessages = collection.loadMessages(collectionData.getConfigurationSection("messages"));
                Logger.console("Validating loaded messages for collection '" + key + "' - Message: " + validMessages.getMessage());

                this.collections.put(key, collection);
            }

            return;
        }

        Logger.console("No section named 'collections' found in saves.yml");
    }

    public ValidationResponse createCollection(String name) {
        if (this.savesFile.getConfiguration().get("collections." + name) != null) {
            return new ValidationResponse(false, "&cA collection with the name '" + name + "' already exists!");
        }

        this.savesFile.getConfiguration().set("collections." + name + ".messages", new ArrayList<>());
        this.savesFile.save();

        Collection collection = new Collection();
        collection.setAttribute(CollectionAttribute.NAME, name);

        // No need to validate first, because everything has already been validated before
        this.collections.put(name, collection);

        return new ValidationResponse(true, "&aCollection has been created! Use '/mb' to add and modify messages.");
    }
}
