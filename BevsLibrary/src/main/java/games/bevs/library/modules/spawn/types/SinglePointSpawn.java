package games.bevs.library.modules.spawn.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@NoArgsConstructor
@AllArgsConstructor
public class SinglePointSpawn extends SpawnBase
{
    @Getter
    @Setter
    private Location spawn;

    @Override
    public Location getSpawn(Player player)
    {
        return spawn;
    }
}
