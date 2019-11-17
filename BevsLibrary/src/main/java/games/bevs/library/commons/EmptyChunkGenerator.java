package games.bevs.library.commons;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.generator.ChunkGenerator;

import net.minecraft.server.v1_8_R3.ServerNBTManager;
import net.minecraft.server.v1_8_R3.WorldProvider;
import net.minecraft.server.v1_8_R3.WorldServer;

public class EmptyChunkGenerator extends ChunkGenerator 
{

    /**
     * Sets the generator of the world specified by {@code worldUuid}
     * to {@link EmptyChunkGenerator}.
     *
     * This method should be called from {@link ServerNBTManager#createChunkLoader(WorldProvider)},
     * right before {@code WorldServer#k()} accesses {@link WorldServer#generator}.
     *
     * @param worldUuid the UUID of the world
     */
    public static void setGenerator(UUID worldUuid) {
        CraftWorld craftWorld = (CraftWorld) Bukkit.getWorld(worldUuid);
        craftWorld.getHandle().generator = new EmptyChunkGenerator();
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        return new byte[world.getMaxHeight() / 16][];
    }
}