package games.bevs.hub.listeners;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Duration;
import games.bevs.library.commons.ModuleListener;
import games.bevs.library.modules.fentity.FakePlayer;
import games.bevs.library.modules.playerdata.PlayerDataHandler;
import games.bevs.library.modules.playerdata.types.PlayerData;
import games.bevs.library.modules.scoreboard.BevsScoreboard;
import games.bevs.library.modules.ticker.TickEvent;
import games.bevs.library.modules.ticker.TimeType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ScoreboardListener implements Listener
{
    private PlayerDataHandler playerDataHandler;

    public ScoreboardListener(PlayerDataHandler playerDataHandler)
    {
        this.playerDataHandler = playerDataHandler;
    }

    @EventHandler
    public void onSecond(TickEvent e)
    {
        if(e.getType() != TimeType.SLOWER)
            return;

        long hours = (1000l * 60l * 60l);
        long targetTime = 1574932082371l + hours * 58;
        Duration dur = new Duration(targetTime - System.currentTimeMillis());

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            PlayerData playerData = this.playerDataHandler.getPlayerDataManager().getPlayerData(onlinePlayer);
            if(playerData == null) continue;
            BevsScoreboard scoreboard = playerData.getBevsScoreboard();
            if(scoreboard == null) continue;
            int i = 0;
            scoreboard.setLine(i++,  CC.white + CC.strikeThrough + "---------------------");
            scoreboard.setLine(i++,  CC.bAqua + "YOUTUBE: COMPSWHY");
            scoreboard.setLine(i++,  CC.white + CC.strikeThrough + "---------------------");
            scoreboard.setLine(i++,  CC.bGold + dur.getAsUit(Duration.TimeUnit.HOUR) + " HOURS");
            scoreboard.setLine(i++,  CC.white + CC.strikeThrough + "---------------------");
        }
    }

    @EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if(e.getBlock().getType() != Material.IRON_BLOCK) return;

		FakePlayer fakePlayer = new FakePlayer(CC.bYellow + "BevsGames", e.getBlock().getWorld().getSpawnLocation());
		fakePlayer.setSkin("Sprock");
		fakePlayer.spawn();
	}
}
