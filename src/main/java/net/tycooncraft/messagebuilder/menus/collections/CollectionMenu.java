package net.tycooncraft.messagebuilder.menus.collections;

import net.tycooncraft.messagebuilder.menus.MenuModule;
import net.tycooncraft.messagebuilder.utils.menus.Menu;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import org.bukkit.Material;

import java.awt.*;

public class CollectionMenu extends Menu {

    public CollectionMenu(MenuModule menuModule) {
        super("&8Collections", 54, "collections", MenuType.STATIC);

        fill(new Point(0, 0), new Point(8, 5), true, Material.BLACK_STAINED_GLASS_PANE);
    }
}
