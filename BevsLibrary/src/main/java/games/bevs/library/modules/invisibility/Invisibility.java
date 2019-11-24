package games.bevs.library.modules.invisibility;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.commons.manager.PlayerManager;
import games.bevs.library.commons.utils.PluginUtils;
import games.bevs.library.modules.Module;
import games.bevs.library.modules.commands.CommandFramework;
import games.bevs.library.modules.invisibility.commands.InvisiblityCommands;
import games.bevs.library.modules.invisibility.events.VisibilityChangeEvent;
import games.bevs.library.modules.playerdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetAddress;
import java.util.UUID;

/**
 * This Module allows staff to go invisible to non-staff
 * or even lower level staff
 *
 * <h3>Commands</h3>
 * <code>
 *     /vanish - Will hide you from players
 *     /unvanish - Will make you visibile to all again
 * </code>
 */
public class Invisibility extends Module
{
    private PlayerDataManager playerDataManager;
    private PlayerManager<Rank> playerVisiblity;

    public Invisibility(JavaPlugin plugin, PlayerDataManager playerDataManager)
    {
        super("Invisiblity", plugin);

        this.playerDataManager = playerDataManager;
        this.playerVisiblity = new PlayerManager<Rank>(plugin, true)
        {
            //When player joins by default they we shouldn't add them to the hashmap
            // null = not added to manager
            @Override
            public Rank onPlayerCreation(UUID uniquieId, String username, InetAddress ipAddress)
            {
                return null;
            }

            @Override
            public boolean onPlayerDestruction(Rank playerObj)
            {
                return true;
            }
        };
    }


    //todo: add commands /vis and /invis
    @Override
    public void onCommands(CommandFramework commandFramework)
    {
        commandFramework.registerCommands(new InvisiblityCommands(this));
    }

    public void setInvisibleTo(Player player, Rank rank)
    {
        UUID uuid = player.getUniqueId();
        Rank from = this.playerVisiblity.getPlayer(player);
        if(from == null) from = Rank.NORMAL;

        this.playerVisiblity.register(uuid, rank);

        PluginUtils.call(new VisibilityChangeEvent(player, from, rank));

        Bukkit.getOnlinePlayers()
                .forEach(viewer -> {
                    if (!this.playerDataManager.getPlayerData(viewer.getUniqueId()).getRank().hasPermissionsOf(rank)) {
                        viewer.hidePlayer(player);
                    } else {
                        viewer.showPlayer(player);
                    }
                });

        player.sendMessage(CC.lPurple + "You're now only visible to " + rank.getColouredDisplayName() + CC.lPurple + " and up!");
        player.setPlayerListName("* " + player.getDisplayName());
    }

    public void setVisible(Player player) {
        UUID uuid = player.getUniqueId();

        Rank from = this.playerVisiblity.getPlayer(player);

        if (from != null) {
            this.playerVisiblity.unregisterPlayer(uuid);
            Bukkit.getPluginManager().callEvent(new VisibilityChangeEvent(player, from, Rank.NORMAL));
            player.sendMessage(CC.lPurple + "You're now visible to all!");
            Bukkit.getOnlinePlayers().stream()
                    .filter(viewer -> !viewer.canSee(player))
                    .forEach(viewer -> viewer.showPlayer(player));
            player.setPlayerListName(player.getDisplayName());

        }
    }
}
