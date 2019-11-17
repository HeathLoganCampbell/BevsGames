package games.bevs.permissions.managers;

import games.bevs.permissions.types.PlayerData;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.WeakHashMap;

@RequiredArgsConstructor
public class PlayerManager
{
    private WeakHashMap<Player, PermissionAttachment> permissions = new WeakHashMap<>();
    @NonNull
    @Getter
    private JavaPlugin plugin;

    public void setPermssion(Player player, String permssion)
    {
        PermissionAttachment attachment = player.addAttachment(plugin, permssion,true);
        permissions.put(player, attachment);
    }

    public void load(UUID uniqueId)
    {

    }

    public void save(PlayerData playerData)
    {

    }
}
