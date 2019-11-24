package games.bevs.library.modules.fentity.listener;

import games.bevs.library.modules.fentity.EntityEngine;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@AllArgsConstructor
public class PlayerListener implements Listener
{
    private EntityEngine entityEngine;

    //Just for testing the entity spawns correctly
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
//        this.entityEngine.spawnFor(player);
    }
}
