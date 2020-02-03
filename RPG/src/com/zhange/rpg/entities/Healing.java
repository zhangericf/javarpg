package com.zhange.rpg.entities;

import com.zhange.rpg.entities.Entity;
import com.zhange.rpg.entities.Player;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;

public class Healing extends Entity {

    public Healing(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);

        bounds.setWidth(32);
        bounds.setHeight(20);
        bounds.setXOffset(5);
        bounds.setYOffset(96);
    }

    public void update(Player player, double time) {
        super.update(time);
        if (full) {
            if (bounds.collides(player.bounds)) {
                int heal = 40;
                player.health += heal;
                System.out.println("Player healed");
                full = false;
                if (player.health > player.maxHealth)
                    player.health = player.maxHealth;
            }
        } else if (anim.hasBeenPlayed()) {
            full = true;
            isDead = true;
        }
    }
    @Override
    public void render(Graphics2D g) {
        /*g.setColor(Color.blue);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());*/

        g.drawImage(anim.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
