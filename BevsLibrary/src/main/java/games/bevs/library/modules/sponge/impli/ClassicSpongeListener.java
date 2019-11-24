package games.bevs.library.modules.sponge.impli;

import games.bevs.library.modules.sponge.SpongeModule;
import games.bevs.library.modules.sponge.types.LauncherType;
import games.bevs.library.modules.sponge.types.SpongeListener;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;


/**
 * This method is the classic mcpvp method 
 * but it makes uses of playerMoveEvent which 
 * doesn't scale well
 */
public class ClassicSpongeListener extends SpongeListener
{
	public ClassicSpongeListener(SpongeModule module)
	{
		super(module, LauncherType.CLASSIC);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent e) 
	{
		Player player = e.getPlayer();
		Block block = e.getTo().getBlock().getRelative(0, -1, 0);

		if (block.getType() == this.getModule().getSpongeSettings().getLaunchMaterial())
		{
			this.getModule().launch(player, block);
		}
	}
}
