package me.imstring.discordshopper.handlers.listeners.executors;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@RequiredArgsConstructor
public class InteractionListener extends ListenerAdapter {
    private final Core instance;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getCustomId();
        if (buttonId == null) {
            event.reply("\uD83D\uDE11 Tivemos um problema ao processar a ação! Tente novamente ou contate o suporte!").setEphemeral(true).queue();
            return;
        }

        instance.getInteractionManager().handleInteraction(event, buttonId);
    }
}
