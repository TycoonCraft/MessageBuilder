package net.tycooncraft.messagebuilder.utils.menus;

import lombok.Getter;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import net.tycooncraft.messagebuilder.utils.menus.enums.SimpleClickType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void onClick(int slot, Player player, ClickType type) {
        Item clicked = slots.get(slot);
        if (clicked == null) return;

        clicked.onClick(SimpleClickType.getType(type), player);
    }

    public void open(Player player, boolean menuSwitch) {
        if (player.hasPermission("messagebuilder.menu." + name)) {
            player.openInventory(this.inventory);
            if (menuSwitch)
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.7f, 1.0f);
        }
    }

    public void fillEmpty(Material material) {
        ItemStack fillItem = new ItemStack(material, 1);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (slots.get(i) == null) inventory.setItem(i, fillItem.clone());
        }
    }

    public void fill(Point start, Point end, boolean borderOnly, Material material) {
        int deltaX = end.x - start.x;
        ItemStack stack = new ItemStack(material, 1);

        if (borderOnly) {
            // fill top and bottom row
            for (int x = start.x; x <= end.x; x++) {
                int topRow = start.y * 9;
                inventory.setItem(topRow + x, stack);

                int bottomRow = end.y * 9;
                inventory.setItem(bottomRow + x, stack);
            }

            // fill right and left column
            for (int y = start.y + 1; y < end.y; y++) {
                int column = y * 9;

                inventory.setItem(column + start.x, stack);
                inventory.setItem(column + (start.x == 0 ? deltaX : deltaX + start.x), stack);
            }
        } else {
            // fill every row
            for (int y = start.y; y <= end.y; y++) {
                int column = y * 9;

                for (int x = start.x; x <= end.x; x++) {
                    inventory.setItem(column + x, stack);
                }
            }
        }
    }

    public List<Integer> calculateSlots(Point start, Point end) {
        List<Integer> slots = new ArrayList<>();
        for (int y = start.y; y <= end.y; y++) {
            int column = y * 9;
            for (int x = start.x; x <= end.x; x++) {
                slots.add(column + x);
            }
        }
        return slots;
    }

}
