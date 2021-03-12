package net.tycooncraft.messagebuilder.utils.menus;

import lombok.Getter;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public class Menu implements InventoryHolder {

    private final String name;
    @Getter private final MenuType type;

    @Getter private final Inventory inventory;
    private final Map<Integer, Item> slots = new HashMap<>();

    public Menu(String title, int size, MenuType type) {
        this(title, size, "general", type);
    }

    public Menu(String title, int size, String permission, MenuType type) {
        this.name = permission;
        this.type = type;
        this.inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', title));
    }

    public Menu setItem(int slot, Item item) {
        this.slots.put(slot, item);
        this.inventory.setItem(slot, item.getItem());
        return this;
    }

    public void onClick(int slot, Player player) {
        Item clicked = slots.get(slot);
        if (clicked == null) return;

        clicked.getConsumer().accept(player, clicked);
    }

    public void open(Player player) {
        if (player.hasPermission("messagebuilder.menu." + name)) {
            player.openInventory(this.inventory);
        }
    }

}
