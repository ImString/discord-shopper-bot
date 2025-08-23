package me.imstring.discordshopper.handlers.interactions.executors.buttons;

import me.imstring.discordshopper.handlers.interactions.DiscordInteraction;
import me.imstring.discordshopper.handlers.interactions.DiscordInteractionOptions;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionType;

import java.util.List;

public class VerificationButton extends DiscordInteraction {

    public VerificationButton() {
        super("verification_button", new DiscordInteractionOptions(
                List.of("verify-account"),
                InteractionType.COMPONENT
        ));
    }

    @Override
    public void exec(Object data, String interactionId) {
        ButtonInteractionEvent event = (ButtonInteractionEvent) data;

        event.reply("✅ Registrado com sucesso!").setEphemeral(true).queue();
    }
}
