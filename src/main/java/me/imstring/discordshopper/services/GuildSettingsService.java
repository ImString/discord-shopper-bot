package me.imstring.discordshopper.services;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.database.Database;
import me.imstring.discordshopper.entities.GuildSettings;

import java.sql.SQLException;

@RequiredArgsConstructor
public class GuildSettingsService {

    private final Core instance;

    public GuildSettings findByGuildId(String guildId) throws SQLException {
        Database.Query query = instance.getDatabase().query(
                "SELECT id, guild_id, verification_channel, welcome_channel, logs_channel, tickets_channel, cart_channel FROM guild_settings WHERE guild_id = ?",
                guildId
        );

        if (!query.resultSet.next()) {
            return null;
        }

        return new GuildSettings(
                query.resultSet.getInt("id"),
                query.resultSet.getString("guild_id"),
                query.resultSet.getString("verification_channel"),
                query.resultSet.getString("welcome_channel"),
                query.resultSet.getString("logs_channel"),
                query.resultSet.getString("tickets_channel"),
                query.resultSet.getString("cart_channel")
        );
    }

    public void registerTable() throws SQLException {
        instance.getDatabase().update("CREATE TABLE IF NOT EXISTS guild_settings (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "guild_id VARCHAR(20), " +
                "verification_channel VARCHAR(20), " +
                "welcome_channel VARCHAR(20), " +
                "logs_channel VARCHAR(20), " +
                "tickets_channel VARCHAR(20), " +
                "cart_channel VARCHAR(20)" +
                ");"
        );
    }
}
