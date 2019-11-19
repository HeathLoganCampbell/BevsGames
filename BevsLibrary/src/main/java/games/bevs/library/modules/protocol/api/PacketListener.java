package games.bevs.library.modules.protocol.api;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface PacketListener {
    public void onPacket(Player player, Object packet);
}
