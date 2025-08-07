package me.imstring.discordshopper;

import lombok.Getter;
import me.imstring.discordshopper.commands.CommandManager;
import me.imstring.discordshopper.configurations.ApplicationConfig;
import me.imstring.discordshopper.configurations.DatabaseConfig;
import me.imstring.discordshopper.configurations.DiscordConfig;
import me.imstring.discordshopper.database.Database;
import me.imstring.discordshopper.database.DatabaseType;
import me.imstring.discordshopper.database.providers.HikariMySQL;
import me.imstring.discordshopper.database.providers.MySQL;
import me.imstring.discordshopper.database.providers.SQLite;
import me.imstring.discordshopper.listeners.ListenerManager;
import me.imstring.discordshopper.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.File;
import java.sql.SQLException;
import java.awt.Color;

public class Core {

    private @Getter CommandManager commandManager;
    private @Getter ListenerManager listenerManager;
    public @Getter Color embedDefaultColor = Color.decode(ApplicationConfig.COLOR);

    private @Getter JDA jda;
    private @Getter JDABuilder jdaBuilder;

    private @Getter Database database;

    public void onLoad() {
        if (!setupDatabase()) {
            Logger.error("Failed to connect to the database. Please check your configuration.");
            Logger.error("Exiting the application...");
            System.exit(1);
            return;
        }

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

    private boolean setupDatabase() {
        if (DatabaseConfig.TYPE == DatabaseType.SQLITE) {
            File databaseFile = new File("database.db");
            if (!databaseFile.exists()) {
                try {
                    if (!databaseFile.createNewFile()) {
                        Logger.error("Failed to create database file: " + databaseFile.getAbsolutePath());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.error("Failed to create database file: " + databaseFile.getAbsolutePath());
                    return false;
                }
            }

            database = new SQLite(databaseFile);
        } else if (DatabaseConfig.TYPE == DatabaseType.MYSQL) {
            database = new MySQL(
                    DatabaseConfig.HOST,
                    DatabaseConfig.PORT,
                    DatabaseConfig.DATABASE,
                    DatabaseConfig.USERNAME,
                    DatabaseConfig.PASSWORD
            );
        } else if (DatabaseConfig.TYPE == DatabaseType.HIKARI) {
            database = new HikariMySQL(
                    DatabaseConfig.HOST,
                    DatabaseConfig.PORT,
                    DatabaseConfig.DATABASE,
                    DatabaseConfig.USERNAME,
                    DatabaseConfig.PASSWORD
            );
        } else {
            Logger.error("Unsupported database type: " + DatabaseConfig.TYPE);
            return false;
        }

        try {
            database.openConnection();
            Logger.info("Successfully connected to the database.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.error("Failed to connect to the database: " + e.getMessage());
            return false;
        }
    }
}