package games.bevs.library.modules.worldoptions.listeners;

import games.bevs.library.commons.ModuleListener;
import games.bevs.library.modules.worldoptions.WorldModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
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
}
