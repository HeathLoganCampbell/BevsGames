package games.bevs.permissions;

import games.bevs.library.commons.utils.Console;
import org.bukkit.plugin.java.JavaPlugin;


public class BevsPermissionsPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Console.log(BevsPermissions.NAME, "Enabled plugin");


    }

    @Override
    public void onDisable()
    {
        Console.log(BevsPermissions.NAME, "Disable plugin");
    }
}
