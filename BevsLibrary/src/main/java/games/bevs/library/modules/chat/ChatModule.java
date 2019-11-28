package games.bevs.library.modules.chat;

import games.bevs.library.modules.Module;
import games.bevs.library.modules.chat.listeners.ChatListener;
import games.bevs.library.modules.playerdata.PlayerDataHandler;
import games.bevs.library.modules.playerdata.types.PlayerData;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatModule extends Module
{
    @Getter
    private ChatSetting chatSetting;
    @Getter
    private PlayerDataHandler playerDataHandler;

    public ChatModule(JavaPlugin plugin, ChatSetting chatSetting, PlayerDataHandler playerDataHandler)
    {
        super("Chat", plugin);

        this.chatSetting = chatSetting;

        this.registerListener(new ChatListener(this, playerDataHandler));
    }


}
