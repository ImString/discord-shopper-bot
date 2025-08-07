package me.imstring.discordshopper.commands.executors.common;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.commands.DiscordAbstractCommand;
import me.imstring.discordshopper.configurations.ApplicationConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class AvatarCommand extends DiscordAbstractCommand {
    public AvatarCommand() {
        super("avatar", "Confira o avatar de um usuário.");
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(
                        OptionType.USER,
                        "user",
                        "Insira o usuário",
                        false
                )
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Core instance) {
        User target = event.getOption("user") != null ? event.getOption("user").getAsUser() : event.getUser();
        String avatarUrl = target.getEffectiveAvatarUrl();

        EmbedBuilder embed = new EmbedBuilder();

        embed.setAuthor("Avatar de " + target.getEffectiveName());
        embed.setImage(avatarUrl);
        embed.setColor(instance.getEmbedDefaultColor());
        embed.setFooter("© " + ApplicationConfig.NAME, instance.getJda().getSelfUser().getEffectiveAvatarUrl());

        event.replyEmbeds(embed.build()).queue();
    }
}
