package games.bevs.minigames;

import games.bevs.library.commons.Console;
import games.bevs.library.modules.configurable.ConfigManager;
import games.bevs.library.modules.database.Database;
import games.bevs.library.modules.database.DatabaseConfig;
import games.bevs.library.modules.playerdata.PlayerDataHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class BevsMinigamesPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Console.log("Minigames", "Welcome :)");

        ConfigManager configManager = new ConfigManager(this, this,"games.bevs.minigames");

        Database database = handleDatabase();

        PlayerDataHandler playerDataHandler = new PlayerDataHandler<MinigamePlayerData>(this, MinigamePlayerData.class, database);

        database.done();

    }

    public Database handleDatabase()
    {
        Database database = new Database(this, DatabaseConfig.HOST,
                Integer.parseInt(DatabaseConfig.PORT),
                DatabaseConfig.USERNAME,
                DatabaseConfig.PASSWORD,
                DatabaseConfig.DATABASE);

        return database;
    }

    @Override
    public void onDisable()
    {

    }
}
