package games.bevs.library.modules.disguises.listener;

import games.bevs.library.modules.disguises.DisguiseManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@RequiredArgsConstructor
public class UnloadEntityListener implements Listener
{
    @NonNull
    private DisguiseManager disguiseManager;

//    @EventHandler
//    public void onEntityDeath(EntityDeathEvent e)
//    {
//        Entity entity = e.getEntity();
//        //Handled by PlayerDeathEvent
//        if(entity instanceof Player) return;
////        this.disguiseManager.clearDisguise(Bukkit.getOnlinePlayers(), entity);
//    }
}
