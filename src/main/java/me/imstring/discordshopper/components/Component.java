package me.imstring.discordshopper.components;

import me.imstring.discordshopper.Core;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.entities.Guild;

public interface Component {
    Container create(Core core, Guild guild);
}
