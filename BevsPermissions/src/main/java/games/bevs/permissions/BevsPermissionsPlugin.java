package games.bevs.permissions;

import games.bevs.library.commons.utils.Console;
import games.bevs.library.modules.database.Database;
import org.bukkit.plugin.java.JavaPlugin;


public class BevsPermissionsPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Console.log(BevsPermissions.NAME, "Enabled plugin");

//        Database database = new Database(this, "148.12.55.12", 22, "databaseName");
//        database.map(PlayerData.class);
//        database.map(Rank.class);
//        database.done();

    }

    @Override
    public void onDisable()
    {
        Console.log(BevsPermissions.NAME, "Disable plugin");
    }
}
