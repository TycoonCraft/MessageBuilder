package net.tycooncraft.messagebuilder.content.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationResponse {

    private final boolean valid;
    private final String message;

}
