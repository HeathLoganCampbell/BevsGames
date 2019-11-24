package games.bevs.library.modules.disguises;

import games.bevs.library.modules.disguises.disguises.Disguise;
import games.bevs.library.modules.protocolv2.ProtocolManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class DisguiseManager
{
    private HashMap<Integer, Disguise> disguises = new HashMap<>();

    public DisguiseManager(JavaPlugin plugin, ProtocolManager protocolManager)
    {

    }

    /**
     * Will make an entity appear as another entity for a player
     * @param disguiseEntity
     * @param disguise
     */
    public void setDisguise(List<Player> observers, Entity disguiseEntity, Disguise disguise)
    {

    }

    /**
     * Clear a disguise from any entity
     * @param disguiseEntity
     */
    public void clearDisguise(List<Player> observers, Entity disguiseEntity)
    {

    }
}
