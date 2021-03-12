package net.tycooncraft.messagebuilder.menus;

import net.tycooncraft.messagebuilder.utils.menus.Menu;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MenuModule {

    private final Map<String, Menu> cache;

    public MenuModule() {
        this.cache = new HashMap<>();
    }

    public Menu getMenu(String name, Player opener) {
        Menu menu = cache.getOrDefault(name, null);
        if (menu != null) {
            if (menu.getType() == MenuType.STATIC) return menu;
            return load(name, false, opener);
        }

        Menu newMenu = load(name, false, opener);
        if (newMenu.getType() == MenuType.STATIC) this.cache.put(name, newMenu);
        return newMenu;
    }

    public Menu load(String name, boolean cache, Player... players) {
        try {
            Class<?> clazz = Class.forName(name);
            Constructor<?>[] constructors = clazz.getConstructors();
            Object object = null;

            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == 0) object = constructor.newInstance();
                if (constructor.getParameterCount() == 1) {
                    if (players.length >= 1) object = constructor.newInstance(players[0]);
                }

                break;
            }

            if (object != null) {
                if (object instanceof Menu) {
                    if (cache) this.cache.put(name, (Menu) object);
                    return (Menu) object;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            return new Menu("&7Menu not found", 27, MenuType.STATIC);
        }

        return new Menu("&7Menu not found", 27, MenuType.STATIC);
    }
}
