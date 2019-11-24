package games.bevs.library.modules.adminmode.listeners;

import games.bevs.library.modules.adminmode.AdminMode;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerListener implements Listener
{
    private AdminMode adminMode;

    //Player joins while staff is in the middle of being invis
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        this.adminMode.onDisconnected(player);
    }

    @EventHandler
    public void onKick(PlayerKickEvent e)
    {
        Player player = e.getPlayer();
        this.adminMode.onDisconnected(player);
    }
}
