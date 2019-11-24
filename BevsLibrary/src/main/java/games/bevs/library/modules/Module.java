package games.bevs.library.modules;

import games.bevs.library.modules.commands.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

public class Module
{
    private String name;
    private JavaPlugin plugin;

    public Module(String name, JavaPlugin plugin)
    {
        this.name = name;
        this.plugin = plugin;
    }

    /**
     * Calling register command in here
     * @param commandFramework
     */
    public void onCommands(CommandFramework commandFramework)
    {

    }
}
