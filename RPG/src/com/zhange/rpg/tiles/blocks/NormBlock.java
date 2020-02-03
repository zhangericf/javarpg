package com.zhange.rpg.tiles.blocks;

import com.zhange.rpg.math.AABB;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Texture block
 */

public class NormBlock extends Block {

    public NormBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return false;
    }

    @Override
    public boolean isInside(AABB p) {
        return false;
    }

    public void render(Graphics2D g) {
        super.render(g);
    }
}
