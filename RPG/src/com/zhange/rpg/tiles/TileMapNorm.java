package com.zhange.rpg.tiles;

import com.zhange.rpg.tiles.blocks.Block;
import com.zhange.rpg.tiles.blocks.NormBlock;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;
import java.util.ArrayList;

/**
 * Crée les block textures
 */

public class TileMapNorm extends TileMap {

    private ArrayList<Block> blocks;

    public TileMapNorm(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        blocks = new ArrayList<>();

        String[] block = data.split(",");
        for (int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if (temp != 0) {
                blocks.add(new NormBlock(sprite.getSprite((temp -  1) % tileColumns,  (temp -1) / tileColumns), new Vector2f( (i % width) * tileWidth,  (i / height) * tileHeight), tileWidth, tileHeight));
            }
        }
    }

    public void render(Graphics2D g) {
        for (Block block : blocks) {
            block.render(g);
        }
    }

}
