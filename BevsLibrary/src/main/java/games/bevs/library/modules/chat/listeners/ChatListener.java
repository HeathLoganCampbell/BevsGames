package games.bevs.library.modules.chat.listeners;

import games.bevs.library.commons.ModuleListener;
import games.bevs.library.modules.chat.ChatModule;
import games.bevs.library.modules.chat.ChatSetting;
import games.bevs.library.modules.playerdata.PlayerDataHandler;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends ModuleListener<ChatModule>
{
    private PlayerDataHandler playerDataHandler;
    public ChatListener(ChatModule module, PlayerDataHandler playerDataHandler)
    {
        super(module);
        this.playerDataHandler = playerDataHandler;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        if(e.isCancelled()) return;
        e.setCancelled(true);
        Player player = e.getPlayer();
        String message = e.getMessage();
        ChatSetting settings = this.getModule().getChatSetting();
        TextComponent textComponet = settings.generateNewMessage(message, player, this.playerDataHandler.getPlayerDataManager().getPlayerData(player));

        for (Player recipient : e.getRecipients())
        {
            recipient.spigot().sendMessage(textComponet);
        }
    }
}
