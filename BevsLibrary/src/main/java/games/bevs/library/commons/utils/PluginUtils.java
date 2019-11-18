package games.bevs.library.commons.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginUtils
{
    public static void registerListener(Listener listener, JavaPlugin plugin)
    {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
