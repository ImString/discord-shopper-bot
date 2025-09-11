package me.imstring.discordshopper.components;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.components.impl.settings.CartSettingsComponent;
import me.imstring.discordshopper.components.impl.settings.VerificationSettingsComponent;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.entities.Guild;

@RequiredArgsConstructor
public class ComponentFactory {

    private final Core instance;
    private final Guild guild;

    public Container settingsVerification() {
        VerificationSettingsComponent component = new VerificationSettingsComponent();
        return component.create(this.instance, this.guild);
    }

    public Container settingsCart() {
        return new CartSettingsComponent().create(this.instance, this.guild);
    }
}
