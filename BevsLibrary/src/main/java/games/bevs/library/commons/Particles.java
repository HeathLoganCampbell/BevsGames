package games.bevs.library.commons;

import games.bevs.library.commons.reflection.Reflection;
import games.bevs.library.commons.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum Particles {
    HUGE_EXPLOSION("hugeexplosion"),
    LARGE_EXPLODE("largeexplode"),
    FIREWORKS_SPARK("fireworksSpark"),
    BUBBLE("bubble"),
    SUSPEND("suspend"),
    DEPTH_SUSPEND("depthSuspend"),
    TOWN_AURA("townaura"),
    CRIT("crit"),
    MAGIC_CRIT("magicCrit"),
    MOB_SPELL("mobSpell"),
    MOB_SPELL_AMBIENT("mobSpellAmbient"),
    SPELL("spell"),
    INSTANT_SPELL("instantSpell"),
    WITCH_MAGIC("witchMagic"),
    NOTE("note"),
    PORTAL("portal"),
    ENCHANTMENT_TABLE("enchantmenttable"),
    EXPLODE("explode"),
    FLAME("flame"),
    LAVA("lava"),
    FOOTSTEP("footstep"),
    SPLASH("splash"),
    LARGE_SMOKE("largesmoke"),
    CLOUD("cloud"),
    RED_DUST("reddust"),
    SNOWBALL_POOF("snowballpoof"),
    DRIP_WATER("dripWater"),
    DRIP_LAVA("dripLava"),
    SNOW_SHOVEL("snowshovel"),
    SLIME("slime"),
    HEART("heart"),
    ANGRY_VILLAGER("angryVillager"),
    HAPPY_VILLAGER("happerVillager"),
    ICONCRACK("iconcrack_"),
    TILECRACK("tilecrack_");

    private String particleName;

    private Particles(String particleName) {
        this.particleName = particleName;
    }

    public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "a", this.particleName);
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "b", (float) location.getX());
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "c", (float) location.getY());
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "d", (float) location.getZ());
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "e", offsetX);
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "f", offsetY);
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "g", offsetZ);
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "h", speed);
        Reflection.setValue(PacketPlayOutWorldParticles.class, packet, "i", count);

        PacketUtils.sendPacket(player, packet);
    }
}