package games.bevs.library.modules.worldoptions;

import games.bevs.library.modules.Module;
import games.bevs.library.modules.worldoptions.listeners.WorldListeners;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldModule extends Module
{
    @Getter
    private WorldOptionSettings settings;

    public WorldModule( JavaPlugin plugin, WorldOptionSettings settings)
    {
        super("World", plugin);
        this.settings = settings;

        this.registerListener(new WorldListeners(this));
    }


}
