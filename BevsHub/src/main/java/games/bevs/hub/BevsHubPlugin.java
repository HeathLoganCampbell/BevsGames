package games.bevs.hub;

import games.bevs.hub.listeners.ScoreboardListener;
import games.bevs.library.commons.CC;
import games.bevs.library.commons.Console;
import games.bevs.library.commons.ItemStackBuilder;
import games.bevs.library.commons.Scheduler;
import games.bevs.library.commons.utils.PluginUtils;
import games.bevs.library.commons.utils.TabUtils;
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
import games.bevs.library.modules.ticker.Ticker;
import games.bevs.library.modules.worldoptions.WorldModule;
import games.bevs.library.modules.worldoptions.WorldOptionSettings;
import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;


public class BevsHubPlugin extends JavaPlugin
{
    private NPCLib npclib;

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

        ItemStack item = new ItemStackBuilder(Material.SIGN).displayName(CC.bYellow + "Coming soon...").build();
        JoinQuit joinQuit = new JoinQuit(this);
        joinQuit.addJoinRunnable((player) -> {
            Scheduler.later(() -> {
                HubPlayerData playerData = playerDataHandler.getPlayerDataManager().getPlayerData(player);
                BevsScoreboard scoreboard = new BevsScoreboard(this, player);
                playerData.setBevsScoreboard(scoreboard);
                scoreboard.open(player);

                scoreboard.setTitle(CC.bYellow + "MC MONDAY");

                PlayerInventory inv = player.getInventory();
                inv.setItem(4, item);

                TabUtils.sendTab(player, "\n" + CC.bYellow + "MC MONDAY\n", "\n" + CC.gray + "                  POWERED BY COMPSWHY                  " + "\n");

                player.sendMessage(new String[] {
                        CC.bYellow + "      MCMONDAY" + CC.gray + " powered by COMPSWHY",
                        CC.white + CC.strikeThrough + "----------------------------------------",
                        "",
                        CC.bGreen + "  SUBSCRIBE: " + CC.gray + "http://youtube.compswhy.com",
                        CC.bGreen + "  JOIN: " + CC.gray + "         http://discord.compswhy.com",
                        "",
                        CC.white + CC.strikeThrough + "----------------------------------------",
                });
            }, 5l);
        });

        ChatModule chat = new ChatModule(this, new ChatSetting(), playerDataHandler);

        WorldOptionSettings worldOptionSettings = new WorldOptionSettings();
        worldOptionSettings.setEnableWeather(false);
        worldOptionSettings.setEnableDecay(false);
        worldOptionSettings.setEnableBlockBreak(false);
        worldOptionSettings.setEnableBlockPlace(false);
        worldOptionSettings.setEnableBlockInteraction(false);

        worldOptionSettings.setEnableHunger(false);
        worldOptionSettings.setEnablePlayerDamage(false);

        worldOptionSettings.setEnableDropItems(false);
        worldOptionSettings.setEnablePickUpItems(false);
        WorldModule worldModule = new WorldModule(this, worldOptionSettings);

        Ticker ticker = new Ticker(this);

        database.done();
        playerDataHandler.loadActivePlayerData();

        PluginUtils.registerListener(new ScoreboardListener(playerDataHandler), this);

        spawnNPCs(joinQuit);
    }

    private void spawnNPCs( JoinQuit joinQuit ){
        this.npclib = new NPCLib(this);

        final Location loc = new Location(Bukkit.getWorlds().get(0), -151.5D, 136.0D, 223.5D);

        //1982119160 https://mineskin.org/1982119160
        MineSkinFetcher.fetchSkinFromIdAsync(188100, skin -> {
            NPC npc = npclib.createNPC(Arrays.asList(CC.bYellow + "MC Monday", CC.gray + "Powered by COMPSWHY"));
            Bukkit.broadcastMessage(" vvvvv" + "");
            Bukkit.broadcastMessage(npc + "");
            npc.setLocation(loc);
            npc.setSkin(skin);
            npc.create();
            // The SkinFetcher fetches the skin async, you can only show the NPC to the player sync.
            Bukkit.getScheduler().runTask(this, () -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    npc.show(onlinePlayer);
                }
            });

            joinQuit.addJoinRunnable((player) ->  npc.show(player));
        });

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
