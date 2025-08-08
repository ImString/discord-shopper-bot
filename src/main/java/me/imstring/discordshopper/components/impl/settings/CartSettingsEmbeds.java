package me.imstring.discordshopper.components.impl.settings;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.components.Component;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.components.separator.Separator;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.components.container.Container;

public class CartSettingsEmbeds extends Component {

    public Container create(Core instance, Guild guild) {
        return Container.of(
                TextDisplay.of(String.join("\n",
                        "# \uD83D\uDED2 Monte seu pedido aqui!",
                        "",
                        "### ✨ Praticidade e Segurança do início ao fim",
                        "",
                        "Navegue pelos nossos produtos e adicione tudo o que precisa ao seu carrinho com calma.",
                        "Seu canal de pedido é exclusivo e seguro.",
                        "",
                        "**\uD83D\uDCCC Clique no botão abaixo para começar!**"
                )),
                Separator.createDivider(Separator.Spacing.LARGE),
                ActionRow.of(
                        Button.of(ButtonStyle.SECONDARY, "manage-cart", "\uD83D\uDECD\uFE0F Abrir Meu Carrinho")
                )
        ).withAccentColor(instance.getEmbedDefaultColor());
    }

}
