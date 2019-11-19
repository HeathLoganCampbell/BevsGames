package games.bevs.library.modules.protocol.packet.out;

import games.bevs.library.modules.protocol.api.NMSObject;
import lombok.Getter;

@Getter
public class WrappedOutEntityDestroy extends NMSObject {
	private static final String packet = NMSObject.Server.ENTITY_DESTROY;

	public WrappedOutEntityDestroy(int[] ids) {
		setPacket(packet, ids);
	}
}
