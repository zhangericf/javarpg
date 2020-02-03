package com.zhange.rpg.entities;

import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.AABB;
import com.zhange.rpg.math.Vector2f;
import com.zhange.rpg.states.StateManager;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Entity {

    protected AABB sense;
    protected AABB enAttack;
    protected int r_sense, base_r_sense, r_attack;
    protected int superSense = 1000;
    protected double superSenseTime;
    protected boolean isSuperSense = false;
    protected Vector2f origin;
    protected int point = 10;

    //#region Getter&&Setter
    public AABB getSense() {
        return sense;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setSense(AABB sense) {
        this.sense = sense;
    }

    public AABB getEnAttack() {
        return enAttack;
    }

    public void setEnAttack(AABB enAttack) {
        this.enAttack = enAttack;
    }

    public void setBase_r_sense(int base_r_sense) {
        this.base_r_sense = base_r_sense;
    }

    public int getR_sense() {
        return r_sense;
    }

    public void setR_sense(int r_sense) {
        this.r_sense = r_sense;
    }

    public int getR_attack() {
        return r_attack;
    }

    public void setR_attack(int r_attack) {
        this.r_attack = r_attack;
    }

    public int getSuperSense() {
        return superSense;
    }

    public void setSuperSense(int superSense) {
        this.superSense = superSense;
    }

    public double getSuperSenseTime() {
        return superSenseTime;
    }

    public void setSuperSenseTime(double superSenseTime) {
        this.superSenseTime = superSenseTime;
    }

    public boolean isSuperSense() {
        return isSuperSense;
    }

    public void setSuperSense(boolean superSense) {
        isSuperSense = superSense;
    }

    public Vector2f getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2f origin) {
        this.origin = origin;
    }
    //#endregion
    public Enemy(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        this.origin = origin;
        acc = 1f;
        maxSpeed = 2f;
        r_attack = 50;
        maxHealth = 50;
        attackDamage = 15;
        sense = new AABB(new Vector2f(origin.x + size / 2 - r_sense / 2, origin.y + size - size / 5 - r_sense / 2), r_sense);
        enAttack = new AABB(new Vector2f(origin.x + size / 2 - r_attack / 2, origin.y + size - size / 5 - r_attack / 2), r_attack);
    }

    public void setBounds(int w, int h, int xoff, int yoff) {
        bounds.setWidth(w);
        bounds.setHeight(h);
        bounds.setXOffset(xoff);
        bounds.setYOffset(yoff);
    }

    public void chase(Player player) {
        if (sense.colCircleBox(player.getBounds()) && !enAttack.colCircleBox(player.getBounds())) {
            up = pos.y > player.pos.y + 1;
            down = pos.y < player.pos.y - 1;
            left = pos.x > player.pos.x + 1;
            right = pos.x < player.pos.x - 1;
            r_sense = base_r_sense*2;
        } else {
            r_sense = base_r_sense;
            up = false;
            down = false;
            left = false;
            right = false;
        }
    }

    public void update(Player player, double time) {
        sense = new AABB(new Vector2f(origin.x + size / 2 - r_sense / 2, origin.y + size - size / 5 - r_sense / 2), r_sense);
        super.update(time);
        attacking = isAttacking(time);
        if(!dying) {
            chase(player);
            move();
            if(enAttack.colCircleBox(player.getBounds()) && !isInvincible) {
                attack = true;
                player.setHealth(player.getHealth() - attackDamage);
            } else {
                attack = false;
            }

            if (attack && canAttack) {
                attackTime = System.nanoTime();
            }

            if (!TC.collisionTile(dx, 0)) {
                enAttack.getPos().x += dx;
                sense.getPos().x += dx;
                pos.x += dx;
            }
            
            if (!TC.collisionTile(0, dy)) {
                enAttack.getPos().y += dy;
                sense.getPos().y += dy;
                pos.y += dy;
            }

        } else if (anim.hasBeenPlayed()) {
            System.out.println("Enemy killed");
            StateManager.addScore(point);
            isDead = true;
        }
    }

    @Override
    public void render(Graphics2D g) {
       /* g.setColor(Color.red);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        g.setColor(Color.YELLOW);
        g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r_sense, r_sense);
        g.setColor(new Color(255, 92, 213));
        g.drawOval((int) (enAttack.getPos().getWorldVar().x), (int) (enAttack.getPos().getWorldVar().y), r_attack, r_attack);*/

        g.setColor(Color.black);
        g.drawRect((int) (getPos().getWorldVar().x + getBounds().getXOffset()) - 11, (int) (getPos().getWorldVar().y + getBounds().getYOffset()) - 50, (int) getBounds().getWidth() + 20, 10);
        g.setColor(new Color(183, 0, 2));
        g.fillRect((int) (getPos().getWorldVar().x + getBounds().getXOffset()) - 10, (int) (getPos().getWorldVar().y + getBounds().getYOffset()) - 49, health*52/maxHealth - 1, 9);

        g.drawImage(anim.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
