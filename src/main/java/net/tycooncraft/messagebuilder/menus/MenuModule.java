package net.tycooncraft.messagebuilder.menus;

import lombok.Getter;
import net.tycooncraft.messagebuilder.resources.PluginFile;
import net.tycooncraft.messagebuilder.utils.menus.Menu;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MenuModule {

    private final Map<String, Menu> cache;

    // TODO this is really bad practice, lets just say its subject to change
    @Getter private final PluginFile savesFile;

    public MenuModule(PluginFile savesFile) {
        this.cache = new HashMap<>();
        this.savesFile = savesFile;
    }

    public Menu getMenu(Class<?> menuClass, Player opener) {
        Menu menu = cache.getOrDefault(menuClass.getSimpleName(), null);
        if (menu != null) {
            if (menu.getType() == MenuType.STATIC) return menu;
            return load(menuClass.getName(), false, opener);
        }

        Menu newMenu = load(menuClass.getName(), false, opener);
        if (newMenu.getType() == MenuType.STATIC) this.cache.put(menuClass.getSimpleName(), newMenu);
        return newMenu;
    }

    public Menu load(String name, boolean cache, Player... players) {
        try {
            Class<?> clazz = Class.forName(name);
            Constructor<?>[] constructors = clazz.getConstructors();
            Object object = null;

            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == 1) object = constructor.newInstance(this);
                if (constructor.getParameterCount() == 2) {
                    if (players.length >= 1) object = constructor.newInstance(this, players[0]);
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
            exception.printStackTrace();
        }

        return new Menu("&8Menu not found", 9, MenuType.ERROR);
    }
}
