package games.bevs.library.modules.invisibility.events;

import games.bevs.library.commons.Rank;
import games.bevs.library.commons.event.PlayerEventBase;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class VisibilityChangeEvent extends PlayerEventBase
{
    private Rank from, to;

    public VisibilityChangeEvent(Player player, Rank from, Rank to)
    {
        super(player);
        this.from = from;
        this.to = to;
    }
}
