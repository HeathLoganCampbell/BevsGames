package games.bevs.library.modules.scoreboard;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import games.bevs.library.commons.utils.PacketUtils;
import games.bevs.library.commons.utils.reflection.Reflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


/**
 * Improvements to make,
 * - Check if there has been any changes to the content
 * - Fill out the api
 *
 */
public class BevsScoreboard
{
	private static final Class PACKET_CLASS = PacketPlayOutScoreboardTeam.class;
    
    private static final Field TEAM_NAME = Reflection.getField(PACKET_CLASS, "a");
    private static final Field DISPLAY_NAME = Reflection.getField(PACKET_CLASS, "b");
    private static final Field PREFIX = Reflection.getField(PACKET_CLASS, "c");
    private static final Field SUFFIX = Reflection.getField(PACKET_CLASS, "d");
    private static final Field NAME_TAG_VISIBILITY = Reflection.getField(PACKET_CLASS, "e");
    private static final Field MEMBERS = Reflection.getField(PACKET_CLASS, "g");
    private static final Field TEAM_MODE = Reflection.getField(PACKET_CLASS, "h");
    private static final Field OPTIONS = Reflection.getField(PACKET_CLASS, "i");

	
	private static final char[] charaters = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'l', 'r', 'm', 'n', 'o', 'k' };
	
	private static Scoreboard scoreboard;
	private static Objective objective;
	
	private List<String> teamNames = new ArrayList<>();
	
	private boolean isRefreshRequired = false;
	private boolean[] lineUpdated = new boolean[15];
	private String[] lineUpdatedContent = new String[15];
	
	private Player player;
	
	public BevsScoreboard(JavaPlugin plugin, Player player)
	{
		this.player = player;
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if(objective == null)
        {
	        scoreboard.registerNewObjective(UUID.randomUUID().toString().substring(0, 8), "dummy").setDisplaySlot(DisplaySlot.SIDEBAR);
	        objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        }
        
        reset();
	}
	
	public void open(Player player)
	{
		player.setScoreboard(scoreboard);
	}
	
	public void setTitle(String title)
	{
		objective.setDisplayName(title);
	}
	
	public void setLine(int slot, String line)
	{
		String teamName = this.teamNames.get(slot);
		Team team = this.scoreboard.getTeam(teamName);
		if(team == null) return;
		
		String prefix = line.substring(0, Math.min(line.length(), 16));
		
		String lastColor = ChatColor.getLastColors(line);
		
		String suffix = lastColor + line.substring(prefix.length(), Math.min(line.length(), 30));
		
		if(prefix.length() > 16)
		{
			System.out.println("'" +prefix + "' (Prefix) is too long for the scoreboard at " + prefix.length());
			System.out.println("The line length is a total of " + line.length());
		}
			
		if(suffix.length() > 16)
		{
			System.out.println("'" +suffix + "' (Suffix) is too long for the scoreboard " + suffix.length());
			System.out.println("The line length is a total of " + line.length());
		}

		try {
			PacketUtils.sendPacket(player, constructDefaultPacket(team, TeamMode.UPDATE, prefix, suffix));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.objective.getScore(teamName).setScore(15 - slot);
	}
	
	 private Object constructDefaultPacket(Team team, TeamMode mode, String prefix, String suffix) {
	        if (mode != TeamMode.CREATE && mode != TeamMode.REMOVE && mode != TeamMode.UPDATE)
	            return null;
	        try {
	            Object teamPacket = PACKET_CLASS.newInstance();
				Reflection.setValue(TEAM_NAME, teamPacket, team.getName());
	            if (mode == TeamMode.REMOVE)
	                return teamPacket;

				Reflection.setValue(DISPLAY_NAME, teamPacket, team.getDisplayName());
				Reflection.setValue(PREFIX, teamPacket, prefix);
				Reflection.setValue(SUFFIX, teamPacket, suffix);
				Reflection.setValue(NAME_TAG_VISIBILITY, teamPacket, "never");
				Reflection.setValue(TEAM_MODE, teamPacket, mode.getMode());
				Reflection.setValue(OPTIONS, teamPacket, 0);

	            return teamPacket;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	@SuppressWarnings("deprecation")
	public void reset()
	{
//		 this.teamNames.forEach(this.scoreboard::resetScores);
//		 this.teamNames.clear();
//		 this.teamNames = new ArrayList<>();
		 
		 for (int i = 0; i < 16; i++) 
		 {
		      
		      String teamName = "§" + "1234567890abcdefghijklmnopqrstuvwxyz".toCharArray()[i];
		      
		      this.teamNames.add(teamName);
		      Team team = this.scoreboard.getTeam(teamName);
		      if(team == null)
		    	  team = this.scoreboard.registerNewTeam(teamName);
		      team.addEntry(teamName);
		 } 
	}
	
	 public enum TeamMode {

	        CREATE(0),
	        REMOVE(1),
	        UPDATE(2),
	        ADD_PLAYER(3),
	        REMOVE_PLAYER(4);

	        private final int mode;

	        TeamMode(final int mode) {
	            this.mode = mode;
	        }

	        public final int getMode() {
	            return this.mode;
	        }

	    }
}