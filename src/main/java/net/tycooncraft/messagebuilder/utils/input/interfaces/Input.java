package net.tycooncraft.messagebuilder.utils.input.interfaces;

import net.tycooncraft.messagebuilder.utils.input.enums.InputType;

public interface Input {

    void onInput(String input);
    void cancel();
    InputType getType();

}
