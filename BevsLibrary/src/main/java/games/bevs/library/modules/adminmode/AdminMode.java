package games.bevs.library.modules.adminmode;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.modules.invisibility.Invisibility;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.UUID;

public class AdminMode
{
    private JavaPlugin plugin;
    private Invisibility invisibility;
    private HashSet<UUID> adminModePlayers;

    public AdminMode(JavaPlugin plugin, Invisibility invisibility)
    {
        this.plugin = plugin;
        this.invisibility = invisibility;
    }

    public boolean inAdminMode(Player player)
    {
        return this.adminModePlayers.contains(player.getUniqueId());
    }

    public void toggleAdminMode(Player player)
    {
        boolean inAdmin = this.inAdminMode(player);

        if(inAdmin)
        {
            this.invisibility.setInvisibleTo(player, Rank.MOD);
            player.setGameMode(GameMode.CREATIVE);
            this.adminModePlayers.remove(player.getUniqueId());
        }
        else
        {
            this.invisibility.setVisible(player);
            player.setGameMode(GameMode.SURVIVAL);
            this.adminModePlayers.remove(player.getUniqueId());
        }

        player.sendMessage(CC.dPurple + "You are " + CC.idPurple + (!inAdmin ? "now" : "no longer") + CC.dPurple + " in admin mode!");
    }
}
