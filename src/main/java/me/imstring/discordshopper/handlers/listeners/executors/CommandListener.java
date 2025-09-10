package me.imstring.discordshopper.handlers.listeners.executors;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.entities.GuildSettings;
import me.imstring.discordshopper.handlers.commands.DiscordCommand;
import me.imstring.discordshopper.repositories.GuildSettingsRepository;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CommandListener extends ListenerAdapter {

    private final Core instance;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        DiscordCommand command = instance.getCommandManager().getCommand(commandName);
        if (command == null) {
            event.reply("Comando não encontrado!").setEphemeral(true).queue();
            return;
        }

        try {
            command.execute(event, instance);
        } catch (Exception e) {
            event.reply("Tivemos um problema ao executar o comando: " + e.getMessage()).setEphemeral(true).queue();
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        String commandName = event.getName();
        DiscordCommand command = instance.getCommandManager().getCommand(commandName);

        if (command != null) {
            command.onAutoComplete(event);
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        Optional<GuildSettings> guildSettingsOpt = new GuildSettingsRepository(instance)
                .findByGuildId(event.getGuild().getId());

        if (guildSettingsOpt.isEmpty()) {
            instance.getCommandManager().loadSlashCommands(event.getGuild(), List.of("guild-register"));
            return;
        }

        List<String> commandsToRegister = instance.getCommandManager().getCommandNames().stream()
                .filter(name -> !name.equalsIgnoreCase("guild-register"))
                .toList();

        instance.getCommandManager().loadSlashCommands(event.getGuild(), commandsToRegister);
    }

}
