package games.bevs.library.modules.spawn.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class PersonalSpawn extends SpawnBase
{
    @Getter
    private Location fallBackLocation;
    @Getter
    private HashMap<UUID, Location> personalSpawns = new HashMap<>();

    public void setPersonalSpawn(Player player, Location location)
    {
        this.setPersonalSpawn(player.getUniqueId(), location);
    }

    public void setPersonalSpawn(UUID uniqueId, Location location)
    {
        this.getPersonalSpawns().put(uniqueId, location);
    }

    public Location getPersonalSpawn(Player player)
    {
        return this.getPersonalSpawn(player.getUniqueId());
    }

    public Location getPersonalSpawn(UUID uniqueId)
    {
        return this.getPersonalSpawns().get(uniqueId);
    }

    /**
     * Used when a player leaves the server, so we don't have a
     * memory leak, it's not required but it's just nice
     * @param player
     */
    public void removePersonalSpawn(Player player)
    {
        this.removePersonalSpawn(player);
    }

    public void removePersonalSpawn(UUID uniqueId)
    {
        this.getPersonalSpawns().remove(uniqueId);
    }

    @Override
    public Location getSpawn(Player player)
    {
        return this.getSpawn(player);
    }
}
