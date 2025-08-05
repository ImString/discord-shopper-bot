package me.imstring.discordshopper.commands.executors;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.commands.DiscordAbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends DiscordAbstractCommand {
    public PingCommand() {
        super("ping", "Verifique se o bot está online.");
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event, Core instance) {
        long start = System.currentTimeMillis();
        event.reply("Ping...").queue(success -> {
            long ping = System.currentTimeMillis() - start;
            success.editOriginal("Pong! O ping é: " + ping + "ms | websocket: " + event.getJDA().getGatewayPing() + "ms").queue();
        }, failure -> {
            event.reply("Ocorreu um erro ao calcular o ping.").setEphemeral(true).queue();
        });
    }
}
