package me.imstring.discordshopper.handlers.commands;

import lombok.Getter;
import lombok.NonNull;
import me.imstring.discordshopper.Core;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class DiscordCommand {

    private final @NonNull String name;
    private final @NonNull String description;
    private @Getter Collection<Permission> defaultPermissions;

    public DiscordCommand(@NonNull String name) {
        this.name = name;
        this.description = "No description provided.";
    }

    public DiscordCommand(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }

    public DiscordCommand(@NonNull String name, @NonNull String description, Collection<Permission> defaultPermissions) {
        this.name = name;
        this.description = description;
        this.defaultPermissions = defaultPermissions;
    }

    public abstract void execute(SlashCommandInteractionEvent event, Core instance) throws SQLException;

    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
    }

    public @NonNull String getName() {
        return this.name;
    }

    public @NonNull String getDescription() {
        return this.description;
    }

    public List<OptionData> getOptions() {
        return Collections.emptyList();
    }
}
