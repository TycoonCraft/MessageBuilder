package net.tycooncraft.messagebuilder.menus.messages;

import net.tycooncraft.messagebuilder.content.collections.Collection;
import net.tycooncraft.messagebuilder.content.messages.Message;
import net.tycooncraft.messagebuilder.content.messages.MessageAttribute;
import net.tycooncraft.messagebuilder.menus.MenuModule;
import net.tycooncraft.messagebuilder.utils.menus.Item;
import net.tycooncraft.messagebuilder.utils.menus.Menu;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import org.bukkit.Material;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageMenu extends Menu {

    public MessageMenu(MenuModule menuModule) {
        super("&8Menu not available", 9, "messages", MenuType.STATIC);
    }

    public MessageMenu(MenuModule menuModule, Collection collection) {
        this(menuModule, collection, 1);
    }

    private MessageMenu(MenuModule menuModule, Collection collection, int page) {
        super("&8Messages", 54, "messages", MenuType.STATIC);

        //  Fill the border with black stained glass pain
        super.fill(new Point(0, 0), new Point(8, 5), true, Material.BLACK_STAINED_GLASS_PANE);

        // Calculate what slots to place message items in
        List<Integer> messageSlots = super.calculateSlots(new Point(1, 1), new Point(7, 4));
        // Load all messages from the selected collection
        List<Message> messages = collection.getMessages();

        // Amount of slots available for message items
        int availableSlots = messages.size();
        // Where in the array to start
        int start = (availableSlots * page) - availableSlots;

        // Use the size of the messages variable and the current page and change the slots to go over accordingly
        if (messages.size() <= availableSlots * page)
            availableSlots = messages.size() - (availableSlots * (page - 1));

        for (int i = 0; i < availableSlots; i++) {
            setItem(messageSlots.get(i), new Item(Material.PAINTING)
                    .setName("&a" + messages.get(i + start).getAttribute(MessageAttribute.NAME).toString())
                    .setLore(this.wrapDescription(messages.get(i + start).getAttribute(MessageAttribute.LINES)))
                    .onClick((player, item) -> {

                    })
            );
        }

        // Close menu item
        setItem(49, new Item(Material.REDSTONE)
                .setName("&cClose")
                .onClick(((player, item) -> player.closeInventory()))
        );

        // Create new message item
        setItem(50, new Item(Material.COMPARATOR)
                .setName("&aCreate Message")
                .setLore(Arrays.asList(
                        "&7Create new messages",
                        "&7to make your plugins",
                        "&7as user-friendly as",
                        "&7possible.",
                        "&7",
                        "&eClick to start set-up!"))
                .onClick((player, item) -> {

                })
        );

        // Calculate how many pages are necessary to fit all messages
        int maxPage = (int) Math.ceil((double) messages.size() / 27);

        // Show the current page and the amount of pages available in the lore of the navigation items
        List<String> navigateLore = Collections.singletonList("&7(" + page + "/" + maxPage + ")");

        // Only show the 'Previous page' item when the player is not currently at page 1
        if (page > 1) {
            setItem(45, new Item(Material.ARROW)
                    .setName("&aPrevious Page")
                    .setLore(navigateLore)
                    .onClick(((player, item) -> {
                        new MessageMenu(menuModule, collection, page - 1).open(player);
                    }))
            );
        }

        // Only show the 'Next page' item when the player is not at the last page
        if (page < maxPage) {
            setItem(53, new Item(Material.ARROW)
                    .setName("&aNext Page")
                    .setLore(navigateLore)
                    .onClick(((player, item) -> {
                        new MessageMenu(menuModule, collection, page + 1).open(player);
                    }))
            );
        }
    }

    private List<String> wrapDescription(Object oDescription) {
        List<String> wrappedDescription = new ArrayList<>(Arrays.asList("&7", "&8Start Message"));
        if (oDescription != null) {
            List<String> description = (List<String>) oDescription;
            wrappedDescription.addAll(description);
        }

        Collections.addAll(wrappedDescription, "&8End Message", "&7", "&eClick to manage message!");
        return wrappedDescription;
    }
}
