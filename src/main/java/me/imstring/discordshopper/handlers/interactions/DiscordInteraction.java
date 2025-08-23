package me.imstring.discordshopper.handlers.interactions;

public abstract class DiscordInteraction {
    private String name;
    private DiscordInteractionOptions options;

    public DiscordInteraction(String name, DiscordInteractionOptions options) {
        this.name = name;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public DiscordInteractionOptions getOptions() {
        return options;
    }

    public abstract void exec(Object data, String interactionId);
}
