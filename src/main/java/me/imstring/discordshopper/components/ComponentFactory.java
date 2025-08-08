package me.imstring.discordshopper.components;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.components.impl.settings.CartSettingsEmbeds;
import me.imstring.discordshopper.components.impl.settings.VerificationSettingsEmbeds;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.entities.Guild;

@RequiredArgsConstructor
public class ComponentFactory {

    private final Core core;

    public Container settingsVerification(Guild guild) {
        return new VerificationSettingsEmbeds().create(core, guild);
    }

    public Container settingsCart(Guild guild) {
        return new CartSettingsEmbeds().create(core, guild);
    }
}
