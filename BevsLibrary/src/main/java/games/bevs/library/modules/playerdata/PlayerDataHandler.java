package games.bevs.library.modules.playerdata;

import games.bevs.library.commons.Console;
import games.bevs.library.modules.database.Database;
import games.bevs.library.modules.playerdata.listeners.PlayerDataListener;
import games.bevs.library.modules.playerdata.types.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.dao.BasicDAO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * A
 * @param <P>
 */
public class PlayerDataHandler<P extends PlayerData>
{
    @Getter
    private PlayerDataManager<P> playerDataManager;
    private Database database;
    private BasicDAO<P, UUID> daoAccessor;

    private Class<P> clazz;
    private Constructor<P> playerDataConstructor;

    public PlayerDataHandler(JavaPlugin plugin, Class<P> clazz, Database database)
    {
        this.playerDataManager = new PlayerDataManager<P>();
        this.database = database;
        this.clazz = clazz;

        this.database.map(clazz);

        try {
            this.playerDataConstructor = this.clazz.getConstructor(UUID.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this), plugin);
    }

    public BasicDAO<P, UUID> getDaoAccessor()
    {
        if(daoAccessor == null)
            this.daoAccessor = new BasicDAO<P, UUID>(this.clazz, this.database.getDatastore());
        return this.daoAccessor;
    }

    private P fetchPlayerData(String username, UUID uniqueId)
    {
        P playerData = this.getDaoAccessor().findOne("_id", uniqueId);
        if (playerData == null)
        {
            try {
                playerData = this.playerDataConstructor.newInstance(uniqueId);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            playerData.setUsername(username);
            this.getDaoAccessor().save(playerData);
        }
        return playerData;
    }

    public void connnect(String name, UUID uniqueId)
    {
        P playerData = this.fetchPlayerData(name, uniqueId);
        this.playerDataManager.registerPlayerData(playerData);
        playerData.setLoaded(true);

        Console.log("PlayerData", "Player " + name + " has connected");
    }

    public void disconnnect(String name, UUID uniqueId)
    {
        P playerData = this.playerDataManager.getPlayerData(uniqueId);
        if(playerData == null)
        {
            Console.log("PlayerData", "Failed to save playerdata " + name + " as it was null");
            return;
        }
        //save from database;
        this.getDaoAccessor().save(playerData);
        this.playerDataManager.unregisterPlayerData(uniqueId);

        Console.log("PlayerData", "Player " + name + " has disconnected");
    }
}
