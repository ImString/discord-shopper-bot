package me.imstring.discordshopper.handlers.interactions.executors.buttons;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.entities.GuildSettings;
import me.imstring.discordshopper.handlers.interactions.DiscordInteraction;
import me.imstring.discordshopper.handlers.interactions.DiscordInteractionOptions;
import me.imstring.discordshopper.repositories.GuildSettingsRepository;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionType;

import java.util.List;
import java.util.Optional;

public class VerificationButton extends DiscordInteraction {

    public VerificationButton() {
        super("verification_button", new DiscordInteractionOptions(
                List.of("verify-account"),
                InteractionType.COMPONENT
        ));
    }

    @Override
    public void exec(Object data, String interactionId, Core instance) {
        ButtonInteractionEvent event = (ButtonInteractionEvent) data;
        Guild guild = event.getGuild();
        Member member = event.getMember();

        if (guild == null || member == null) return;

        Optional<GuildSettings> optSettings = new GuildSettingsRepository(instance).findByGuildId(guild.getId());
        if (optSettings.isEmpty()) return;

        GuildSettings settings = optSettings.get();
        String authRoleId = settings.getMemberAuthenticationRoleId();

        if (authRoleId == null || authRoleId.isBlank()) {
            reply(event, "❌ Tivemos um problema para registrar você, contate um administrador!");
            return;
        }

        Role authRole = guild.getRoleById(authRoleId);
        if (authRole == null) return;

        if (member.getRoles().contains(authRole)) {
            reply(event, "⚠️ Você já está cadastrado no servidor!");
            return;
        }

        if (!guild.getSelfMember().canInteract(authRole)) {
            reply(event, "⚠️ O cargo de autenticação está acima do cargo do bot na hierarquia. Contate um administrador!");
            return;
        }

        Role autoRole = guild.getRoleById(settings.getMemberAutoRoleId());
        if (autoRole != null) {
            guild.removeRoleFromMember(member, autoRole).queue(
                    s -> addRoleWithReply(guild, member, authRole, event),
                    f -> reply(event, "❌ Falha ao remover cargo temporário!")
            );
            return;
        }

        addRoleWithReply(guild, member, authRole, event);
    }

    private void addRoleWithReply(Guild guild, Member member, Role authRole, ButtonInteractionEvent event) {
        guild.addRoleToMember(member, authRole).queue(
                s -> reply(event, "✅ Registrado com sucesso!"),
                f -> reply(event, "❌ Tivemos um problema para registrar você! Tente novamente!")
        );
    }

    private void reply(ButtonInteractionEvent event, String message) {
        event.reply(message).setEphemeral(true).queue();
    }
}