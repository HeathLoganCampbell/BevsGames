package games.bevs.library.modules.worldoptions.listeners;

import games.bevs.library.commons.ModuleListener;
import games.bevs.library.modules.worldoptions.WorldModule;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListeners extends ModuleListener<WorldModule>
{
    public WorldListeners(WorldModule module)
    {
        super(module);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e)
    {
        if(!this.getModule().getSettings().isEnableWeather())
            if(e.toWeatherState())
                e.setCancelled(true);
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent e)
    {
        if(!this.getModule().getSettings().isEnableDecay())
            e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(!this.getModule().getSettings().isEnableBlockBreak())
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(!this.getModule().getSettings().isEnableBlockPlace())
            e.setCancelled(true);
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        Block block = e.getClickedBlock();
        if(block == null) return;
        if(block.getType() == Material.AIR) return;
        if(!(block.getType() == Material.CHEST
                || block.getType() == Material.TRAPPED_CHEST
                || block.getType() == Material.FURNACE))
            return;
        if(!this.getModule().getSettings().isEnableBlockInteraction())
            e.setCancelled(true);
    }

    @EventHandler
    public void onFoodlevel(FoodLevelChangeEvent e)
    {
        Player player = (Player) e.getEntity();
        if(!this.getModule().getSettings().isEnableHunger())
            e.setFoodLevel(20);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        if(!(e.getEntity() instanceof Player))
            return;
        Player player = (Player) e.getEntity();
        if(!this.getModule().getSettings().isEnablePlayerDamage())
            e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(!this.getModule().getSettings().isEnableDropItems())
            e.setCancelled(true);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(!this.getModule().getSettings().isEnablePickUpItems())
            e.setCancelled(true);
    }
}
