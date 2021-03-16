package net.tycooncraft.messagebuilder.utils.menus.enums;

import org.bukkit.event.inventory.ClickType;

public enum SimpleClickType {

    LEFT(ClickType.LEFT, ClickType.SHIFT_LEFT, ClickType.DROP, ClickType.CONTROL_DROP),
    RIGHT(ClickType.RIGHT, ClickType.SHIFT_RIGHT)

    ;

    private final ClickType[] types;

    SimpleClickType(ClickType... equals) {
        this.types = equals;
    }

    public static SimpleClickType getType(ClickType bukkitClickType) {
        for (SimpleClickType type : values()) {
            if (type.has(bukkitClickType)) return type;
        }
        return LEFT;
    }

    public boolean has(ClickType bukkitClickType) {
        for (ClickType type : this.types) {
            if (bukkitClickType.equals(type)) return true;
        }
        return false;
    }



}
