package games.bevs.library.commons.manager;

import java.net.InetAddress;
import java.util.UUID;

import games.bevs.library.commons.utils.PluginUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Extend onPlayerCreation & onPlayerDestruction for more options
 * @param <P>
 */
public abstract class PlayerManager<P> extends Manager<UUID, P> implements Listener
{
	public PlayerManager(JavaPlugin plugin, boolean autoManage)
	{
		super();
		
		
		if(autoManage)
		{
			PluginUtils.registerListener(this, plugin);
		}
		
	}
	
	public abstract P onPlayerCreation(UUID uniquieId, String username, InetAddress ipAddress);

	/**
	 *
	 * @param playerObj
	 * @return to remove from managers
	 */
	public abstract boolean onPlayerDestruction(P playerObj);
	
	public void registerPlayer(UUID uniquieId, String username, InetAddress ipAddress)
	{
		P playerObj = this.onPlayerCreation(uniquieId, username, ipAddress);
		if(playerObj == null) return;
		this.register(uniquieId, playerObj);
	}
	
	public void unregisterPlayer(UUID uniquieId)
	{
		P playerObj = this.getPlayer(uniquieId);
		if(this.onPlayerDestruction(playerObj))
			this.unregister(uniquieId);
	}
	
	public P getPlayer(UUID uniquieId)
	{
		return this.get(uniquieId);
	}
	
	public P getPlayer(Player player)
	{
		return this.getPlayer(player.getUniqueId());
	}
	
	/*
	 * Pass the point is only enabled if the autoManage is true
	 */
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uniquieId = e.getUniqueId();
		String username = e.getName();
		InetAddress ipAddress =e.getAddress();
		
		this.registerPlayer(uniquieId, username, ipAddress);
	}
	
	/** 
	 * We must handle the player getting forced leaved (aka kicked)
	 * as being kicked does not result in a PlayerQuitEvent
	 */
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		Player player = e.getPlayer();
		UUID uniquieId = player.getUniqueId();
		
		this.unregisterPlayer(uniquieId);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		UUID uniquieId = player.getUniqueId();
		
		this.unregisterPlayer(uniquieId);
	}
}