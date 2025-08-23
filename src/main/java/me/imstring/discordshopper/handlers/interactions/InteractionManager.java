package me.imstring.discordshopper.handlers.interactions;

import com.google.common.reflect.ClassPath;
import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.utils.Logger;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InteractionManager {

    private final Core instance;
    private final List<DiscordInteraction> interactions = new ArrayList<>();

    public void registerAll() {
        Logger.info("Registering interactions...");

        ClassPath classPath;
        try {
            classPath = ClassPath.from(getClass().getClassLoader());
        } catch (Exception e) {
            Logger.error(e);
            return;
        }

        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("me.imstring.discordshopper.handlers.interactions.executors")) {
            try {
                Class<?> clazz = classInfo.load();
                if (DiscordInteraction.class.isAssignableFrom(clazz) && !clazz.isInterface() && !clazz.isLocalClass()) {
                    DiscordInteraction interaction = (DiscordInteraction) clazz.getDeclaredConstructor().newInstance();
                    interactions.add(interaction);

                    Logger.info("Registered interaction: " + clazz.getSimpleName());
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    public void handleInteraction(Object data, String identifier) {
        boolean found = false;
        for (DiscordInteraction interaction : interactions) {
            if (interaction.getOptions().getCustomIds().stream().anyMatch(identifier::matches)) {
                found = true;
                interaction.exec(data, identifier);
                break;
            }
        }

        if (found) return;

        Logger.warn("No interaction found for identifier: " + identifier);
    }
}
