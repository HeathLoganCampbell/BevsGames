package games.bevs.library.modules.essentials.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.commons.utils.PingUtils;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * the players current ping
 * 
 * Note: I learned on munchy that people feel like, 
 * 		 when their ping is divide, they lag less
 * 		 even when their connection is the same.
 */
public class PingCommand
{
	@Command(name = "ping",
			description = "Tells you, your current ping",
			usage = "/ping",
			requireRank = Rank.NORMAL,
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

		//Makes placebo for players
		int ping = PingUtils.getPing(target);
		ping /= 2;

		player.sendMessage(CC.green + (ping + " ms"));
	}
}
