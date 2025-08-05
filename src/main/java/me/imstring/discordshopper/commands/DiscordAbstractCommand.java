package me.imstring.discordshopper.commands;

import lombok.NonNull;
import me.imstring.discordshopper.Core;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class DiscordAbstractCommand {

    private final @NonNull String name;
    private final @NonNull String description;

    public DiscordAbstractCommand(@NonNull String name) {
        this.name = name;
        this.description = "No description provided.";
    }

    public DiscordAbstractCommand(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute(SlashCommandInteractionEvent event, Core instance);

    public @NonNull String getName() {
        return this.name;
    }

    public @NonNull String getDescription() {
        return this.description;
    }

}
