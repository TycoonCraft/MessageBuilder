package net.tycooncraft.messagebuilder.menus.collections;

import net.tycooncraft.messagebuilder.content.collections.Collection;
import net.tycooncraft.messagebuilder.content.collections.CollectionAttribute;
import net.tycooncraft.messagebuilder.menus.MenuModule;
import net.tycooncraft.messagebuilder.menus.messages.MessageMenu;
import net.tycooncraft.messagebuilder.utils.input.chat.ChatInput;
import net.tycooncraft.messagebuilder.utils.menus.Item;
import net.tycooncraft.messagebuilder.utils.menus.Menu;
import net.tycooncraft.messagebuilder.utils.menus.enums.MenuType;
import net.tycooncraft.messagebuilder.utils.menus.enums.SimpleClickType;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionMenu extends Menu {

    public CollectionMenu(MenuModule menuModule) {
        this(menuModule, 1);
    }

    private CollectionMenu(MenuModule menuModule, int page) {
        super("&8Collections", 54, "collections", MenuType.STATIC);

        // Fill the border with black stained glass pane
        super.fill(new Point(0, 0), new Point(8, 5), true, Material.BLACK_STAINED_GLASS_PANE);

        // Calculate what slots to place collection items in
        List<Integer> collectionSlots = super.calculateSlots(new Point(1, 1), new Point(7, 4));
        // Load collections from the content module
        Collection[] collections = menuModule.getContentModule().getCollections().values().toArray(new Collection[0]);

        // Amount of slots available for collection items
        int availableSlots = collectionSlots.size();
        // Where in the array to start
        int start = (availableSlots * page) - availableSlots;

        // Use the size of the collections variable and the current page and change the slots to go over accordingly
        if (collections.length <= availableSlots * page)
            availableSlots = collections.length - (availableSlots * (page - 1));

        for (int i = 0; i < availableSlots; i++) {
            int finalI = i;
            setItem(collectionSlots.get(i), new Item(Material.PAINTING)
                    .setName("&a" + collections[i + start].getAttribute(CollectionAttribute.NAME).toString())
                    .setLore(this.wrapDescription(collections[i + start].getAttribute(CollectionAttribute.DESCRIPTION), collections[i + start].getMessages().size()))
                    .setOnClick(SimpleClickType.LEFT, (player, item) -> {
                        new MessageMenu(menuModule, collections[finalI + start]).open(player, true);
                    })
                    .setOnClick(SimpleClickType.RIGHT, (player, item) -> {
                        // TODO rename or delete collection
                    })
            );
        }

        // Close menu item
        setItem(49, new Item(Material.REDSTONE)
                .setName("&cClose")
                .setOnClick(SimpleClickType.LEFT, ((player, item) -> player.closeInventory()))
        );

        // Create new collection item
        setItem(50, new Item(Material.REPEATER)
                .setName("&aCreate Collection")
                .setLore(Arrays.asList(
                        "&7Set up new collections",
                        "&7in order to keep",
                        "&7a good overview",
                        "&7on your projects.",
                        "&7",
                        "&eClick to start set-up!"))
                .setOnClick(SimpleClickType.LEFT, (player, item) -> {
                    player.closeInventory();

                    menuModule.getInputModule().queueInput(player.getUniqueId(), new ChatInput(player, "&aEnter a new collection name or type 'cancel' to stop the set-up process:",
                            ((inputPlayer, message) -> {
                                // TODO save

                                Menu reloadedMenu = menuModule.reload(CollectionMenu.class);
                                menuModule.openMenuSync(reloadedMenu, inputPlayer, true);
                            })
                    ));

                })
        );

        // Calculate how many pages are necessary to fit all collections
        int maxPage = (int) Math.ceil((double) collections.length / 27);

        // Show the current page and the amount of pages available in the lore of the navigation items
        List<String> navigateLore = Collections.singletonList("&7(" + page + "/" + maxPage + ")");

        // Only show the 'Previous page' item when the player is not currently at page 1
        if (page > 1) {
            setItem(45, new Item(Material.ARROW)
                    .setName("&aPrevious Page")
                    .setLore(navigateLore)
                    .setOnClick(SimpleClickType.LEFT, ((player, item) -> {
                        new CollectionMenu(menuModule, page - 1).open(player, true);
                    }))
            );
        }

        // Only show the 'Next page' item when the player is not at the last page
        if (page < maxPage) {
            setItem(53, new Item(Material.ARROW)
                    .setName("&aNext Page")
                    .setLore(navigateLore)
                    .setOnClick(SimpleClickType.LEFT, (player, item) -> {
                        new CollectionMenu(menuModule, page + 1).open(player, true);
                    })
            );
        }
    }

    private List<String> wrapDescription(Object oDescription, int availableMessages) {
        List<String> wrappedDescription = new ArrayList<>();
        if (oDescription != null) {
            String description = (String) oDescription;
            String wrappedString = WordUtils.wrap(description, 25);
            String[] wrapped = wrappedString.split(System.lineSeparator());

            wrappedDescription = Arrays.stream(wrapped).map(s -> s = "&7" + s).collect(Collectors.toList());
        }

        Collections.addAll(wrappedDescription, "&7", "&8➤ " + availableMessages + " message(s) available", "&7", "&eLeft-click to show messages.", "&bRight-click to edit collection.");
        return wrappedDescription;
    }
}
