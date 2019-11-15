package games.bevs.permissions.managers;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.WeakHashMap;

@RequiredArgsConstructor
public class RankManager
{

    @NonNull @Getter
    private JavaPlugin plugin;

}
