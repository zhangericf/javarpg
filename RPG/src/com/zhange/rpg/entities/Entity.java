package com.zhange.rpg.entities;

import com.zhange.rpg.graphics.Animation;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.AABB;
import com.zhange.rpg.util.TileCollision;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    // Sprite line
    private final int Attack = 10, Dead = 9, Swim = 5, Drown = 4, Up = 3, Down = 2, Left = 1, Right = 0;

    protected Sprite sprite;
    protected Vector2f pos;
    protected Animation anim;
    protected int currentDirection = Right, currentAnimation, size, maxHealth = 100, health;
    protected float healthPercent = 1;

    // Action boolean
    protected boolean up, down, left, right, attack, attack2, swimming, drown, isDead, dying, full = true, attacking = false, canAttack = true, canHeal = true;

    protected int invincible = 500;
    protected double invincibleTime;
    protected boolean isInvincible = false;

    protected double attackTime;
    protected int attackDamage;
    protected int attackSpeed = 1000;
    protected int attackDuration = 600;

    protected float dx;
    protected float dy;

    protected float maxSpeed = 3f;
    protected float acc = 2.5f;
    protected float deacc = 0.4f;

    protected AABB hitBounds;
    protected AABB bounds;
    protected TileCollision TC;


    public Entity(Sprite sprite, Vector2f origin, int size) {
        this.sprite = sprite;
        pos = origin;
        this.size = size;

        bounds = new AABB(origin, size, size);
        hitBounds = new AABB(origin, size, size);
        hitBounds.setXOffset(size / 2);

        anim = new Animation();
        setAnimation(Right, sprite.getSpriteArray(Right), 10);

        TC = new TileCollision(this);
    }

    public Entity getEntity() {
        return this;
    }
    public Vector2f getPos() {
        return pos;
    }

    public void move() {
        if(up) {
            currentDirection = Up;
            dy -= acc;
            if(dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }

        if(down) {
            currentDirection = Down;
            dy += acc;
            if(dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }

        if(left) {
            currentDirection = Left;
            dx -= acc;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }

        if(right) {
            currentDirection = Right;
            dx += acc;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public void setSwimming(boolean i) {
        swimming = i;
    }
    public void setDrown(boolean i) {
        drown = i;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public void setAcc(float acc) {
        this.acc = acc;
    }
    public void setDeacc(float deacc) {
        this.deacc = deacc;
    }
    public int getHealth() {
        return health;
    }
    public boolean isNotDead() {
        return !isDead;
    }
    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public AABB getBounds() {
        return bounds;
    }

    public int getSize() {
        return size;
    }
    public Animation getAnimation() {
        return anim;
    }

    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        anim.setFrames(frames);
        anim.setDelay(delay);
    }

    public void animate() {
         if (!full) {
            if (currentAnimation != 1 || anim.getDelay() == -1)
            setAnimation(1, sprite.getSpriteArray(1), 50);
        } else if (drown) {
            if (currentAnimation != Drown || anim.getDelay() == -1) {
                setAnimation(Drown, sprite.getSpriteArray(Drown), 12);
            }
        } else if (dying) {
            if (currentAnimation != Dead || anim.getDelay() == -1) {
                setAnimation(Dead, sprite.getSpriteArray(Dead), 5);
            }
        } else if (!swimming) {
            if (attacking) {
                if (currentAnimation < 10) {
                    setAnimation(currentDirection + Attack, sprite.getSpriteArray(currentDirection + Attack), attackDuration / 100);
                }
            } else if (up || down || left || right) {
                if (currentAnimation != currentDirection || anim.getDelay() == -1) {
                    setAnimation(currentDirection, sprite.getSpriteArray(currentDirection), 5);
                }
            } else {
                setAnimation(currentDirection, sprite.getSpriteArray(currentDirection), -1);
            }
        } else {
            if (up || down || left || right) {
                if (currentAnimation != currentDirection + Swim || anim.getDelay() == -1) {
                    setAnimation(currentDirection + Swim, sprite.getSpriteArray(currentDirection + Swim), 5);
                }
            } else {
                setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
            }
        }
    }

    public void setHealth(int i) {
        if(!isInvincible) {
            health = i;
            isInvincible = true;
            invincibleTime = System.nanoTime();
            if(health <= 0) {
                dying = true;
            }
            healthPercent = (float) health / (float) maxHealth;
        }
    }

    public boolean isAttacking(double time) {
        canAttack = !(attackTime / 1000000 > ((time / 1000000) - attackSpeed));
        /*if (canAttack)
        System.out.println("can Attack " + (attackTime / 1000000 ) + ", " + ((time / 1000000) - attackSpeed));
        if (attackTime / 1000000 + attackDuration > (time / 1000000))
        System.out.println("attacking " + (attackTime / 1000000 + attackDuration) + ", " + time / 1000000);*/
        return attackTime / 1000000 + attackDuration > (time / 1000000);
    }

    protected double healTime, currentTime;
    public boolean Cooldown(int second) {
        currentTime = System.currentTimeMillis();
        return currentTime - healTime >= second * 1000;
    }

    public void setHitBoxDirection() {
        if (up) {
            hitBounds.setXOffset(0);
            hitBounds.setYOffset(-size / 2 );
        } else if (down) {
            hitBounds.setXOffset(0);
            hitBounds.setYOffset(size / 2 + 14);
        } else if (left) {
            hitBounds.setXOffset(-size / 2);
            hitBounds.setYOffset(14);
        } else if (right) {
            hitBounds.setXOffset(size / 2);
            hitBounds.setYOffset(14);
        }
    }

    public void update(double time) {
        if(isInvincible) {
            if((invincibleTime / 1000000) + invincible < (time / 1000000) ) {
                isInvincible = false;
            }
        }
        animate();
        setHitBoxDirection();
        anim.update();
    }

    public abstract void render(Graphics2D g);
}
