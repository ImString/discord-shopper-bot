package me.imstring.discordshopper.components;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.components.impl.settings.CartSettingsComponent;
import me.imstring.discordshopper.components.impl.settings.VerificationSettingsComponent;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.entities.Guild;

@RequiredArgsConstructor
public class ComponentFactory {

    private final Core core;

    public Container settingsVerification(Guild guild) {
        return new VerificationSettingsComponent().create(core, guild);
    }

    public Container settingsCart(Guild guild) {
        return new CartSettingsComponent().create(core, guild);
    }
}
