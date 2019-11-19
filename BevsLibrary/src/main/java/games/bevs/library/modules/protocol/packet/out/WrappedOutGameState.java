package games.bevs.library.modules.protocol.packet.out;

import games.bevs.library.modules.protocol.api.NMSObject;
import games.bevs.library.modules.protocol.api.ProtocolVersion;
import games.bevs.library.modules.protocol.reflection.FieldAccessor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class WrappedOutGameState extends NMSObject {
    private static final String packet = NMSObject.Server.GAME_STATE;

    private static FieldAccessor<Integer> fieldReason = fetchField(packet, int.class, 0);
    private static FieldAccessor<Float> fieldValue = fetchField(packet, float.class, 0);

    private int reason;
    private float value;

    public WrappedOutGameState(Object packet) {
        super(packet);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        reason = fetch(fieldReason);
        value = fetch(fieldValue);
    }
}
