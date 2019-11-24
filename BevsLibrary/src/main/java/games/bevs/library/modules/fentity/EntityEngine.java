package games.bevs.library.modules.fentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import games.bevs.library.commons.Scheduler;
import games.bevs.library.commons.reflection.Reflection;
import games.bevs.library.commons.utils.MathUtils;
import games.bevs.library.commons.utils.PluginUtils;
import games.bevs.library.modules.fentity.listener.PlayerListener;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EntityEngine
{
//    private static final Class<?> PACK_OUT_NAMED_ENTITY_SPAWN = Reflection.getMcClass("PacketPlayOutNamedEntitySpawn");
//    private static final Field ENTITY_ID = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "a");
//    private static final Field ENTITY_UUID = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "b");
//    private static final Field ENTITY_POSITION_X = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "c");
//    private static final Field ENTITY_POSITION_Y = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "d");
//    private static final Field ENTITY_POSITION_Z = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "e");
//    private static final Field ENTITY_YAW = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "f");
//    private static final Field ENTITY_PITCH = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "g");
//    private static final Field ENTITY_ITEM = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "h");
//    private static final Field ENTITY_DATAWATCHER = Reflection.getField(PACK_OUT_NAMED_ENTITY_SPAWN, "i");
//
//    private static final Class<?> PACK_OUT_PLAYER_INFO_CLASS = Reflection.getMcClass("PacketPlayOutPlayerInfo");
//    private static final Class<?> PLAYER_INFO_CLASS = Reflection.getMcClass("PacketPlayOutPlayerInfo$PlayerInfoData");
//    private static final Constructor<?> PLAYER_INFO_CONSTRUCTOR = Reflection.getConstructor(PLAYER_INFO_CLASS, PacketPlayOutPlayerInfo.class, GameProfile.class, int.class, WorldSettings.EnumGamemode.class, IChatBaseComponent.class);
//    private static final Field PLAYER_INFO_ACTION = Reflection.getField(PACK_OUT_PLAYER_INFO_CLASS, "a");
//    private static final Field PLAYER_INFO_DATA = Reflection.getField(PACK_OUT_PLAYER_INFO_CLASS, "b");
//
//    private JavaPlugin plugin;
//    private int entityId = -1;
//
//    private DataWatcher dataWatcher = new DataWatcher(null);
//    private GameProfile profile;
//    private UUID uuid = UUID.randomUUID();
//
//    private Behavior behavior;
//
//    private Vector lastLocale;
//    private float lastYaw, lastPitch;
//
//    public EntityEngine(JavaPlugin plugin)
//    {
//        this.plugin = plugin;
//
//        profile = new GameProfile(this.uuid, "Sprock");
//        profile.getProperties().put("textures", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MzEyNjE3NzkyNjAsInByb2ZpbGVJZCI6ImFkMWM2Yjk1YTA5ODRmNTE4MWJhOTgyMzY0OTllM2JkIiwicHJvZmlsZU5hbWUiOiJGdXJrYW5iejAwIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yNzUyOTkzNDljYWZiNDRlMzBlM2Y2M2M0YWNiNDFhOWJjNmQyMWYxMzJlMmI4ZDNhZTBiZmI5NTgxZjdiYzA4In19fQ==", "D1cSY3Vo16qnJ0NbEQnC9wJrSqdxghcbdqCy8T9GXWW7Wj1WqosWHxvnGet6cJXkFH21n2HCUQ2+VGUw6T9wL6BVj3zdQS+Vz1zRlAYwTw4bf13xCdcGPuntOwzWZeXfW2B78FIhF61H7vxGiwmRthKJT+EVtTngoqEWlpuyQ4oQMssc4C5i/5spWZ74HCX/EtemfELvFVNaeqeSuSZpx1cwVCdT4sP8Q3U8mRyMRx8JnUk5u9Matv5+izdj5Z6V2tFKhV+CQWaDRH2EwGG4hVxz2pM0dstDNyAirwwW3KRiPmdVZNjR01ixtfEVzbHBVduPA6f/DQ77OxL54oq9d22FYKdAniUrhI3kI5uxQpNXNVd82W1OhWFkKvcoBz8u6acTv8JIEiKo6SdWsc4GWDxp7jgHLVr4kYoFXTm3x9HIbqbW3v4V07196OfC1EVV/35ChiUa+BU/r78A1AxdAzwh2x62FOFq3xqs17HWMkkbj03cGunIglCEUW35Jxn54O3DsJ3hHSInMPz8iacjaLCHS/H2nLqwwz4/5LILpt+XX9nwj4+UIm97OlK3lsGaA3G0V9Ev8tSMGrucVsRF3xDZt9v3o6gsdAYZxHGDYQDyzHBF/zaVwQuKBHF0N5o9CKwkRLtNPoJce5RkdTzguCRrUSSz9fquvVylFdc8cWs="));
//
//
//        PluginUtils.registerListener(new PlayerListener(this), this.plugin);
//
//        Scheduler.repeat(() -> {
//            if(entityId != -1)
//            {
//
//            }
//        }, 20l, 20l);
//    }
//
//    public void spawnFor(Player player)
//    {
//        try {
//            this.lastLocale = new Vector(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
//            entityId = 160353 + MathUtils.random(1000);
//
//            // Add Player
//            Object tabAddPacket = Reflection.getInstance(PACK_OUT_PLAYER_INFO_CLASS);
//            Reflection.setValue(PLAYER_INFO_ACTION, tabAddPacket, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
//            List<Object> addInfoList = Reflection.getValue(PLAYER_INFO_DATA, tabAddPacket);
//            String pname = "DUMMY";
//            Object playerData = Reflection.getInstance(PLAYER_INFO_CONSTRUCTOR, tabAddPacket, profile, 69, WorldSettings.EnumGamemode.SURVIVAL, CraftChatMessage.fromString(pname)[0]);
//            addInfoList.add(playerData);
//
//            // Spawn Player
//            PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn();
//            Reflection.setValue(ENTITY_ID, spawnPacket, entityId);
//            Reflection.setValue(ENTITY_UUID, spawnPacket, this.uuid);
//            Reflection.setValue(ENTITY_POSITION_X, spawnPacket, (int) Math.floor(this.getX() * 32D));
//            Reflection.setValue(ENTITY_POSITION_Y, spawnPacket, (int) Math.floor(this.getY() * 32D));
//            Reflection.setValue(ENTITY_POSITION_Z, spawnPacket, (int) Math.floor(this.getZ() * 32D));
//            Reflection.setValue(ENTITY_YAW, spawnPacket, (byte)((int)(this.getYaw() * 256.0F / 360.0F)));
//            Reflection.setValue(ENTITY_PITCH, spawnPacket, (byte)((int)(this.getPitch() * 256.0F / 360.0F)));
//            Reflection.setValue(ENTITY_DATAWATCHER, spawnPacket, this.dataWatcher);
//            Reflection.setValue(ENTITY_ITEM, spawnPacket, 0);
//
//            // Remove Player
//            Object tabRemovePacket = Reflection.getInstance(PACK_OUT_PLAYER_INFO_CLASS);
//            Reflection.setValue(PLAYER_INFO_ACTION, tabRemovePacket, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
//            List<Object> removeInfoList = Reflection.getValue(PLAYER_INFO_DATA, tabRemovePacket);
//            removeInfoList.add(Reflection.getInstance(PLAYER_INFO_CONSTRUCTOR, tabRemovePacket, profile, 69, WorldSettings.EnumGamemode.SURVIVAL, CraftChatMessage.fromString(pname)[0]));
//
//            //TODO: Reflection on this
//            CraftPlayer cp = (CraftPlayer) player;
//            ((EntityPlayer)cp.getHandle()).playerConnection.sendPacket((Packet) tabAddPacket);
//            ((EntityPlayer)cp.getHandle()).playerConnection.sendPacket(spawnPacket);
//
//            //remove from tab
//            Scheduler.later(() -> {
//                ((EntityPlayer)cp.getHandle()).playerConnection.sendPacket((Packet) tabRemovePacket);
//            }, 1000, TimeUnit.MILLISECONDS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removeFor(Player player)
//    {
//
//    }
//
//    protected void sendPositionAndRotationFor(List<Player> players, byte deltaX, byte deltaY, byte deltaZ, float yaw, float pitch) {
//        try {
//            PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook ppoem = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(entityId, deltaX, deltaY, deltaZ, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), true);
//            players.forEach(player -> {
//                CraftPlayer cp = (CraftPlayer) player;
//                cp.getHandle().playerConnection.sendPacket(ppoem);
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void tick()
//    {
//
//    }
//
//    public double getX() {
//    return this.lastLocale.getX();
//}
//    public double getY() {
//        return this.lastLocale.getY();
//    }
//    public double getZ() {
//        return this.lastLocale.getZ();
//    }
//
//    public float getYaw() {
//        return this.lastYaw;
//    }
//    public float getPitch() {
//        return this.lastPitch;
//    }
}
