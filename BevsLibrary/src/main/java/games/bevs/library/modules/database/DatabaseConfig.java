package games.bevs.library.modules.database;

import games.bevs.library.modules.configurable.Configurable;
import games.bevs.library.modules.configurable.Optional;

@Configurable
public class DatabaseConfig
{
    @Optional(path = "database", fileName = "BevsConfigs/database")
    public static String HOST = "localhost";

    @Optional(path = "database", fileName = "BevsConfigs/database")
    public static String PORT = "27017";

    @Optional(path = "database", fileName = "BevsConfigs/database")
    public static String DATABASE = "BevsDatabase";

    @Optional(path = "database", fileName = "BevsConfigs/database")
    public static String USERNAME = "";

    @Optional(path = "database", fileName = "BevsConfigs/database")
    public static String PASSWORD = "";
}