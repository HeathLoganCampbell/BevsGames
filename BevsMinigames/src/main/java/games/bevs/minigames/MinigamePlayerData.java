package games.bevs.minigames;

import games.bevs.library.commons.Rank;
import games.bevs.library.modules.playerdata.types.PlayerData;
import games.bevs.library.modules.playerdata.types.RankDuration;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity(noClassnameStored = true)
public class MinigamePlayerData extends PlayerData
{
    private String special = "Example";

    public MinigamePlayerData(UUID uniqueId)
    {
        super(uniqueId);
    }

    public MinigamePlayerData(UUID uniqueId, String username, Rank rank, List<RankDuration> rankHistory, String special)
    {
        super(uniqueId, username, rank, rankHistory);
        this.special = special;
    }
}
