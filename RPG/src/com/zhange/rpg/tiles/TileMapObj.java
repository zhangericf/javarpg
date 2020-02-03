package com.zhange.rpg.tiles;

import com.zhange.rpg.tiles.blocks.Block;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.tiles.blocks.WaterBlock;
import com.zhange.rpg.tiles.blocks.ObjBlock;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;
import java.util.HashMap;

/**
 * Crée les different type de block de collision
 */

public class TileMapObj extends TileMap {

    public static HashMap<String , Block> TMO_blocks;
    protected int height;

    public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        Block tempBlock;
        TMO_blocks = new HashMap<>();
        this.height = height;

        String[] block = data.split(",");
        for (int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if (temp != 0) {
                if (temp == 392) {
                    tempBlock = new WaterBlock(sprite.getSprite((temp - 1) % tileColumns,  (temp -1) / tileColumns), new Vector2f((i % width) * tileWidth,  (i / height) * tileHeight), tileWidth, tileHeight);
                } else {
                    tempBlock = new ObjBlock(sprite.getSprite((temp - 1) % tileColumns,  (temp -1) / tileColumns), new Vector2f( (i % width) * tileWidth,  (i / height) * tileHeight), tileWidth, tileHeight);
                }
                TMO_blocks.put(i % width + "," + i / height, tempBlock);
            }
        }
    }
    public void render(Graphics2D g) {
        for (Block block: TMO_blocks.values()) {
            block.render(g);
        }
    }
}
