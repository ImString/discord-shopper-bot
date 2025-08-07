package me.imstring.discordshopper.commands.executors.settings;

import me.imstring.discordshopper.embeds.EmbedFactory;
import me.imstring.discordshopper.enums.GuildChannelsType;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.Command.Choice;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.commands.DiscordAbstractCommand;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SetChannelCommand extends DiscordAbstractCommand {
    private final List<String> channelsTypes = Arrays.stream(GuildChannelsType.values()).toList()
            .stream()
            .map(GuildChannelsType::name)
            .toList();

    public SetChannelCommand() {
        super("setchannel", "Define os canais de automação do bot.",
                new ArrayList<>(Collections.singleton(Permission.ADMINISTRATOR))
        );
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(
                        OptionType.STRING,
                        "type",
                        "Insira o tipo de canal que deseja definir.",
                        true,
                        true
                ),
                new OptionData(
                        OptionType.CHANNEL,
                        "channel",
                        "Selecione o canal que deseja definir.",
                        true
                )
        );
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event, Core instance) {
        GuildChannelsType type;

        try {
            type = GuildChannelsType.valueOf(event.getOption("type").getAsString().toUpperCase());
        } catch (IllegalArgumentException e) {
            event.reply("O tipo selecionado é inválido! Tipos válidos: " + String.join(", ", this.channelsTypes)).setEphemeral(true).queue();
            return;
        }

        GuildChannel channel = event.getOption("channel").getAsChannel();
        if (!channel.getType().isMessage()) {
            event.reply("O canal selecionado não é um canal de texto!").setEphemeral(true).queue();
            return;
        }

        if (!channel.getGuild().getSelfMember().hasPermission(channel, Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND)) {
            event.reply("O bot não tem permissão para ver ou enviar mensagens nesse canal!").setEphemeral(true).queue();
            return;
        }

        EmbedFactory embedFactory = new EmbedFactory(instance);
        MessageChannel messageChannel = (MessageChannel) channel;

        switch (type) {
            case VERIFICATION -> messageChannel.sendMessageEmbeds(embedFactory.settingsVerification(event.getGuild()).build()).addActionRow(
                    Button.primary("verify", "🔒 Verifique-se")
            ).queue();
            case CART -> messageChannel.sendMessageEmbeds(embedFactory.settingsCart(event.getGuild()).build()).addActionRow(
                    Button.success("manage-cart", "\uD83D\uDED2 Gerenciar Carrinho")
            ).queue();
        }

        event.reply("✅ Canal `" + type + "` configurado para: " + channel.getAsMention()).queue();
    }

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
        if (!event.getFocusedOption().getName().equals("type")) return;

        String input = event.getFocusedOption().getValue().toUpperCase();
        List<Choice> suggestions = this.channelsTypes.stream()
                .filter(type -> type.startsWith(input))
                .map(type -> new Choice(type, type))
                .collect(Collectors.toList());

        event.replyChoices(suggestions).queue();
    }
}
