package games.bevs.library.modules.chat;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.modules.playerdata.types.PlayerData;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.awt.*;
import net.md_5.bungee.api.chat.*;

public class ChatSetting
{
    @Getter
    private String chatFormat = "%1s" + CC.white + ": %2s";

    public TextComponent generateNewMessage(String oldMessage, Player sender, PlayerData playerdata)
    {
        TextComponent usernameMsg = new TextComponent(playerdata.getRank().getTagColor() + sender.getName() +  CC.gray + ": ");

        TextComponent message = new TextComponent((playerdata.getRank() == Rank.NORMAL ?  CC.gray : CC.white ) + oldMessage);

        usernameMsg.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/msg " + sender.getName() + " ") );
        StringBuilder strBuilder = new StringBuilder();
        //HOVER
        strBuilder.append(CC.yellow).append(playerdata.getUsername()).append("\n");
        strBuilder.append(playerdata.getRank().getColouredDisplayName()).append("\n");
        strBuilder.append("\n");
        strBuilder.append(CC.gray).append("Click to message");

        usernameMsg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(strBuilder.toString()).create() ) );
        usernameMsg.addExtra(message);

        return usernameMsg;
    }
}
