package games.bevs.library.commons;

import games.bevs.library.modules.Module;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ModuleListener<M extends Module> implements Listener
{
    private M module;

    public JavaPlugin getPlugin()
    {
        return this.getModule().getPlugin();
    }
}
