package me.imstring.discordshopper.commands.executors.staff;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.commands.DiscordAbstractCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class PingCommand extends DiscordAbstractCommand {
    public PingCommand() {
        super("ping", "Verifique se o bot está online.", new ArrayList<>(Collections.singleton(Permission.ADMINISTRATOR)));
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event, Core instance) {
        long start = System.currentTimeMillis();
        event.reply("Ping...").queue(success -> {
            long ping = System.currentTimeMillis() - start;
            success.editOriginal("Pong \uD83C\uDFD3! O ping é: ``" + ping + "ms`` | WebSocket: ``" + event.getJDA().getGatewayPing() + "ms``").queue();
        }, failure -> {
            event.reply("Ocorreu um erro ao calcular o ping.").setEphemeral(true).queue();
        });
    }
}
