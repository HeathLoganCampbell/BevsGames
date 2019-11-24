package games.bevs.library.modules.sponge;

import games.bevs.library.modules.Module;
import games.bevs.library.modules.sponge.impli.BevsSpongeListener;
import games.bevs.library.modules.sponge.impli.ClassicSpongeListener;
import games.bevs.library.modules.sponge.listeners.PlayerCleanUp;
import games.bevs.library.modules.sponge.types.LauncherType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import lombok.Getter;

/**
 * This module will allow you 
 * to place sponges which will launch players
 * in the opposite director of the sponges
 * <br/>
 * We have Bevs implementation which is based on pressure plates 
 * which is handled on interaction Event (aka when a pleasure plate is step on)
 * and Classic which is mcpvp's which is based on move event 
 */
public class SpongeModule extends Module
{
	private @Getter NoFallDamageList noFallDamageList;
	private @Getter SpongeSettings spongeSettings;

	public SpongeModule(JavaPlugin plugin, SpongeSettings spongeSettings)
	{
		super("Sponge", plugin);
		this.spongeSettings = spongeSettings;
		this.noFallDamageList = new NoFallDamageList();

		setUpListeners();
	}

	public SpongeModule(JavaPlugin plugin)
	{
		this(plugin, new SpongeSettings());
	}

	
	private void setUpListeners()
	{
		LauncherType launcherType = this.spongeSettings.getLauncherType();
		
		if(launcherType == LauncherType.BEVS) this.registerListener(new BevsSpongeListener(this));
		if(launcherType == LauncherType.CLASSIC) this.registerListener(new ClassicSpongeListener(this));
		
		this.registerListener(new PlayerCleanUp(this.noFallDamageList));
	}
	
	/**
	 * Gets the number of blocks under the player, do velocity math, and will launch them
	 * @param player
	 * @param block
	 */
	public void launch(Player player, Block block) 
	{
		Vector velocity = new Vector(0, getSpongeDepth(block), 0);
		velocity = velocity.add(new Vector(getSpongeDepth(block.getRelative(-1, -1, 0)), 0, 0));
		velocity = velocity.add(new Vector(-getSpongeDepth(block.getRelative(1, -1, 0)), 0, 0));
		velocity = velocity.add(new Vector(0, 0, getSpongeDepth(block.getRelative(0, -1, -1))));
		velocity = velocity.add(new Vector(0, 0, -getSpongeDepth(block.getRelative(0, -1, 1))));
		velocity.setX(velocity.getX() * this.getSpongeSettings().getVertVelocity());
		velocity.setY(velocity.getY() * this.getSpongeSettings().getHortVelocity());
		velocity.setZ(velocity.getZ() * this.getSpongeSettings().getVertVelocity());
		player.setVelocity(velocity);
	}

	private int getSpongeDepth(Block block) 
	{
		int depth = 0;

		while (block.getType() == this.getSpongeSettings().getLaunchMaterial()) 
		{
			depth++;
			if (block.getY() == 0)
				break;
			block = block.getRelative(BlockFace.DOWN);
		}
		return depth;
	}
}
