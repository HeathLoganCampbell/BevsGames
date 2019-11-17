package games.bevs.permissions;

import games.bevs.library.commons.utils.CC;
import games.bevs.library.commons.utils.Console;
import games.bevs.library.modules.configurable.ConfigManager;
import games.bevs.library.modules.database.Database;
import games.bevs.permissions.database.DatabaseConfig;
import games.bevs.permissions.types.PlayerData;
import games.bevs.permissions.types.Rank;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.ArrayList;
import java.util.UUID;


public class BevsPermissionsPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Console.log(BevsPermissions.NAME, "Enabled plugin");

        ConfigManager configManager = new ConfigManager(this, this,"games.bevs.permissions");

        handleDatabase();
    }

    public void handleDatabase()
    {
        Database database = new Database(this, DatabaseConfig.HOST,
                Integer.parseInt(DatabaseConfig.PORT),
                DatabaseConfig.USERNAME,
                DatabaseConfig.PASSWORD,
                DatabaseConfig.DATABASE);
        database.map(PlayerData.class);
        database.map(Rank.class);
        database.done();

        BasicDAO<Rank, String> rankDao = new BasicDAO(Rank.class, database.getDatastore());
        BasicDAO<PlayerData, UUID> playerDataDao = new BasicDAO(PlayerData.class, database.getDatastore());

//        PlayerData playerData = new PlayerData(UUID.randomUUID(), "Sprock", "ADMIN", new ArrayList<>());
//        playerDataDao.save(playerData);

//        Rank exampleRank = new Rank("yeet", "Yoted", CC.green + "YEET");
//        exampleRank.getPermission().add("bevsNetwork.command.kill");
//        exampleRank.getPermission().add("bevsNetwork.command.tp");
//        exampleRank.getPermission().add("bevsNetwork.command.spin");
//        rankDao.save(exampleRank);
    }

    @Override
    public void onDisable()
    {
        Console.log(BevsPermissions.NAME, "Disable plugin");
    }
}
