package games.bevs.library.modules.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import games.bevs.library.commons.Console;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * A database wrapper class,
 * call map(?.Class) or mapPackage("package.of.my.stuff")
 *
 * then called done()
 */
public class Database
{
    private MongoClient mongo;
    private Morphia morphia;
    private @Getter Datastore datastore;

    private String databaseName;

    public Database(JavaPlugin plugin, String url, int port, String username, String password, String database)
    {
        this.databaseName = database;

        ServerAddress addr = new ServerAddress(url, port);
        List<MongoCredential> credentials = new ArrayList<>();

        if(username.equalsIgnoreCase(""))
            username = null;

        if (username != null)
            credentials.add(MongoCredential.createCredential(username, database,
                    password.toCharArray()));
        try {
            mongo = new MongoClient(addr, credentials);
            morphia = new Morphia();

            // Set class loader to spigot's class loader
            if (plugin != null)
                morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {
                    @Override
                    protected ClassLoader getClassLoaderForClass() {
                        return plugin.getClass().getClassLoader();
                    }
                });

        } catch (Exception e) {
            Console.log( "Mongo", "Failed to connect to Mongo");
            e.printStackTrace();
            Bukkit.shutdown();
            throw new RuntimeException("Failed to connect to Mongo");
        }
    }

    public void map(Class<?> clazz)
    {
        morphia.map(clazz);
    }

    public void mapPackage(String path)
    {
        morphia.mapPackage(path);
    }

    /**
     * Called after you have made all your mappings
     */
    public void done()
    {
        datastore = morphia.createDatastore(mongo, this.databaseName);
        datastore.ensureIndexes();
    }
}
