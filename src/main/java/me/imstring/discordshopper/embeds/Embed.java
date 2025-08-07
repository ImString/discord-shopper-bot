package me.imstring.discordshopper.embeds;

import me.imstring.discordshopper.Core;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

public abstract class Embed {
    public EmbedBuilder create(Core instance) {
        return null;
    }

    public EmbedBuilder create(Core instance, Guild guild) {
        return null;
    }
}
