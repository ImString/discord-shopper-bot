package me.imstring.discordshopper.repositories;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.database.Database;
import me.imstring.discordshopper.entities.GuildSettings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class GuildSettingsRepository implements Repository<GuildSettings> {

    private final Core instance;

    @Override
    public void save(GuildSettings entity) throws SQLException {
        instance.getDatabase().update(
                "INSERT INTO guild_settings (guild_id, verification_channel, welcome_channel, logs_channel, tickets_channel, cart_channel) VALUES (?, ?, ?, ?, ?, ?)",
                entity.getGuildId(),
                entity.getVerificationChannelId(),
                entity.getWelcomeChannelId(),
                entity.getLogsChannelId(),
                entity.getTicketsChannelId(),
                entity.getCartChannelId()
        );
    }

    @Override
    public void update(GuildSettings entity) throws SQLException {
        instance.getDatabase().update(
                "UPDATE guild_settings SET verification_channel = ?, welcome_channel = ?, logs_channel = ?, tickets_channel = ?, cart_channel = ? WHERE guild_id = ?",
                entity.getVerificationChannelId(),
                entity.getWelcomeChannelId(),
                entity.getLogsChannelId(),
                entity.getTicketsChannelId(),
                entity.getCartChannelId(),
                entity.getGuildId()
        );
    }

    @Override
    public void delete(GuildSettings entity) throws SQLException {
        instance.getDatabase().update(
                "DELETE FROM guild_settings WHERE guild_id = ?",
                entity.getGuildId()
        );
    }

    @Override
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

    @Override
    public GuildSettings findById(String id) throws SQLException {
        Database.Query query = instance.getDatabase().query(
                "SELECT id, guild_id, verification_channel, welcome_channel, logs_channel, tickets_channel, cart_channel FROM guild_settings WHERE id = ?",
                id
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

    @Override
    public Iterator<GuildSettings> findAll() throws SQLException {
        Database.Query query = instance.getDatabase().query(
                "SELECT id, guild_id, verification_channel, welcome_channel, logs_channel, tickets_channel, cart_channel FROM guild_settings"
        );

        List<GuildSettings> list = new ArrayList<>();
        while (query.resultSet.next()) {
            list.add(new GuildSettings(
                    query.resultSet.getInt("id"),
                    query.resultSet.getString("guild_id"),
                    query.resultSet.getString("verification_channel"),
                    query.resultSet.getString("welcome_channel"),
                    query.resultSet.getString("logs_channel"),
                    query.resultSet.getString("tickets_channel"),
                    query.resultSet.getString("cart_channel")
            ));
        }

        return list.iterator();
    }

}
