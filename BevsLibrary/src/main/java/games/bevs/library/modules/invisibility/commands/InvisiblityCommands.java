package games.bevs.library.modules.invisibility.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;
import games.bevs.library.modules.invisibility.Invisibility;
import games.bevs.library.modules.playerdata.types.PlayerData;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class InvisiblityCommands
{
    private Invisibility invisibility;

    /**
     * Hide from players with this command
     */
    @Command(name = "invisibility",
            aliases = {"vanish", "v", "invis"},
            description = "Become invisbile to ranks lower than you",
            usage = "/invis [Rank]",
            requireRank = Rank.MOD,
            playerOnly = true
    )
    public void onInvisCommand(CommandArgs args)
    {
        Player player = args.getPlayer();
        PlayerData playerData = args.getPlayerData();
        if(playerData == null)
        {
            player.sendMessage(CC.red + "Playerdata not saved to memory");
        }

        invisibility.setInvisibleTo(player, Rank.MOD);
    }

    @Command(name = "visibility",
            aliases = {"unvanish", "vis"},
            description = "Become invisbile to ranks lower than you",
            usage = "/invis [Rank]",
            requireRank = Rank.MOD,
            playerOnly = true
    )
    public void onVisCommand(CommandArgs args)
    {
        Player player = args.getPlayer();
        invisibility.setVisible(player);
    }
}
