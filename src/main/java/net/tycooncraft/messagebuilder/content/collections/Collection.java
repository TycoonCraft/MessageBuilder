package net.tycooncraft.messagebuilder.content.collections;

import lombok.Getter;
import net.tycooncraft.messagebuilder.content.messages.Message;
import net.tycooncraft.messagebuilder.content.messages.MessageAttribute;
import net.tycooncraft.messagebuilder.content.objects.ValidationResponse;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class Collection {

    private final Map<CollectionAttribute, Object> attributes;
    @Getter private final List<Message> messages;

    public Collection() {
        this.attributes = new HashMap<>();
        this.messages = new ArrayList<>();
    }

    public void setAttribute(CollectionAttribute attribute, Object value) {
        if (value == null) return;
        this.attributes.put(attribute, value);
    }

    public Object getAttribute(CollectionAttribute attribute) {
        return this.attributes.get(attribute);
    }

    public ValidationResponse isValid() {
        for (CollectionAttribute attribute : CollectionAttribute.values()) {
            if (attribute.isRequired() && !this.attributes.containsKey(attribute)) return new ValidationResponse(false, "Missing attribute '" + attribute.name() + "'");
        }

        return new ValidationResponse(true, "");
    }

    public ValidationResponse loadMessages(ConfigurationSection messages) {
        if (messages == null) return new ValidationResponse(false, "Collection '" + getAttribute(CollectionAttribute.NAME) + "' has no section named 'messages' in the saves.yml file");

        Set<String> keys = messages.getKeys(false);

        int success = 0;
        int failed = 0;

        for (String key : keys) {
            ConfigurationSection messageData = messages.getConfigurationSection(key);
            if (messageData == null) continue;

            Message message = new Message();
            message.setAttribute(MessageAttribute.NAME, messageData.get("name"));
            message.setAttribute(MessageAttribute.LINES, messageData.getStringList("lines"));

            ValidationResponse validation = message.isValid();
            if (!validation.isValid()) {
                failed++;
                continue;
            }

            success++;
            this.messages.add(message);
        }

        return new ValidationResponse(true, success + " message(s) have been saved, " + failed + " could not be fetched");
    }

}
