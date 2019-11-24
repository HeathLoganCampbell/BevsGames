package games.bevs.library.modules.essentials;


import games.bevs.library.commons.utils.ClassGetterUtils;
import games.bevs.library.modules.Module;
import games.bevs.library.modules.commands.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

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
	public void onCommands(CommandFramework commandFramework)
	{
		ClassGetterUtils.getClassesForPackage(this.getPlugin(), "games.bevs.library.modules.essentials").forEach(clazz -> {
			try {
				
				Object obj = clazz.newInstance();
				commandFramework.registerCommands(obj);
				this.log("Loaded command " + clazz.getSimpleName());

			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				 | SecurityException e) {
				e.printStackTrace();
			}
		});
	}
}
