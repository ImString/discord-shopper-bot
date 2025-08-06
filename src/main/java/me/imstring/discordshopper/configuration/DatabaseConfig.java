package me.imstring.discordshopper.configuration;

import me.imstring.discordshopper.database.DatabaseType;
import me.imstring.discordshopper.utils.ConfigurationOption;

public class DatabaseConfig {
    @ConfigurationOption("DB_TYPE")
    public static DatabaseType TYPE = DatabaseType.SQLITE;

    @ConfigurationOption("DB_HOST")
    public static String HOST = "localhost";

    @ConfigurationOption("DB_PORT")
    public static int PORT = 3306;

    @ConfigurationOption("DB_DATABASE")
    public static String DATABASE = "discordshopper";

    @ConfigurationOption("DB_USERNAME")
    public static String USERNAME = "root";

    @ConfigurationOption("DB_PASSWORD")
    public static String PASSWORD = "password";
}
