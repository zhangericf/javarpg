package com.zhange.rpg.entities;

import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;

public class Trap extends Entity {

    public Trap(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);

        bounds.setWidth(32);
        bounds.setHeight(20);
        bounds.setXOffset(10);
        bounds.setYOffset(20);
    }

    public void update(Player player, double time) {
        super.update(time);
        if (bounds.collides(player.getBounds())) {
            System.out.println("je suis la");
            int damage = 20;
            player.health -= damage;
            isDead = true;
        }
    }
    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        g.drawImage(anim.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
