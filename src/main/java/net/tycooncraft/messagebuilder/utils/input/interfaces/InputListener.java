package net.tycooncraft.messagebuilder.utils.input.interfaces;

import java.util.UUID;

public interface InputListener {

    void queueInput(UUID uuid, Input input);

}
