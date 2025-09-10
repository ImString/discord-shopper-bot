package me.imstring.discordshopper.entities;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GuildSettings {
    private Integer id;

    private String guildId;

    // Channel's
    private String welcomeChannelId;
    private String verificationChannelId;
    private String logsChannelId;
    private String ticketsChannelId;
    private String cartChannelId;

    // Role's
    private String memberAutoRoleId;
    private String memberAuthenticationRoleId;

    public GuildSettings(String guildId) {
        this.id = null;
        this.guildId = guildId;
    }
}
