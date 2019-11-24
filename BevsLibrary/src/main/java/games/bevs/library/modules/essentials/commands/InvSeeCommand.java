package games.bevs.library.modules.essentials.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * open a player inventory
 * 
 * Useages
 * 		/invis <Target>
 * 			Look into someone elses inventory
 * 			Target - is a player name 
 */
public class InvSeeCommand
{

	@Command(name = "seeinventory",
			aliases = {"invsee"},
			description = "Allows your to see someones inventory",
			usage = "/ping",
			requireRank = Rank.MOD,
			playerOnly = true
	)
	public void onPing(CommandArgs args)
	{
		Player player = args.getPlayer();
		Player target = player;
		if(args.length() == 1)
		{
			String targetName = args.getArgs(0);
			target = Bukkit.getPlayer(targetName);
			if(target == null)
			{
				player.sendMessage(CC.red + (targetName + " is not online!"));
				return;
			}
		}

		player.openInventory(target.getInventory());
		player.sendMessage(CC.green + (target.getName() + "'s inventory is open."));
	}
}
