package net.tycooncraft.messagebuilder.utils.input.chat;

import net.tycooncraft.messagebuilder.utils.input.interfaces.Input;
import net.tycooncraft.messagebuilder.utils.input.interfaces.InputListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatInputListener implements Listener, InputListener {

    private final Map<UUID, ChatInput> inputQueue;

    public ChatInputListener() {
        this.inputQueue = new HashMap<>();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (inputQueue.containsKey(uuid)) {
            String message = event.getMessage();
            ChatInput queued = inputQueue.get(uuid);
            inputQueue.remove(uuid);

            if (message.equalsIgnoreCase("cancel")) {
                queued.cancel();
                return;
            }

            queued.onInput(message);
        }
    }

    @Override
    public void queueInput(UUID uuid, Input input) {
        if (input instanceof ChatInput) {
            this.inputQueue.put(uuid, (ChatInput) input);
        }
    }
}
