package me.imstring.discordshopper.components.impl.settings;

import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.components.Component;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.separator.Separator;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.entities.Guild;

public class VerificationSettingsComponent extends Component {

    public Container create(Core instance, Guild guild) {
        return Container.of(
                TextDisplay.of(String.join("\n",
                        "## ✅ Verifique sua conta",
                        "",
                        "### Para ter acesso a todos os nossos canais, você precisa se validar.",
                        "",
                        "É só um clique! Ao fazer isso, você confirma que leu e concorda com as regras do servidor.",
                        "",
                        "**Clique no botão abaixo para receber o cargo e liberar seu acesso!**"
                )),
                Separator.createDivider(Separator.Spacing.LARGE),
                ActionRow.of(
                        Button.of(ButtonStyle.SUCCESS, "verify-account", "✅ Verifique-se")
                )
        );
    }

}
