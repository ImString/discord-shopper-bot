package me.imstring.discordshopper.handlers.listeners.executors;

import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.entities.GuildSettings;
import me.imstring.discordshopper.repositories.GuildSettingsRepository;
import me.imstring.discordshopper.utils.Logger;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Optional;

@RequiredArgsConstructor
public class AutoRoleListener extends ListenerAdapter {
    private final Core instance;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();

        GuildSettingsRepository repository = new GuildSettingsRepository(instance);
        Optional<GuildSettings> guildSettingsOpt = repository.findByGuildId(guild.getId());

        if (guildSettingsOpt.isEmpty()) {
            return;
        }

        GuildSettings guildSettings = guildSettingsOpt.get();
        String autoRoleId = guildSettings.getMemberAutoRoleId();

        if (autoRoleId == null || autoRoleId.isEmpty()) {
            return;
        }

        Role autoRole = guild.getRoleById(autoRoleId);
        if (autoRole == null) {
            return;
        }

        if (!guild.getSelfMember().canInteract(autoRole)) {
            Logger.error("Could not add role with ID \"" + autoRoleId + "\" on guild \"" + guild.getId() + "\" because the bot does not have permission or the role is above it");
            return;
        }

        if (member.getRoles().contains(autoRole)) {
            return;
        }

        guild.addRoleToMember(member, autoRole).queue();
    }
}
