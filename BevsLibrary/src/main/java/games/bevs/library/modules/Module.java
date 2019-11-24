package games.bevs.library.modules;

import games.bevs.library.commons.utils.PluginUtils;
import games.bevs.library.modules.commands.CommandFramework;
import org.bukkit.event.Listener;
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

    public void registerListener(Listener listener)
    {
        PluginUtils.registerListener(listener, this.plugin);
    }

    /**
     * Calling register command in here
     * @param commandFramework
     */
    public void onCommands(CommandFramework commandFramework)
    {

    }
}
