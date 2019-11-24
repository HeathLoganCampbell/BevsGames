package games.bevs.library.modules.protocolv2;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ProtocolManager extends TinyProtocol
{

    /**
     * Construct a new instance of TinyProtocol, and start intercepting packets for all connected clients and future clients.
     * <p>
     * You can construct multiple instances per plugin.
     *
     * @param plugin - the plugin.
     */
    public ProtocolManager(Plugin plugin) {
        super(plugin);
    }

    public Object onPacketOutAsync(Player receiver, Channel channel, Object packet) {

        return packet;
    }
    public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
        System.out.println("Packet: " + packet.getClass());
        return packet;
    }
}
