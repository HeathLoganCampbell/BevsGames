package games.bevs.library.modules.sponge.impli;

import games.bevs.library.modules.sponge.SpongeModule;
import games.bevs.library.modules.sponge.types.LauncherType;
import games.bevs.library.modules.sponge.types.SpongeListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class BevsSpongeListener extends SpongeListener
{
	public BevsSpongeListener(SpongeModule module)
	{
		super(module, LauncherType.BEVS);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		Action action = e.getAction();
		if(action != Action.PHYSICAL ) return;
		Block clickedBlock = e.getClickedBlock();
		
		Block standingBlock = clickedBlock.getRelative(BlockFace.DOWN);
		
		if(clickedBlock.getType() == Material.STONE_PLATE
				&& standingBlock.getType() == this.getModule().getSpongeSettings().getLaunchMaterial())
		{
			this.getModule().launch(player, standingBlock);
		}
	}
}
