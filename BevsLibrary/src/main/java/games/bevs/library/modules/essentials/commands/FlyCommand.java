package games.bevs.library.modules.essentials.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


/**
 * Get to set players flight
 * 
 * Useages
 * 		/fly <Target> <status>
 * 			Sets flying state
 * 			Target - is a player name 
 * 			Status - true or false on if they can fly
 * 		/fly <Target>
 * 			toggles flying state
 * 			Target - target player
 * 		/fly 
 * 			Affects your flying state
 */
public class FlyCommand
{


	@Command(name = "fly",
			description = "let a player fly",
			usage = "/fly <PlayerName>",
			requireRank = Rank.MOD,
			playerOnly = true
	)
	public void onHeal(CommandArgs args)
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

		boolean canFly = !target.isFlying();

		if(args.length() == 2)
		{
			String strBoolean = args.getArgs(1);
			if(strBoolean.equalsIgnoreCase("true") || strBoolean.equalsIgnoreCase("yes"))
				canFly = true;
			else
				canFly = false;
		}

		target.setAllowFlight(canFly);
		target.setFlying(canFly);

		if(player != target)
			target.sendMessage(CC.green +  ("You can " + ( canFly ? "now" : "no longer") +  " fly!"));
		player.sendMessage(CC.green + (target.getName() + " can " + ( canFly ? "now" : "no longer") + " fly!"));
	}
}
