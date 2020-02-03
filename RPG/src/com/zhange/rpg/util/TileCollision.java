package com.zhange.rpg.util;

import com.zhange.rpg.entities.Entity;
import com.zhange.rpg.tiles.TileManager;
import com.zhange.rpg.tiles.TileMapObj;
import com.zhange.rpg.tiles.blocks.Block;
import com.zhange.rpg.tiles.blocks.WaterBlock;

/**
 * Gere les collisions entre block et player
 */

public class TileCollision {

    private Entity e;
    private int TmBs = TileManager.blockSize;

    public TileCollision(Entity e) {
        this.e = e;
    }

    public boolean collisionTile(float ax, float ay) {
        for (int i = 0; i < 4; i++) {
            int xt = (int) ((e.getPos().x +ax) + (i % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / TmBs;
            int yt = (int) ((e.getPos().y +ay) + (i / 2) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / TmBs;
            Block block = TileMapObj.TMO_blocks.get(xt + "," + yt);
            if (TileMapObj.TMO_blocks.containsKey(xt + "," + yt)) {
                if (block instanceof WaterBlock) {
                    return collisionWater(ax, ay, xt, yt, block);
                }
                return block.update(e.getBounds());
            }
        }
        return false;
    }

    public boolean collisionWater(float ax, float ay, float xt, float yt, Block block) {

        int nextXt = (int) ((( (e.getPos().x + ax) + e.getBounds().getXOffset()) / TmBs) + e.getBounds().getWidth() / TmBs);
        int nextYt = (int) ((( (e.getPos().y + ay) + e.getBounds().getYOffset()) / TmBs) + e.getBounds().getHeight() / TmBs);

        if ((nextXt == yt + 1 || (nextXt == xt + 1) || (nextYt == yt - 1) || (nextXt == xt - 1))) {
            if (TileMapObj.TMO_blocks.containsKey(nextXt + "," + nextYt)) {
                Block nextBlock = TileMapObj.TMO_blocks.get(nextXt + "," + nextYt);
                e.setSwimming(true);
                return nextBlock.update(e.getBounds());
            }
        } else {
            if (block.isInside(e.getBounds())) {
                e.setSwimming(true);
                return block.update(e.getBounds());
            }
        }
        e.setSwimming(false);
        return false;
    }
}
