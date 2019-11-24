package games.bevs.library.modules.adminmode;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.commons.utils.PluginUtils;
import games.bevs.library.modules.Module;
import games.bevs.library.modules.adminmode.commands.AdminModeCommand;
import games.bevs.library.modules.adminmode.listeners.PlayerListener;
import games.bevs.library.modules.commands.CommandFramework;
import games.bevs.library.modules.invisibility.Invisibility;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.UUID;

public class AdminMode extends Module
{
    private JavaPlugin plugin;
    private Invisibility invisibility;
    private HashSet<UUID> adminModePlayers = new HashSet<>();

    public AdminMode(JavaPlugin plugin, Invisibility invisibility)
    {
        super("AdminMode", plugin);
        this.invisibility = invisibility;

        this.registerListener(new PlayerListener(this));
    }

    @Override
    public void onCommands(CommandFramework commandFramework)
    {
        commandFramework.registerCommands(new AdminModeCommand(this));
    }

    public void onDisconnected(Player player)
    {
        if(this.inAdminMode(player))
            this.adminModePlayers.remove(player.getUniqueId());
    }

    public boolean inAdminMode(Player player)
    {
        return this.adminModePlayers.contains(player.getUniqueId());
    }

    public void toggleAdminMode(Player player)
    {
        boolean inAdmin = this.inAdminMode(player);

        if(!inAdmin)
        {
            this.invisibility.setInvisibleTo(player, Rank.MOD);
            player.setGameMode(GameMode.CREATIVE);
            this.adminModePlayers.add(player.getUniqueId());
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
