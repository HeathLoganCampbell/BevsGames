package games.bevs.library.modules.sponge;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;

public class NoFallDamageList
{
	private HashSet<UUID> players = new HashSet<>();
	
	public void add(Player player)
	{
		this.players.add(player.getUniqueId());
	}
	
	public boolean remove(Player player)
	{
		return this.players.remove(player.getUniqueId());
	}
}
