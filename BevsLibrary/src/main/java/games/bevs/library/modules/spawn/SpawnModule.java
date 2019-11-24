package games.bevs.library.modules.spawn;

import games.bevs.library.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Allows you to
 * - set Spawn point (multiple)
 * - gamemode of the player
 * - Greeting message
 * - Default items
 */
public class SpawnModule extends Module
{
    public SpawnModule(JavaPlugin plugin)
    {
        super("Spawn", plugin);
    }
}
