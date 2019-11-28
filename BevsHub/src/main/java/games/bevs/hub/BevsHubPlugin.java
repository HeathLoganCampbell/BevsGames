package games.bevs.hub;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Console;
import games.bevs.library.commons.Scheduler;
import games.bevs.library.modules.adminmode.AdminMode;
import games.bevs.library.modules.chat.ChatModule;
import games.bevs.library.modules.chat.ChatSetting;
import games.bevs.library.modules.commands.CommandFramework;
import games.bevs.library.modules.configurable.ConfigManager;
import games.bevs.library.modules.database.Database;
import games.bevs.library.modules.database.DatabaseConfig;
import games.bevs.library.modules.essentials.EssentialsModule;
import games.bevs.library.modules.invisibility.Invisibility;
import games.bevs.library.modules.joinquit.JoinQuit;
import games.bevs.library.modules.playerdata.PlayerDataHandler;
import games.bevs.library.modules.scoreboard.BevsScoreboard;
import games.bevs.library.modules.sponge.SpongeModule;
import games.bevs.library.modules.sponge.SpongeSettings;
import games.bevs.library.modules.sponge.types.LauncherType;
import games.bevs.library.modules.worldoptions.WorldModule;
import games.bevs.library.modules.worldoptions.WorldOptionSettings;
import org.bukkit.plugin.java.JavaPlugin;


public class BevsHubPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Console.log("Hub", "Welcome :)");

        ConfigManager configManager = new ConfigManager(this, this,"games.bevs.hub");

        Database database = handleDatabase();

        PlayerDataHandler<HubPlayerData> playerDataHandler = new PlayerDataHandler<HubPlayerData>(this, HubPlayerData.class, database);
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

        JoinQuit joinQuit = new JoinQuit(this);
        joinQuit.addJoinRunnable((player) -> {
            Scheduler.later(() -> {
                HubPlayerData playerData = playerDataHandler.getPlayerDataManager().getPlayerData(player);
                BevsScoreboard scoreboard = new BevsScoreboard(this, player);
                playerData.setBevsScoreboard(scoreboard);
                scoreboard.open(player);

                scoreboard.setTitle(CC.bYellow + "MC MONDAY");
                int i = 0;
                scoreboard.setLine(i++,  CC.white + CC.strikeThrough + "---------------------");
//            scoreboard.setLine(i++,  CC.bAqua + "DISCORD: discord.compswhy.com");
                scoreboard.setLine(i++,  CC.bAqua + "YOUTUBE: COMPSWHY");
                scoreboard.setLine(i++,  CC.white + CC.strikeThrough + "---------------------");
                scoreboard.setLine(i++,  CC.bGold + "COUNTDOWN");
                scoreboard.setLine(i++,  CC.white + CC.strikeThrough + "---------------------");
            }, 5l);
        });

        ChatModule chat = new ChatModule(this, new ChatSetting(), playerDataHandler);

        WorldOptionSettings worldOptionSettings = new WorldOptionSettings();
        worldOptionSettings.setEnableWeather(false);
        worldOptionSettings.setEnableDecay(false);
        WorldModule worldModule = new WorldModule(this, worldOptionSettings);

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
