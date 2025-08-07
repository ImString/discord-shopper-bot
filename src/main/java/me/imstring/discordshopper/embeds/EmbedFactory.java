package me.imstring.discordshopper.embeds;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.embeds.settings.CartSettingsEmbeds;
import me.imstring.discordshopper.embeds.settings.VerificationSettingsEmbeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

@RequiredArgsConstructor
public class EmbedFactory {

    private final Core core;

    public EmbedBuilder settingsVerification(Guild guild) {
        return new VerificationSettingsEmbeds().create(core, guild);
    }

    public EmbedBuilder settingsCart(Guild guild) {
        return new CartSettingsEmbeds().create(core, guild);
    }
}
