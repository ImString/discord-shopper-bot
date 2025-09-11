package me.imstring.discordshopper.handlers.interactions;

import lombok.Getter;
import me.imstring.discordshopper.Core;

@Getter
public abstract class DiscordInteraction {
    private final String name;
    private final DiscordInteractionOptions options;

    public DiscordInteraction(String name, DiscordInteractionOptions options) {
        this.name = name;
        this.options = options;
    }

    public abstract void exec(Object data, String interactionId, Core instance);
}
