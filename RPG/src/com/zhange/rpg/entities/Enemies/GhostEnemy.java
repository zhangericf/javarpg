package com.zhange.rpg.entities.Enemies;

import com.zhange.rpg.entities.Enemy;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.AABB;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;

public class GhostEnemy extends Enemy {
    public GhostEnemy(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        setAcc(1f);
        setMaxSpeed(2f);
        r_sense = 250;
        setBase_r_sense(r_sense);
        setR_attack(50);
        setMaxHealth(70);
        setPoint(20);
        attackDamage = 15;
        sense = new AABB(new Vector2f(origin.x + size / 2 - r_sense / 2, origin.y + size - size / 5 - r_sense / 2), r_sense);
        enAttack = new AABB(new Vector2f(origin.x + size / 2 - r_attack / 2, origin.y + size - size / 5 - r_attack / 2), r_attack);
        setSense(sense);
        setEnAttack(enAttack);
        setBounds(32, 20, 10, 32);
        health = maxHealth;
    }
}
