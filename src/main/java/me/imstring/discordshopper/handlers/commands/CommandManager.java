package me.imstring.discordshopper.handlers.commands;

import com.google.common.reflect.ClassPath;
import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.utils.Logger;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CommandManager {

    private static final Map<String, DiscordCommand> mainCommands = new HashMap<>();

    private final Core instance;

    public void registerAll() {
        Logger.info("Registering commands...");

        ClassPath classPath;
        try {
            classPath = ClassPath.from(getClass().getClassLoader());
        } catch (Exception e) {
            Logger.error(e);
            return;
        }

        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(this.getClass().getPackageName() + ".executors")) {
            try {
                Class<?> clazz = classInfo.load();
                if (DiscordCommand.class.isAssignableFrom(clazz) && !clazz.isInterface() && !clazz.isLocalClass()) {
                    DiscordCommand command = (DiscordCommand) clazz.getDeclaredConstructor().newInstance();
                    mainCommands.put(command.getName(), command);

                    Logger.info("Registered command: " + command.getName());
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    public void loadSlashCommands(Guild guild, List<String> commandNamesToLoad) {
        List<SlashCommandData> commands = mainCommands.entrySet().stream()
                .filter(entry -> commandNamesToLoad.contains(entry.getKey()))
                .map(entry -> {
                    DiscordCommand cmd = entry.getValue();
                    SlashCommandData slash = Commands.slash(cmd.getName(), cmd.getDescription())
                            .addOptions(cmd.getOptions());
                    if (cmd.getDefaultPermissions() != null) {
                        slash.setDefaultPermissions(DefaultMemberPermissions.enabledFor(cmd.getDefaultPermissions()));
                    }
                    return slash;
                })
                .toList();

        guild.updateCommands().addCommands(commands).queue(success -> {
            for (String cmdName : commandNamesToLoad) {
                Logger.info("Loaded slash command: \"" + cmdName + "\" for guild \"" + guild.getName() + "\"");
            }
            Logger.info("Finish load commands in guild \"" + guild.getName() + "\"");
        }, failure -> Logger.error("Failed to load slash commands for guild " + guild.getName() + ": " + failure.getMessage()));
    }

    public List<String> getCommandNames() {
        return mainCommands.keySet().stream().toList();
    }

    public DiscordCommand getCommand(String name) {
        return mainCommands.get(name);
    }
}
