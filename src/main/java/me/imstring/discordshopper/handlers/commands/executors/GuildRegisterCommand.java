package me.imstring.discordshopper.handlers.commands.executors;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.entities.GuildSettings;
import me.imstring.discordshopper.handlers.commands.DiscordCommand;
import me.imstring.discordshopper.repositories.GuildSettingsRepository;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class GuildRegisterCommand extends DiscordCommand {
    public GuildRegisterCommand() {
        super("guild-register", "Registra o bot no servidor, para utilização das funções.", new ArrayList<>(Collections.singleton(Permission.ADMINISTRATOR)));
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event, Core instance) {
        Guild guild = event.getGuild();
        if (guild == null) {
            throw new Error();
        }

        GuildSettingsRepository guildSettingsRepository = new GuildSettingsRepository(instance);
        Optional<GuildSettings> guildSettingsOpt = guildSettingsRepository.findByGuildId(guild.getId());

        if (guildSettingsOpt.isPresent()) {
            event.reply("Seu servidor já está cadastrado! Consulte o provedor!").setEphemeral(true).queue();
            return;
        }

        try {
            guildSettingsRepository.save(new GuildSettings(event.getGuild().getId()));

            instance.getCommandManager().loadSlashCommands(event.getGuild(), instance.getCommandManager().getCommandNames().stream()
                    .filter(name -> !name.equalsIgnoreCase("guild-register"))
                    .toList());

            event.reply("Parabéns seu servidor foi cadastrado no nosso sistema!").setEphemeral(true).queue();
        } catch (SQLException e) {
            event.reply("Tivemos um problema ao cadastrar os dados do servidor.").setEphemeral(true).queue();
        }
    }
}
