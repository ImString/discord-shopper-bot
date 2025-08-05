package me.imstring.discordshopper.listeners.executors;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.commands.DiscordAbstractCommand;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CommandListener extends ListenerAdapter {

    private final Core instance;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        DiscordAbstractCommand command = instance.getCommandManager().getCommand(commandName);
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
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        instance.getCommandManager().loadSlashCommands(event.getGuild());
    }

}
