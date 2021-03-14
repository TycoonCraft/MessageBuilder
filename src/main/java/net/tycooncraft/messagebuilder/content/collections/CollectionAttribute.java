package net.tycooncraft.messagebuilder.content.collections;

public enum CollectionAttribute {

    NAME(true),
    DESCRIPTION(false);

    private final boolean required;

    CollectionAttribute(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return this.required;
    }
}
