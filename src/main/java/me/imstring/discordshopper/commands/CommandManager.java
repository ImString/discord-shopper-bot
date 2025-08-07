package me.imstring.discordshopper.commands;

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

    private static final Map<String, DiscordAbstractCommand> mainCommands = new HashMap<>();

    private final Core instance;

    public void register() {
        ClassPath classPath;
        try {
            classPath = ClassPath.from(getClass().getClassLoader());
        } catch (Exception e) {
            Logger.error(e);
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
                Logger.error(e);
            }
        }
    }

    public void loadSlashCommands(Guild guild) {
        List<SlashCommandData> commands = mainCommands.values().stream().map(cmd -> {
            SlashCommandData slash = Commands.slash(cmd.getName(), cmd.getDescription()).addOptions(cmd.getOptions());
            if (cmd.getDefaultPermissions() != null) {
                slash.setDefaultPermissions(DefaultMemberPermissions.enabledFor(cmd.getDefaultPermissions()));
            }
            return slash;
        }).toList();

        guild.updateCommands().addCommands(commands).queue(success -> {
            for (DiscordAbstractCommand command : mainCommands.values()) {
                Logger.info("Loaded slash command: " + command.getName() + " for guild " + guild.getName());
            }
        }, failure -> Logger.error("Failed to load slash commands for guild " + guild.getName() + ": " + failure.getMessage()));
    }

    public List<String> getCommandNames() {
        return mainCommands.keySet().stream().toList();
    }

    public DiscordAbstractCommand getCommand(String name) {
        return mainCommands.get(name);
    }
}
