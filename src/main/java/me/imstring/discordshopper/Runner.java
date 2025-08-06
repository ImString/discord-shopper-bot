package me.imstring.discordshopper;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import me.imstring.discordshopper.configuration.DatabaseConfig;
import me.imstring.discordshopper.configuration.DiscordConfig;
import me.imstring.discordshopper.utils.ConfigurationLoader;

public class Runner {
    public static @Getter Core core;
    public static Dotenv dotenv = Dotenv.load();

    public static void main(String[] args) {
        ConfigurationLoader.loadConfig(DiscordConfig.class);
        ConfigurationLoader.loadConfig(DatabaseConfig.class);

        core = new Core();
        core.onLoad();
    }
}
