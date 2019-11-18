package games.bevs.library.commons.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventBase extends Event
{
	
	/**
	 * Call the evennt
	 */
	public void call()
	{
		Bukkit.getPluginManager().callEvent(this);
	}
	/* Bukkit code that isn't really needed in every class 99% of the time */
	private static final HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() { return handlerList; }
    public static HandlerList getHandlerList() { return handlerList; }
}
