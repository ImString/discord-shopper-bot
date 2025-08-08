package me.imstring.discordshopper.components;

import me.imstring.discordshopper.Core;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.entities.Guild;

public abstract class Component {
    public Container create(Core instance) {
        return null;
    }

    public Container create(Core instance, Guild guild) {
        return null;
    }
}
