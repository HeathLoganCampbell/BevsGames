package games.bevs.library.modules.protocol;

import games.bevs.library.modules.protocol.api.PacketListener;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolManager extends TinyProtocol
{
    private Map<Class<? extends Packet>, List<PacketListener>> packetListeners;
    public ProtocolManager(Plugin plugin) {
        super(plugin);

        this.packetListeners = new HashMap<>();
    }

    public void register(Class<? extends Packet> packet, PacketListener packetListener)
    {
        packetListeners.computeIfAbsent(packet, key -> new ArrayList<>(1)).add(packetListener);
    }

//    public void unregister(PacketListener packetListener)
//    {
//        packetListeners.values().removeIf(listeners -> listeners.remove(packetListener));
//    }

    private Object call(Player player, Object packet) {

        if (packet != null) {

            Class<?> clazz = packet.getClass();
            Map<Class<? extends Packet>, List<PacketListener>> listenerMap = this.packetListeners;
            List<PacketListener> priorities = listenerMap.get(clazz);
            if (priorities == null) {
                return packet;
            }


            for (PacketListener listener : priorities) {

                try {
                    listener.onPacket(player, packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return packet;
    }

    @Override
    public Object onPacketOutAsync(Player receiver, Object packet)
    {
        this.call(receiver, packet);
        return packet;
    }

    @Override
    public Object onPacketInAsync(Player sender, Object packet)
    {
        this.call(sender, packet);
        return packet;
    }
}
