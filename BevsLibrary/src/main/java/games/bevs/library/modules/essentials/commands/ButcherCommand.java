package games.bevs.library.modules.essentials.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Rank;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Kills all entities in your world
 * 
 * Usage
 * 		/butcher
 * 			kills all unnamed living entities
 * 		/butcher all
 * 			kills all entities.
 */
public class ButcherCommand
{
	@Command(name = "heal",
			description = "Heal a player",
			usage = "/heal",
			requireRank = Rank.MOD,
			playerOnly = true
	)
	public void onHeal(CommandArgs args)
	{
		Player player = args.getPlayer();
		World world = player.getWorld();

		if(args.length() == 1 && args.getArgs(0).equalsIgnoreCase("all"))
		{
			//remove all entities
			world.getEntities().stream()
					.filter(entity -> !(entity instanceof Player))
					.forEach(entity -> entity.remove());
			player.sendMessage(CC.green + ("Destroyed all living entities in " + world.getName()));
		}
		else
		{
			//remove all mobs that don't have names
			world.getEntities().stream()
					.filter(entity -> (entity instanceof LivingEntity))
					.filter(entity -> !(entity instanceof Player))
					.filter(entity -> !((LivingEntity)entity).isCustomNameVisible())
					.forEach(entity -> entity.remove());
			player.sendMessage(CC.green + ("Destroyed all living entities in " + world.getName()));
			player.sendMessage(CC.aqua + ("To kill ALL entities, use '/butcher all'"));
		}
	}
}
