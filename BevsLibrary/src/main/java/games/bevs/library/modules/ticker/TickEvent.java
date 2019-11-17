package games.bevs.library.modules.ticker;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TickEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private TimeType type;
	
	public TickEvent(TimeType type) {
		this.type = type;
	}
	
	public TimeType getType() {
		return type;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
