package games.bevs.library.modules.essentials;


import com.google.gson.internal.Streams;
import games.bevs.library.commons.utils.ClassGetterUtils;
import games.bevs.library.modules.Module;
import games.bevs.library.modules.commands.CommandFramework;
import games.bevs.library.modules.essentials.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

/**
 * Essentials commands
 */
public class EssentialsModule extends Module
{
	public EssentialsModule(JavaPlugin plugin) {
		super("Essentials", plugin);
	}
	
	/**
	 * Reigster all class
	 */
	@Override
	public void onCommands(CommandFramework commandFramework)
	{
		Stream.of(new ButcherCommand(),
				 new FeedCommand(),
				 new FlyCommand(),
				 new HealCommand(),
				 new InvSeeCommand(),
				 new PingCommand()).forEach(
						commandFramework::registerCommands);
	}
}
