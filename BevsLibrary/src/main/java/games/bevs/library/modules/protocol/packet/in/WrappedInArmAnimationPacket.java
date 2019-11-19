package games.bevs.library.modules.protocol.packet.in;

import games.bevs.library.modules.protocol.api.NMSObject;
import lombok.Getter;

@Getter
public class WrappedInArmAnimationPacket extends NMSObject {
    private static final String packet = NMSObject.Client.ARM_ANIMATION;
}
