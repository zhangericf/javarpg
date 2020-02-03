package com.zhange.rpg.entities.Enemies;

import com.zhange.rpg.entities.Enemy;
import com.zhange.rpg.entities.Player;
import com.zhange.rpg.entities.Projectiles.Plasmaball;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.AABB;
import com.zhange.rpg.math.Vector2f;
import com.zhange.rpg.states.PlayState;
import com.zhange.rpg.states.StateManager;

import java.awt.*;

/**
 * Boss de fin
 */

public class BigDragon extends Enemy {

    public static int damage;

    public BigDragon(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        setAcc(1f);
        setMaxSpeed(2f);
        r_sense = 600;
        r_attack = 600;
        setBase_r_sense(r_sense);
        setMaxHealth(300);
        setPoint(1000);
        damage = 19;
        sense = new AABB(new Vector2f(origin.x + size / 2 - r_sense / 2, origin.y + size*5/2 - r_sense / 2), r_sense);
        enAttack = new AABB(new Vector2f(origin.x + size / 2 - r_attack / 2, origin.y + size*5/2 - r_attack / 2), r_attack);
        setSense(sense);
        setEnAttack(enAttack);
        setBounds(110, 48, 4, 108);
        health = maxHealth;
    }

    @Override
    public void chase(Player player) {
        if (sense.colCircleBox(player.getBounds())) {
            left = pos.x > player.getPos().x + 1;
            right = pos.x + 100 < player.getPos().x - 1;
        } else {
            up = false;
            down = false;
            left = false;
            right = false;
        }
    }

    public void dragonAttack() {
        PlayState.plasmaballs.add(new Plasmaball(new Sprite("Boss/plasmaball.png", 128, 128), new Vector2f(pos.x+30, pos.y + 100), 60));
    }

    @Override
    public void update(Player player, double time) {
        super.update(time);

        attacking = isAttacking(time);
        if(!dying) {
            chase(player);
            super.move();
            if (enAttack.colCircleBox(player.getBounds()) && Cooldown(2)) {
                attack = true;
                dragonAttack();
                System.out.println("fireball");
            } else {
                attack = false;
            }

            if (attack && canAttack) {
                healTime = currentTime;
                attackTime = System.nanoTime();
            }

            if (!TC.collisionTile(dx, 0)) {
                enAttack.getPos().x += dx;
                sense.getPos().x += dx;
                pos.x += dx;
            }

        } else if (anim.hasBeenPlayed()) {
            System.out.println("Enemy killed");
            StateManager.addScore(point);
            PlayState.spawned = false;
            isDead = true;
            PlayState.bossKiled = true;
        }
    }

    @Override
    public void render(Graphics2D g) {
        /*g.setColor(Color.blue);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        g.setColor(Color.YELLOW);
        g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r_sense, r_sense);
        g.setColor(new Color(255, 92, 213));
        g.drawOval((int) (enAttack.getPos().getWorldVar().x), (int) (enAttack.getPos().getWorldVar().y), r_attack, r_attack);*/

        g.setColor(Color.black);
        g.drawRect((int) (getPos().getWorldVar().x + getBounds().getXOffset()) - 6, (int) (getPos().getWorldVar().y + getBounds().getYOffset()) - 120, (int) getBounds().getWidth() + 20, 10);
        g.setColor(new Color(183, 0, 2));
        g.fillRect((int) (getPos().getWorldVar().x + getBounds().getXOffset()) - 5, (int) (getPos().getWorldVar().y + getBounds().getYOffset()) - 119, health*130/maxHealth - 1, 9);

        g.drawImage(anim.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
