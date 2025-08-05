package me.imstring.discordshopper.commands;

import com.google.common.reflect.ClassPath;
import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.utils.Logger;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CommandManager {

    private static final Map<String, DiscordAbstractCommand> mainCommands = new HashMap<>();

    private final Core instance;

    public void register() {
        ClassPath classPath;
        try {
            classPath = ClassPath.from(getClass().getClassLoader());
        } catch (Exception e) {
            Logger.fatal(e);
            return;
        }

        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("me.imstring.discordshopper.commands.executors")) {
            try {
                Class<?> clazz = classInfo.load();
                if (DiscordAbstractCommand.class.isAssignableFrom(clazz) && !clazz.isInterface() && !clazz.isLocalClass()) {
                    DiscordAbstractCommand command = (DiscordAbstractCommand) clazz.getDeclaredConstructor().newInstance();
                    mainCommands.put(command.getName(), command);

                    Logger.info("Registered command: " + command.getName());
                }
            } catch (Exception e) {
                Logger.fatal(e);
            }
        }
    }

    public void loadSlashCommands(Guild guild) {
        for (DiscordAbstractCommand command : mainCommands.values()) {
            guild.updateCommands()
                    .addCommands(Commands.slash(command.getName(), command.getDescription()))
                    .queue();

            Logger.info("Loaded slash command: " + command.getName() + " for guild " + guild.getName());
        }
    }

    public List<String> getCommandNames() {
        return mainCommands.keySet().stream().toList();
    }

    public DiscordAbstractCommand getCommand(String name) {
        return mainCommands.get(name);
    }
}
