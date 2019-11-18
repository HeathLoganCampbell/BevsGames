package games.bevs.library.commons.event;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerEventBase extends EventBase
{
	private @NonNull @Getter Player player;
}
