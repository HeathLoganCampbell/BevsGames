package games.bevs.library.modules.playerdata;

import games.bevs.library.commons.utils.Console;
import games.bevs.library.modules.database.Database;
import games.bevs.library.modules.playerdata.listeners.PlayerDataListener;
import games.bevs.library.modules.playerdata.types.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.UUID;

public class PlayerDataHandler<P extends PlayerData>
{
    @Getter
    private PlayerDataManager<P> playerDataManager;
    private Database database;
    private BasicDAO<P, UUID> daoAccessor;

    public PlayerDataHandler(JavaPlugin plugin, Class<P> clazz, Database database)
    {
        this.playerDataManager = new PlayerDataManager<P>();
        this.database = database;

        this.database.map(clazz);
        this.daoAccessor = new BasicDAO<P, UUID>(clazz, this.database.getDatastore());

        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this), plugin);
    }

    public void connnect(String name, UUID uniqueId)
    {
        P playerData = this.daoAccessor.get(uniqueId);;
        //load from database
        this.playerDataManager.registerPlayerData(playerData);
        playerData.setLoaded(true);

        Player player = Bukkit.getPlayer(playerData.getUniqueId());
        if(player != null)
        {
            player.setPlayerListName(playerData.getRank().getTagColor() + player.getName());
        }

        Console.log("PlayerData", "Player " + name + " has connected");
    }

    public void disconnnect(String name, UUID uniqueId)
    {
        P playerData = this.playerDataManager.getPlayerData(uniqueId);

        //save from database;
        this.daoAccessor.save(playerData);
        this.playerDataManager.unregisterPlayerData(uniqueId);

        Console.log("PlayerData", "Player " + name + " has disconnected");
    }
}
