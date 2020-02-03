package com.zhange.rpg.tiles.blocks;

import com.zhange.rpg.math.AABB;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WaterBlock extends Block {

    public WaterBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return false;
    }

    public boolean isInside(AABB p) {
        if (p.getPos().x + p.getYOffset() < pos.x) return false;
        if (p.getPos().y + p.getYOffset() < pos.y) return false;
        if (w + pos.x < p.getWidth() + (p.getPos().x + p.getXOffset())) return false;
        return !(h + pos.y < p.getHeight() + (p.getPos().y + p.getYOffset()));
    }

    public void render(Graphics2D g) {
        super.render(g);
        //g.setColor(Color.green);
        //g.drawRect((int) pos.getWorldVar().x,(int) pos.getWorldVar().y, w, h);
    }
}
