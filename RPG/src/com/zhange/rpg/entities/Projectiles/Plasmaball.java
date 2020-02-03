package com.zhange.rpg.entities.Projectiles;

import com.zhange.rpg.entities.Entity;
import com.zhange.rpg.entities.Player;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;

/**
 * Crée un projectil plasmaball
 */

public class Plasmaball extends Entity {

    public Plasmaball(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        setAcc(0.5f);
        setMaxSpeed(2f);
        attackDamage = 49;
    }

    public void update(Player player, double time) {
        super.update(time);
        down = true;
        move();

        if (!TC.collisionTile(0, dy)) {
            pos.y += dy;
        }

        if(!isDead) {
            if (bounds.collides(player.getBounds())) {
                System.out.println("Je te touche");
                player.setHealth(player.getHealth() - attackDamage);
                isDead = true;
            } else if (pos.y > 1250) {
                isDead = true;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        /*g.setColor(Color.red);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());*/

        g.drawImage(anim.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
