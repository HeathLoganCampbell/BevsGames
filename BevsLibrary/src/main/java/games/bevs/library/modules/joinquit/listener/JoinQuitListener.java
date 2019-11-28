package games.bevs.library.modules.joinquit.listener;

import games.bevs.library.modules.joinquit.JoinQuit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class JoinQuitListener implements Listener
{
    @Getter(AccessLevel.PRIVATE)
    private JoinQuit joinQuit;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        if(player == null) return;
        this.joinQuit.join(player);
        e.setJoinMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        this.joinQuit.quit(player);
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onKick(PlayerKickEvent e)
    {
        Player player = e.getPlayer();
        this.joinQuit.quit(player);
    }
}
