package games.bevs.library.modules.joinquit;

import com.google.common.collect.Lists;
import games.bevs.library.commons.Callback;
import games.bevs.library.modules.Module;
import games.bevs.library.modules.joinquit.listener.JoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class JoinQuit extends Module
{
    private List<Callback<Player>> joinRunnables
                                 , quitRunnables;

    public JoinQuit(JavaPlugin plugin)
    {
        super("JoinQuit", plugin);
        this.joinRunnables = Lists.newLinkedList();
        this.quitRunnables = Lists.newLinkedList();

        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this), plugin);
    }

    public void join(Player player)
    {
        for (Callback<Player> joinRunnable : this.joinRunnables)
            joinRunnable.done(player);

        onJoin(player);
    }

    public void quit(Player player)
    {
        for (Callback<Player> quitRunnable : this.quitRunnables)
            quitRunnable.done(player);

        onQuit(player);
    }

    protected void onJoin(Player player)
    {
    }

    protected void onQuit(Player player)
    {
    }

    public void addJoinRunnable(Callback<Player> callback)
    {
        this.joinRunnables.add(callback);
    }

    public void addQuitRunnable(Callback<Player> callback)
    {
        this.quitRunnables.add(callback);
    }
}
