package net.tycooncraft.messagebuilder.utils.input;

import net.tycooncraft.messagebuilder.MessageBuilder;
import net.tycooncraft.messagebuilder.utils.input.chat.ChatInputListener;
import net.tycooncraft.messagebuilder.utils.input.enums.InputType;
import net.tycooncraft.messagebuilder.utils.input.interfaces.Input;
import net.tycooncraft.messagebuilder.utils.input.interfaces.InputListener;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputModule {

    private final Map<InputType, InputListener> listeners;

    public InputModule(MessageBuilder instance) {
        this.listeners = new HashMap<>();

        ChatInputListener chatInputListener = new ChatInputListener();
        this.listeners.put(InputType.CHAT, chatInputListener);

        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(chatInputListener, instance);

    }

    public void queueInput(UUID uuid, Input input) {
        InputListener listener = this.listeners.get(input.getType());
        listener.queueInput(uuid, input);
    }

}
