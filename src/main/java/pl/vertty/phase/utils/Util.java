package pl.vertty.phase.utils;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.generic.BaseFullChunk;

public class Util {

    public static Location getHighestLocation(final Location l) {
        final Level level = Server.getInstance().getDefaultLevel();
        for (int y = 255; y >= 0; --y) {
            if (level.getBlock(l.getFloorX(), y, l.getFloorZ(), true).getId() != 0) {
                l.setComponents(l.getX(), y, l.getZ());
                return l;
            }
        }
        l.setComponents(l.getX(), 110.0, l.getZ());
        return l;
    }

    public static Block getBlock(final Level level, final int x, final int y, final int z, final boolean load) {
        int fullState;
        if (y >= 0 && y < 256) {
            final int cx = x >> 4;
            final int cz = z >> 4;
            BaseFullChunk chunk;
            if (load) {
                chunk = level.getChunk(cx, cz);
            }
            else {
                chunk = level.getChunkIfLoaded(cx, cz);
            }
            if (chunk != null) {
                fullState = chunk.getFullBlock(x & 0xF, y, z & 0xF);
            }
            else {
                fullState = 0;
            }
        }
        else {
            fullState = 0;
        }
        final Block block = Block.fullList[fullState & 0xFFF].clone();
        block.x = x;
        block.y = y;
        block.z = z;
        block.level = level;
        return block;
    }

}
