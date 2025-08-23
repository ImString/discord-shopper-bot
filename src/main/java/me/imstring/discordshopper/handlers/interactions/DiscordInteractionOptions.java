package me.imstring.discordshopper.handlers.interactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.InteractionType;

import java.util.List;

@Getter
@AllArgsConstructor
public class DiscordInteractionOptions {
    private List<String> customIds;
    private InteractionType type;
    // private Component componentType;

}
