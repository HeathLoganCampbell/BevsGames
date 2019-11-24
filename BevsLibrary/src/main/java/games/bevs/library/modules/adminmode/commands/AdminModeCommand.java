package games.bevs.library.modules.adminmode.commands;

import games.bevs.library.commons.Rank;
import games.bevs.library.modules.adminmode.AdminMode;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class AdminModeCommand
{
    private AdminMode adminMode;

    @Command(name = "admin",
            aliases = {"adminmode"},
            description = "Go into admin mode",
            usage = "/admin",
            requireRank = Rank.MOD,
            playerOnly = true
    )
    public void onAdminMode(CommandArgs args)
    {
        Player player = args.getPlayer();
        adminMode.toggleAdminMode(player);

    }
}
