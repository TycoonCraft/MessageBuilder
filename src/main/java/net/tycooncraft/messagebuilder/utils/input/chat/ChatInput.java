package net.tycooncraft.messagebuilder.utils.input.chat;

import lombok.Getter;
import net.tycooncraft.messagebuilder.utils.input.enums.InputType;
import net.tycooncraft.messagebuilder.utils.input.interfaces.Input;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class ChatInput implements Input {

    private final Player player;
    private final BiConsumer<Player, String> consumer;
    @Getter private final InputType type = InputType.CHAT;

    private static final String CANCELLED_MESSAGE = ChatColor.translateAlternateColorCodes('&', "&cInput cancelled.");

    public ChatInput(Player player, String prompt, BiConsumer<Player, String> onInput) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prompt));
        this.player = player;
        this.consumer = onInput;
    }

    public void onInput(String input) {
        this.consumer.accept(player, input);
    }

    public void cancel() {
        this.player.sendMessage(CANCELLED_MESSAGE);
    }
}
