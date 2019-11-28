package games.bevs.hub;

import games.bevs.library.commons.Rank;
import games.bevs.library.modules.playerdata.types.PlayerData;
import games.bevs.library.modules.playerdata.types.RankDuration;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity(value = "PlayerData", noClassnameStored = true)
public class HubPlayerData extends PlayerData
{

    public HubPlayerData(UUID uniqueId)
    {
        super(uniqueId);
    }

    public HubPlayerData(UUID uniqueId, String username, Rank rank, List<RankDuration> rankHistory)
    {
        super(uniqueId, username, rank, rankHistory);
    }
}
