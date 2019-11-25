package games.bevs.library.modules.spawn.types;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class SpawnBase
{
    public abstract Location getSpawn(Player player);
}
