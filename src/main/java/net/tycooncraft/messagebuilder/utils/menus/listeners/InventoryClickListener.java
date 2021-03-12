package net.tycooncraft.messagebuilder.utils.menus.listeners;

import net.tycooncraft.messagebuilder.utils.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    public InventoryClickListener() {
    }

    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getHolder() != null && event.getClickedInventory().getHolder() instanceof Menu) {
                event.setCancelled(true);

                Menu menu = (Menu) event.getClickedInventory().getHolder();
                menu.onClick(event.getSlot(), (Player) event.getWhoClicked());
            }
        }
    }
}
