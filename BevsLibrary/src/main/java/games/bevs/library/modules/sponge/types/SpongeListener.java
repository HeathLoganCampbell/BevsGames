package games.bevs.library.modules.sponge.types;

import games.bevs.library.modules.sponge.SpongeModule;
import org.bukkit.event.Listener;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SpongeListener  implements Listener 
{
	private @Getter SpongeModule module;
	private @Getter LauncherType type;
}
