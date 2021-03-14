package net.tycooncraft.messagebuilder.menus;

import lombok.Getter;
import net.tycooncraft.messagebuilder.content.ContentModule;
import net.tycooncraft.messagebuilder.utils.menus.Menu;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MenuModule {

    private final Map<String, Menu> cache;

    // TODO this is really really really bad practice, lets just say its subject to change
    @Getter private final ContentModule contentModule;

    public MenuModule(ContentModule contentModule) {
        this.cache = new HashMap<>();
        this.contentModule = contentModule;
    }

    /**
     * Get a cached menu or load a new menu
     * @param menuClass menu class to get or create an instance from
     * @param opener player to load the menu for
     * @return cached/new instance of the given menu
     */
    public Menu getMenu(Class<?> menuClass, Player opener) {
        Menu menu = cache.getOrDefault(menuClass.getSimpleName(), null);
        if (menu != null) {
            if (menu.getType() == MenuType.STATIC) return menu;
            return load(menuClass, false, opener);
        }

        Menu newMenu = load(menuClass, false, opener);
        if (newMenu.getType() == MenuType.STATIC) this.cache.put(menuClass.getSimpleName(), newMenu);
        return newMenu;
    }

    /**
     * Reload a menu that uses MenuType.STATIC
     * @param menuClass menu class to reload
     */
    public void reload(Class<?> menuClass) {
        this.load(menuClass, true);
    }

    /**
     * Load any type of menu and cache it if required
     * @param clazz menu class to load
     * @param cache whether to save the menu in memory, for easy and fast access
     * @param players optional player parameter for dynamic menus
     * @return new instance of given menu
     */
    private Menu load(Class<?> clazz, boolean cache, Player... players) {
        try {
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
                    if (cache) this.cache.put(clazz.getSimpleName(), (Menu) object);
                    return (Menu) object;
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }

        return new Menu("&8Menu not found", 9, MenuType.ERROR);
    }
}
