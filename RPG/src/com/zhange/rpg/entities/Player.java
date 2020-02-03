package com.zhange.rpg.entities;

import com.zhange.rpg.GamePanel;
import com.zhange.rpg.entities.Enemies.BigDragon;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.Vector2f;
import com.zhange.rpg.states.PlayState;
import com.zhange.rpg.util.KeyHandler;
import com.zhange.rpg.util.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity {

    private int lives = 3;
    private int ecart = 40;
    private BufferedImage img;

    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);

        attackDamage = 50;
        maxHealth = 100;
        if (health > maxHealth) health = maxHealth;
        health = maxHealth;
        bounds.setWidth(32);
        bounds.setHeight(20);
        bounds.setXOffset(10);
        bounds.setYOffset(32);

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("ui/head.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetPosition() {
        System.out.println("Reseting player...");
        pos.x = GamePanel.width /2 - 32;
        PlayState.map.x = 0;
        pos.y = GamePanel.height /2 - 32;
        PlayState.map.y = 0;
        setAnimation(0, sprite.getSpriteArray(0), 10);
    }

    public void update(ArrayList<Enemy> enemies, double time, Enemy dragon) {
        super.update(time);
        attacking = isAttacking(time);
        if(!swimming)
            for (Enemy enemy: enemies) {
                if (attacking) {
                    if (attack && hitBounds.collides(enemy.getBounds())) {
                        enemy.setHealth(enemy.getHealth() - attackDamage);
                    }
                }
            }
        if (attacking)
            if (PlayState.spawned)
            if (attack && hitBounds.collides(dragon.bounds))
                dragon.setHealth(dragon.getHealth() - attackDamage);

        if (!dying) {
            if (!drown) {
                move();
                if (pos.x > 470 && pos.x < 964) {
                    PlayState.map.x = pos.x - 470;
                }
                if (pos.y > 412 && pos.y < 1111) {
                    PlayState.map.y = pos.y - 412;
                }
                if (!TC.collisionTile(dx, 0)) {
                    pos.x += dx;
                }
                if (!TC.collisionTile(0, dy)) {
                    pos.y += dy;
                }
                if (pos.x > 614 && pos.y > 880 && PlayState.spawned)
                    if (pos.x < 1015 && pos.y < 940)
                        setHealth(0);
            } else if (anim.hasBeenPlayed()) {
                System.out.println("I'm drowning");
                lives--;
                if (lives != 0) {
                    resetPosition();
                    drown = false;
                    swimming = false;
                } else {
                    isDead = true;
                }
            }
        } else if (anim.hasBeenPlayed()) {
            System.out.println("You have been killed");
            lives--;
            if (lives != 0) {
                resetPosition();
                dying = false;
                health = maxHealth;
            } else {
                isDead = true;
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (swimming) {
            if (mouse.isLeftPressed()) {
                setDrown(true);
            }
        }
        if (mouse.isRightPressed()) {
            dying = true;
        }

        if (attack && canAttack) {
            attackTime = System.nanoTime();
        }

        if (!drown) {
            up = key.up.down;
            down = key.down.down;
            left = key.left.down;
            right = key.right.down;
            attack = key.attack.down;
            attack2 = key.attack2.down;
        } else {
            up = down = left = right = false;
        }
    }

    @Override
    public void render(Graphics2D g) {
        //.setColor(Color.blue);
        //g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        /*if (attack && !swimming) {
            g.setColor(Color.MAGENTA);
            g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), (int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) hitBounds.getWidth(), (int) hitBounds.getHeight());
        }*/
        for (int i = 0; i < lives; i++) {
            g.drawImage(img, ecart * i, 5, 45, 40, null);
        }

        if (attack2) {
            System.out.println(pos.x + ", " + pos.y);
        }

        g.setColor(Color.black);
        g.drawRect((int) (getPos().getWorldVar().x + getBounds().getXOffset()) - 11, (int) (getPos().getWorldVar().y + getBounds().getYOffset()) - 50, (int) getBounds().getWidth() + 20, 10);
        g.setColor(new Color(183, 0, 2));
        g.fillRect((int) (getPos().getWorldVar().x + getBounds().getXOffset()) - 10, (int) (getPos().getWorldVar().y + getBounds().getYOffset()) - 49, health*52/100 - 1, 9);

        g.drawImage(anim.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
