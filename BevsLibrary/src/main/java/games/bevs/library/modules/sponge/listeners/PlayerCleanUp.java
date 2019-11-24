package games.bevs.library.modules.sponge.listeners;

import games.bevs.library.modules.sponge.NoFallDamageList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class will clean up the {@link noFallDamageList}
 */
@AllArgsConstructor
public class PlayerCleanUp implements Listener
{
	private @Getter NoFallDamageList noFallDamageList;
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		this.getNoFallDamageList().remove(player);
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		Player player = e.getPlayer();
		this.getNoFallDamageList().remove(player);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		Player player = e.getEntity();
		this.getNoFallDamageList().remove(player);
	}
	
	//Just in case PlayerDeathEvent ins't working
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		Player player = e.getPlayer();
		this.getNoFallDamageList().remove(player);
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e)
	{
		Player player = e.getPlayer();
		this.getNoFallDamageList().remove(player);
	}
}
