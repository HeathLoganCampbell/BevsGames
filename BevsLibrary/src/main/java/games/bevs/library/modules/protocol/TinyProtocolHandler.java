package games.bevs.library.modules.protocol;

import games.bevs.library.modules.protocol.api.AbstractTinyProtocol;
import games.bevs.library.modules.protocol.api.NMSObject;
import games.bevs.library.modules.protocol.api.ProtocolVersion;
import games.bevs.library.modules.protocol.packet.in.*;
import games.bevs.library.modules.protocol.packet.out.*;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.WeakHashMap;

public class TinyProtocolHandler {
	public static AbstractTinyProtocol instance;
	private static WeakHashMap<Player, ProtocolVersion> protocolVersions = new WeakHashMap<>();

	// Purely for making the code cleaner
	public static void sendPacket(Player player, Object packet) {
		instance.sendPacket(player, packet);
	}

	public static int getProtocolVersion(Player player) {
		return protocolVersions.get(player).getVersion();
	}

	public TinyProtocolHandler(JavaPlugin plugin) {
		TinyProtocolHandler self = this;
		instance = new TinyProtocol(plugin) {
			@Override
			public Object onPacketOutAsync(Player receiver, Object packet) {
				return self.onPacketOutAsync(receiver, packet);
			}

			@Override
			public Object onPacketInAsync(Player sender, Object packet) {
				return self.onPacketInAsync(sender, packet);
			}
		};
	}



	public Object onPacketOutAsync(Player receiver, Object packet) {
		if (receiver == null) return packet;

		boolean cancel = false;
		String name = packet.getClass().getName();
		int index = name.lastIndexOf(".");
		String packetName = name.substring(index + 1);
		try {
			ProtocolVersion playerProtocolVersion = protocolVersions.get(receiver);
			if(playerProtocolVersion == null) {
				try {
					playerProtocolVersion = ProtocolVersion.getVersion(TinyProtocolHandler.getProtocolVersion(receiver));
				} catch (Exception e) {
					playerProtocolVersion = ProtocolVersion.V1_8_9;
				}
				protocolVersions.put(receiver, playerProtocolVersion);
			}

			switch (packetName) {
				case NMSObject.Server.KEEP_ALIVE: {
					WrappedOutKeepAlivePacket wrapped = new WrappedOutKeepAlivePacket(packet);
					wrapped.process(receiver, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Server.ENTITY_VELOCITY: {
					WrappedOutVelocityPacket wrapped = new WrappedOutVelocityPacket(packet);
					wrapped.process(receiver, playerProtocolVersion);
//					if (wrapped.getId() == receiver.getEntityId())
//						data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
//				case NMSObject.Server.POSITION: {
//					WrappedOutPositionPacket wrapped = new WrappedOutPositionPacket(packet);
//					wrapped.process(receiver, playerProtocolVersion);
//					data.fireChecks(wrapped);
//					cancel = wrapped.isCancelled();
//					break;
//				}
				case NMSObject.Server.GAME_STATE: {
					WrappedOutGameState wrapped = new WrappedOutGameState(packet);
					wrapped.process(receiver, playerProtocolVersion);
//					data.fireChecks(wrapped);
//					data.gamemode = GameMode.getByValue((int) wrapped.getValue());
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Server.ENTITY_TELEPORT: {
					WrappedOutEntityTeleport wrapped = new WrappedOutEntityTeleport(packet);
//					for (HumanNPC npc : data.npc.npcs) {
//						npc.teleportEntity(data, wrapped);
//					}
					break;
				}
				case NMSObject.Server.REL_LOOK:
				case NMSObject.Server.REL_POSITION:
				case NMSObject.Server.REL_POSITION_LOOK:
				case NMSObject.Server.LEGACY_REL_LOOK:
				case NMSObject.Server.LEGACY_REL_POSITION:
//				case NMSObject.Server.LEGACY_REL_POSITION_LOOK: {
//					WrappedOutRelativePosition wrapped = new WrappedOutRelativePosition(packet);
//					for (HumanNPC npc : data.npc.npcs) {
//						npc.moveEntity(data, wrapped);
//					}
//					break;
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cancel ? null : packet;
	}

	public Object onPacketInAsync(Player sender, Object packet) {
		if (sender == null) return packet;
		boolean cancel = false;
		String name = packet.getClass().getName();
		int index = name.lastIndexOf(".");
		String packetName = name.substring(index + 1);
		try {
			ProtocolVersion playerProtocolVersion = protocolVersions.get(sender);
			if(playerProtocolVersion == null) {
				try {
					playerProtocolVersion = ProtocolVersion.getVersion(TinyProtocolHandler.getProtocolVersion(sender));
				} catch (Exception e) {
					playerProtocolVersion = ProtocolVersion.V1_8_9;
				}
				protocolVersions.put(sender, playerProtocolVersion);
			}

			switch (packetName) {
				case NMSObject.Client.ARM_ANIMATION: {
					WrappedInArmAnimationPacket wrapped = new WrappedInArmAnimationPacket();
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.BLOCK_DIG: {
					WrappedInBlockDigPacket wrapped = new WrappedInBlockDigPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.BLOCK_PLACE: {
					WrappedInBlockPlacePacket wrapped = new WrappedInBlockPlacePacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.CLIENT_COMMAND: {
					WrappedInClientCommandPacket wrapped = new WrappedInClientCommandPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.CLOSE_WINDOW: {
					WrappedInCloseWindowPacket wrapped = new WrappedInCloseWindowPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.ENTITY_ACTION: {
					WrappedInEntityActionPacket wrapped = new WrappedInEntityActionPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.POSITION:
				case NMSObject.Client.LOOK:
				case NMSObject.Client.POSITION_LOOK:
				case NMSObject.Client.LEGACY_POSITION:
				case NMSObject.Client.LEGACY_LOOK:
				case NMSObject.Client.LEGACY_POSITION_LOOK:
				case NMSObject.Client.FLYING: {
					WrappedInFlyingPacket wrapped = new WrappedInFlyingPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.HELD_ITEM: {
					WrappedInHeldItemSlotPacket wrapped = new WrappedInHeldItemSlotPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.KEEP_ALIVE: {
					WrappedInKeepAlivePacket wrapped = new WrappedInKeepAlivePacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.STEER_VEHICLE: {
					WrappedInSteerVehiclePacket wrapped = new WrappedInSteerVehiclePacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.TRANSACTION: {
					WrappedInTransactionPacket wrapped = new WrappedInTransactionPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
				case NMSObject.Client.USE_ENTITY: {
					WrappedInUseEntityPacket wrapped = new WrappedInUseEntityPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
//					if (data.state.cancelHits-- > 0) {
//						cancel = true;
//					}
					break;
				}
				case NMSObject.Client.WINDOW_CLICK: {
					WrappedInWindowClickPacket wrapped = new WrappedInWindowClickPacket(packet);
					wrapped.process(sender, playerProtocolVersion);
//					data.fireChecks(wrapped);
					cancel = wrapped.isCancelled();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cancel ? null : packet;
	}
}