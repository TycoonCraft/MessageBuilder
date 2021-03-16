package net.tycooncraft.messagebuilder.utils.menus;

import lombok.Getter;
import net.tycooncraft.messagebuilder.utils.menus.enums.SimpleClickType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class Item {

    @Getter private ItemStack item;
    private Map<SimpleClickType, BiConsumer<Player, Item>> clickExecutables = new HashMap<>();

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
        List<String> colorCoded = new ArrayList<>();
        for (String line : lines) {
            colorCoded.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(colorCoded);
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

    public Item setOnClick(SimpleClickType clickType, BiConsumer<Player, Item> onClick) {
        this.clickExecutables.put(clickType, onClick);
        return this;
    }

    public void onClick(SimpleClickType type, Player player) {
        this.clickExecutables.getOrDefault(type, this.clickExecutables.getOrDefault(SimpleClickType.LEFT, (a, b) -> {})).accept(player, this);
    }

    
}
