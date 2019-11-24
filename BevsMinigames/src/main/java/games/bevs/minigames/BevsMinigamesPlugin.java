package games.bevs.minigames;

import games.bevs.library.commons.Console;
import games.bevs.library.modules.adminmode.AdminMode;
import games.bevs.library.modules.commands.CommandFramework;
import games.bevs.library.modules.configurable.ConfigManager;
import games.bevs.library.modules.database.Database;
import games.bevs.library.modules.database.DatabaseConfig;
import games.bevs.library.modules.essentials.EssentialsModule;
import games.bevs.library.modules.invisibility.Invisibility;
import games.bevs.library.modules.playerdata.PlayerDataHandler;
import games.bevs.library.modules.sponge.SpongeModule;
import games.bevs.library.modules.sponge.SpongeSettings;
import games.bevs.library.modules.sponge.types.LauncherType;
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
        CommandFramework commandFramework = new CommandFramework(this, playerDataHandler);

        Invisibility invModule = new Invisibility(this, playerDataHandler.getPlayerDataManager());
        AdminMode adminMode = new AdminMode(this, invModule);
        EssentialsModule essentials = new EssentialsModule(this);

        SpongeSettings spongeSettings = new SpongeSettings();
        spongeSettings.setLauncherType(LauncherType.CLASSIC);
        SpongeModule spongeModule = new SpongeModule(this, spongeSettings);

        playerDataHandler.onCommands(commandFramework);
        invModule.onCommands(commandFramework);
        adminMode.onCommands(commandFramework);
        essentials.onCommands(commandFramework);

        database.done();
        playerDataHandler.loadActivePlayerData();
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
