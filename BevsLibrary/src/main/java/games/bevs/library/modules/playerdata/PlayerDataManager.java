package games.bevs.library.modules.playerdata;


import games.bevs.library.modules.playerdata.types.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager<P extends PlayerData>
{
    private HashMap<UUID, P> playerDatas;

    public void registerPlayerData(P playerData)
    {
        this.playerDatas.put(playerData.getUniqueId(), playerData);
    }

    public void unregisterPlayerData(UUID uniqueId)
    {
        this.playerDatas.remove(uniqueId);
    }

    public P getPlayerData(UUID uniqueId)
    {
        return this.playerDatas.get(uniqueId);
    }
}
