package me.imstring.discordshopper.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class GuildSettings {
    private int id;

    private String guildId;

    // Channel's
    private String welcomeChannelId;
    private String verificationChannelId;
    private String logsChannelId;
    private String ticketsChannelId;
    private String cartChannelId;

    // Role's
    private String memberAutoRoleId;

    public GuildSettings(int id, String guildId, String verificationChannelId, String welcomeChannelId, String logsChannelId, String ticketsChannelId, String cartChannelId) {
        this.id = id;
        this.guildId = guildId;
        this.verificationChannelId = verificationChannelId;
        this.welcomeChannelId = welcomeChannelId;
        this.logsChannelId = logsChannelId;
        this.ticketsChannelId = ticketsChannelId;
        this.cartChannelId = cartChannelId;
    }
}
