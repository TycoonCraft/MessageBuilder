package net.tycooncraft.messagebuilder.content.messages;

import net.tycooncraft.messagebuilder.content.objects.ValidationResponse;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private final Map<MessageAttribute, Object> attributes;

    public Message() {
        this.attributes = new HashMap<>();
    }

    public void setAttribute(MessageAttribute attribute, Object value) {
        if (value == null) return;
        this.attributes.put(attribute, value);
    }

    public Object getAttribute(MessageAttribute attribute) {
        return this.attributes.get(attribute);
    }

    public ValidationResponse isValid() {
        for (MessageAttribute attribute : MessageAttribute.values()) {
            if (attribute.isRequired() && !this.attributes.containsKey(attribute)) return new ValidationResponse(false, "Missing attribute '" + attribute.name() + "'");
        }

        return new ValidationResponse(true, "");
    }

}
