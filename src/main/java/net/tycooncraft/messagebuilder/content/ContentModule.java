package net.tycooncraft.messagebuilder.content;

import net.tycooncraft.messagebuilder.content.collections.Collection;
import net.tycooncraft.messagebuilder.content.collections.CollectionAttribute;
import net.tycooncraft.messagebuilder.content.objects.ValidationResponse;
import net.tycooncraft.messagebuilder.resources.PluginFile;
import net.tycooncraft.messagebuilder.utils.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContentModule {

    private final PluginFile savesFile;
    private final Map<String, Collection> collections;

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
                collection.setAttribute(CollectionAttribute.NAME, collectionData.get("name"));
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
}
