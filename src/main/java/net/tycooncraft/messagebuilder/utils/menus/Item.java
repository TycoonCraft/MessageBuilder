package net.tycooncraft.messagebuilder.utils.menus;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiConsumer;

public class Item {

    @Getter private ItemStack item;
    @Getter private BiConsumer<Player, Item> consumer = (a, b) -> {};

    public Item(ItemStack stack) {
        if (stack == null) stack = new ItemStack(Material.AIR, 1);

        this.item = stack.clone();
    }

    public Item(Material material) {
        this.item = new ItemStack(material, 1);
    }

    public Item setLore(List<String> lines) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.setLore(lines);
        this.item.setItemMeta(meta);
        return this;
    }

    public Item setName(String name) {
        name = ChatColor.translateAlternateColorCodes('&', name);

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;

        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public Item onClick(BiConsumer<Player, Item> onClick) {
        this.consumer = onClick;
        return this;
    }
    
}
