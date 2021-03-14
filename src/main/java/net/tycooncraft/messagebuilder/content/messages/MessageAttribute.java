package net.tycooncraft.messagebuilder.content.messages;

public enum MessageAttribute {

    NAME(true),
    LINES(true),
    ;

    private final boolean required;

    MessageAttribute(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return this.required;
    }

}
