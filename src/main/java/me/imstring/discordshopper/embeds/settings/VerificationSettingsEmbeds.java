package me.imstring.discordshopper.embeds.settings;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.configurations.ApplicationConfig;
import me.imstring.discordshopper.embeds.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

public class VerificationSettingsEmbeds extends Embed {

    @Override
    public EmbedBuilder create(Core instance, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("SISTEMA DE VERIFICAÇÃO");
        embed.setDescription("Para ter acesso total ao nosso servidor no Discord e aproveitar todos os recursos disponíveis, é preciso verificar sua conta.");
        embed.addField("❓ - Como faço isso?", "Simples! Basta clicar no botão \"Verifique-se\" abaixo e seguir as instruções.", false);
        embed.addField("\uD83E\uDD28 - E depois?", "Pronto! Você já vai ter acesso a todo nosso servidor!", false);
        embed.setFooter("© " + ApplicationConfig.NAME + " • Todos os direitos reservados");
        embed.setColor(instance.getEmbedDefaultColor());
        if (guild != null) embed.setThumbnail(guild.getIconUrl());

        return embed;
    }

}
