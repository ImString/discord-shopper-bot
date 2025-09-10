package me.imstring.discordshopper.handlers.commands.executors.settings;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.entities.GuildSettings;
import me.imstring.discordshopper.enums.GuildChannelsType;
import me.imstring.discordshopper.enums.GuildRolesType;
import me.imstring.discordshopper.handlers.commands.DiscordCommand;
import me.imstring.discordshopper.repositories.GuildSettingsRepository;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SetRoleCommand extends DiscordCommand {
    private final List<String> rolesTypes = Arrays.stream(GuildRolesType.values()).toList()
            .stream()
            .map(GuildRolesType::name)
            .toList();

    public SetRoleCommand() {
        super("setrole", "Define os cargos de automação do bot.",
                new ArrayList<>(Collections.singleton(Permission.ADMINISTRATOR))
        );
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event, Core instance) {
        GuildRolesType type;

        try {
            type = GuildRolesType.valueOf(event.getOption("type").getAsString().toUpperCase());
        } catch (IllegalArgumentException e) {
            event.reply("O tipo selecionado é inválido! Tipos válidos: " + String.join(", ", this.rolesTypes)).setEphemeral(true).queue();
            return;
        }

        GuildSettingsRepository guildSettingsRepository = new GuildSettingsRepository(instance);
        Optional<GuildSettings> guildSettingsOpt = guildSettingsRepository.findByGuildId(event.getGuild().getId());

        if (guildSettingsOpt.isEmpty()) {
            event.reply("❌ Seu servidor não está cadastrado, consulte o provedor!").setEphemeral(true).queue();
            return;
        }

        GuildSettings guildSettings = guildSettingsOpt.get();
        Role role = event.getOption("role").getAsRole();

        try {
            if (type == GuildRolesType.AUTO_ROLE) {
                guildSettings.setMemberAutoRoleId(role.getId());
            } else if (type == GuildRolesType.AUTHENTICATION) {
                guildSettings.setMemberAuthenticationRoleId(role.getId());
            }

            guildSettingsRepository.update(guildSettings);
            event.reply("Cargo " + role.getAsMention() + " definido com sucesso como ``" + type.name() + "``!").setEphemeral(true).queue();
        } catch (SQLException err) {
            event.reply("Tivemos um problema ao salvar o novo cargo!").setEphemeral(true).queue();
        }
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(
                        OptionType.STRING,
                        "type",
                        "Insira o tipo de cargo que deseja definir.",
                        true,
                        true
                ),
                new OptionData(
                        OptionType.ROLE,
                        "role",
                        "Selecione o cargo que deseja definir.",
                        true
                )
        );
    }

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
        if (!event.getFocusedOption().getName().equals("type")) return;

        String input = event.getFocusedOption().getValue().toUpperCase();
        List<Command.Choice> suggestions = this.rolesTypes.stream()
                .filter(type -> type.startsWith(input))
                .map(type -> new Command.Choice(type, type))
                .collect(Collectors.toList());

        event.replyChoices(suggestions).queue();
    }
}
