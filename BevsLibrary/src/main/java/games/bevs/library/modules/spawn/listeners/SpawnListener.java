package games.bevs.library.modules.spawn.listeners;

import games.bevs.library.commons.ModuleListener;
import games.bevs.library.modules.spawn.SpawnModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListener extends ModuleListener<SpawnModule>
{
    public SpawnListener(SpawnModule module) {
        super(module);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e)
    {
        Player player = e.getPlayer();
        e.setRespawnLocation(this.getModule().getSpawn(player));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        player.teleport(this.getModule().getSpawn(player));
    }
}
