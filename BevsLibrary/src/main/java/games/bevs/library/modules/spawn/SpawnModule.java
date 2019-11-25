package games.bevs.library.modules.spawn;

import games.bevs.library.modules.Module;
import games.bevs.library.modules.spawn.types.SpawnBase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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
    @Getter
    @Setter
    private SpawnBase spawnBase;

    public SpawnModule(JavaPlugin plugin, SpawnBase spawnBase)
    {
        super("Spawn", plugin);
        this.spawnBase = spawnBase;
    }

    public Location getSpawn(Player player)
    {
       return spawnBase.getSpawn(player);
    }
}
