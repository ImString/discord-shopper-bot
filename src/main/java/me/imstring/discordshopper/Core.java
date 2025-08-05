package me.imstring.discordshopper;

import lombok.Getter;
import me.imstring.discordshopper.commands.CommandManager;
import me.imstring.discordshopper.configuration.DiscordConfig;
import me.imstring.discordshopper.listeners.ListenerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Core {

    private @Getter CommandManager commandManager;
    private @Getter ListenerManager listenerManager;

    private @Getter JDA jda;
    private @Getter JDABuilder jdaBuilder;

    public void onLoad() {
        jdaBuilder = JDABuilder.createDefault(DiscordConfig.TOKEN);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT);
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        jdaBuilder.setChunkingFilter(ChunkingFilter.ALL);
        jdaBuilder.enableCache(CacheFlag.ONLINE_STATUS);

        listenerManager = new ListenerManager(this);
        listenerManager.register();

        commandManager = new CommandManager(this);
        commandManager.register();

        jda = jdaBuilder.build();
    }

}