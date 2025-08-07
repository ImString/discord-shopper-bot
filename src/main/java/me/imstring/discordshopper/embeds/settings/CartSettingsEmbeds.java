package me.imstring.discordshopper.embeds.settings;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.configurations.ApplicationConfig;
import me.imstring.discordshopper.embeds.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

public class CartSettingsEmbeds extends Embed {

    @Override
    public EmbedBuilder create(Core instance, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("\uD83D\uDECD\uFE0F Monte seu carrinho com facilidade!");
        embed.setDescription("Pronto para fazer seu pedido? Clique no botão abaixo para começar! Um canal exclusivo será criado para você selecionar os produtos que deseja comprar com tranquilidade.\n" +
                "\n" +
                "*Seu carrinho será criado automaticamente. Simples, rápido e direto!*");
        embed.setFooter("© " + ApplicationConfig.NAME + " • Todos os direitos reservados");
        embed.setColor(instance.getEmbedDefaultColor());
        if (guild != null) embed.setThumbnail(guild.getIconUrl());

        return embed;
    }

}
