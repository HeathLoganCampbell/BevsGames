package games.bevs.library.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class ShapeUtils
{
	public static ArrayList<Location> getCircle(Location loc, boolean hollow, double radius)
	{
		return getCircleBlocks(loc, radius, 0.0D, hollow, false);
	}
	
	public static ArrayList<Location> getCircleBlocks(Location loc, double radius, double height, boolean hollow, boolean sphere)
	{
		ArrayList<Location> circleblocks = new ArrayList<Location>();
		double cx = loc.getBlockX();
		double cy = loc.getBlockY();
		double cz = loc.getBlockZ();

		for (double y = sphere ? cy - radius : cy; y < (sphere ? cy + radius : cy + height + 1.0D); y += 1.0D)
			for (double x = cx - radius; x <= cx + radius; x += 1.0D)
				for (double z = cz - radius; z <= cz + radius; z += 1.0D)
				{
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0.0D);

					if ((dist < radius * radius) && ((!hollow) || (dist >= (radius - 1.0D) * (radius - 1.0D))))
					{
						Location l = new Location(loc.getWorld(), x, y, z);
						circleblocks.add(l);
					}
				}
		return circleblocks;
	}
	
	public static HashMap<Block, Double> getInRadius(Block block, double dR, boolean hollow)
		{
	    HashMap<Block, Double>  blockList = new HashMap<>();
	    int iR = (int)dR + 1;

	    for (int x = -iR; x <= iR; x++) {
	      for (int z = -iR; z <= iR; z++)
	        for (int y = -iR; y <= iR; y++)
	        {
	          Block curBlock = block.getRelative(x, y, z);

				  double offset = MathUtils.offset(block.getLocation(), curBlock.getLocation());

	          if ((offset <= dR) && ((!hollow) || (offset >= dR - 1.0D)))
	          {
	            blockList.put(curBlock, Double.valueOf(1.0D - offset / dR));
	          }
	        }
	    }
	    return blockList;
	  }
}
