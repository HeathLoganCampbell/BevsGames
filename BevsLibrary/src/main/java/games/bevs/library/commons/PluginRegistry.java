package games.bevs.library.commons;

import games.bevs.library.commons.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andavin
 * @since May 16, 2018
 */
public final class PluginRegistry {

    private static WeakReference<Plugin> defPlugin;
    private static final int LOGGER_ATTEMPTS = 5;
    private static final Map<String, WeakReference<Plugin>> PLUGINS = new HashMap<>();
    private static final Field PLUGINS_FIELD = Reflection.findField(SimplePluginManager.class, "plugins");

    static {
        // A least one plugin should be loaded at the point
        // of a reference to this class therefore we should
        // be able to get a default here
        refresh();
    }

    /**
     * Register a plugin with its package reference
     * in order to use plugin referencing utils such
     * <p>
     * Note that it is expected that the plugin class is
     * in the top level package of the project.
     *
     * @param plugin The {@link Plugin plugin} instance to register.
     */
    public static void register(Plugin plugin) {

        Class<? extends Plugin> clazz = plugin.getClass();
        WeakReference<Plugin> reference = new WeakReference<>(plugin);
        if (defPlugin == null) {
            defPlugin = reference;
        }

        String name = clazz.getName();
        PLUGINS.put(name, reference);
        int first = name.indexOf('.');
        if (first == -1) {
            return;
        }

        for (int dot = name.lastIndexOf('.'); first < dot; dot = name.lastIndexOf('.')) {
            PLUGINS.put(name = name.substring(0, dot), reference);
        }
    }

    /**
     * Get the {@link Plugin} that called the method calling this
     * method. This will return the default plugin if no other
     * plugin can be found within the attempts allotted.
     *
     * @param attempts The attempts to (calls to go back) to try to
     *                 find the plugin within.
     * @return The plugin that called the method.
     */
    @Nonnull
    public static Plugin getPlugin(int attempts) {

        if (defPlugin != null) {

            Plugin defPlugin = PluginRegistry.defPlugin.get();
            if (defPlugin != null) {
                return defPlugin;
            }
        }

        String className = Reflection.getCallerClass(1); // Exclude the class that called this
        Plugin plugin = getPlugin(className);
        for (int tries = 2; plugin == null && tries <= attempts; tries++) {
            plugin = getPlugin(Reflection.getCallerClass(tries));
        }

        //noinspection ConstantConditions
        return plugin != null ? plugin : defPlugin.get();
    }

    /**
     * Get the {@link Plugin} by a class name that is registered.
     *
     * @param className The name of the class to get the plugin for.
     * @return The {@link Plugin} that is registered with the class name.
     */
    @Nullable
    public static Plugin getPlugin(String className) {

        char[] chars = className.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {

            if (chars[i] == '.') {

                WeakReference<Plugin> reference = PLUGINS.get(new String(chars, 0, i));
                if (reference != null && reference.get() != null) {
                    return reference.get();
                }
            }
        }

        refresh();
        return null;
    }

    private static void refresh() {
        // Use reflection to bypass the synchronization of Bukkit.getPluginManager().getPlugins()
        // This should avoid deadlocks and getting exact lists isn't super important here
        List<Plugin> plugins = Reflection.getValue(PLUGINS_FIELD, Bukkit.getPluginManager());
        //noinspection ConstantConditions
        for (Plugin plugin : plugins.toArray(new Plugin[0])) { // No ConcurrentModifications with toArray

            if (plugin != null) { // Null check just in case async issues
                register(plugin);
            }
        }
    }
}