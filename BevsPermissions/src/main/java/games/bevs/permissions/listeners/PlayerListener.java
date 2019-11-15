package games.bevs.permissions.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{


    @EventHandler
    public void onPreAsyncLogin(AsyncPlayerPreLoginEvent e)
    {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player =  e.getPlayer();
//        player.addAttachment(this, "w.w", true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {

    }
}
