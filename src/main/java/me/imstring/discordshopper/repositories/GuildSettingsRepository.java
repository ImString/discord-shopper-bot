package me.imstring.discordshopper.repositories;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.database.Database;
import me.imstring.discordshopper.entities.GuildSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GuildSettingsRepository implements Repository<GuildSettings> {

    private final Core instance;

    private static final String SELECT_COLUMNS = "id, guild_id, welcome_channel, logs_channel, tickets_category, member_role, authentication_role";
    private static final String SELECT_BY_ID_SQL = "SELECT " + SELECT_COLUMNS + " FROM guild_settings WHERE id = ?";
    private static final String SELECT_BY_GUILD_ID_SQL = "SELECT " + SELECT_COLUMNS + " FROM guild_settings WHERE guild_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT " + SELECT_COLUMNS + " FROM guild_settings";

    @Override
    public void save(GuildSettings entity) throws SQLException {
        instance.getDatabase().update(
                "INSERT INTO guild_settings (guild_id, welcome_channel, logs_channel, tickets_category, member_role, authentication_role) VALUES (?, ?, ?, ?, ?, ?)",
                entity.getGuildId(),
                entity.getWelcomeChannelId(),
                entity.getLogsChannelId(),
                entity.getTicketsCategoryId(),
                entity.getMemberAutoRoleId(),
                entity.getMemberAuthenticationRoleId()
        );
    }

    @Override
    public void update(GuildSettings entity) throws SQLException {
        instance.getDatabase().update(
                "UPDATE guild_settings SET welcome_channel = ?, logs_channel = ?, tickets_category = ?, member_role = ?, authentication_role = ? WHERE guild_id = ?",
                entity.getWelcomeChannelId(),
                entity.getLogsChannelId(),
                entity.getTicketsCategoryId(),
                entity.getMemberAutoRoleId(),
                entity.getMemberAuthenticationRoleId(),
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
    public Optional<GuildSettings> findById(String id) {
        try (Database.Query query = instance.getDatabase().query(SELECT_BY_ID_SQL, id)) {
            if (query.resultSet.next()) {
                return Optional.of(mapResultSet(query.resultSet));
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<GuildSettings> findByGuildId(String guildId) {
        try (Database.Query query = instance.getDatabase().query(SELECT_BY_GUILD_ID_SQL, guildId)) {
            if (query.resultSet.next()) {
                return Optional.of(mapResultSet(query.resultSet));
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<GuildSettings> findAll() throws SQLException {
        List<GuildSettings> list = new ArrayList<>();
        try (Database.Query query = instance.getDatabase().query(SELECT_ALL_SQL)) {
            while (query.resultSet.next()) {
                list.add(mapResultSet(query.resultSet));
            }
        }
        return list;
    }

    @Override
    public void registerTable() throws SQLException {
        instance.getDatabase().update("CREATE TABLE IF NOT EXISTS guild_settings (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "guild_id VARCHAR(20) NULL, " +
                "welcome_channel VARCHAR(20) NULL, " +
                "logs_channel VARCHAR(20) NULL, " +
                "tickets_category VARCHAR(20) NULL, " +
                "member_role VARCHAR(20) NULL, " +
                "authentication_role VARCHAR(20) NULL" +
                ");"
        );
    }

    private GuildSettings mapResultSet(ResultSet rs) throws SQLException {
        return new GuildSettings(
                rs.getInt("id"),
                rs.getString("guild_id"),
                rs.getString("welcome_channel"),
                rs.getString("logs_channel"),
                rs.getString("tickets_category"),
                rs.getString("member_role"),
                rs.getString("authentication_role")
        );
    }
}
