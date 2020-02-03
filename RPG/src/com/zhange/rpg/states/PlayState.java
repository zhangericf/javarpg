package com.zhange.rpg.states;

import com.zhange.rpg.GamePanel;
import com.zhange.rpg.entities.Enemies.BigDragon;
import com.zhange.rpg.entities.Enemies.GhostEnemy;
import com.zhange.rpg.entities.Enemy;
import com.zhange.rpg.entities.Healing;
import com.zhange.rpg.entities.Player;
import com.zhange.rpg.entities.Projectiles.Plasmaball;
import com.zhange.rpg.entities.Trap;
import com.zhange.rpg.graphics.Font;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.Vector2f;
import com.zhange.rpg.tiles.TileManager;
import com.zhange.rpg.util.KeyHandler;
import com.zhange.rpg.util.MouseHandler;

import java.awt.*;
import java.util.ArrayList;


public class PlayState extends State {

    private Font font;
    private int width = GamePanel.width, height = GamePanel.height, GPhw = width/2, pv, pvEnemy;
    public static Vector2f map;
    private Player player;
    private Enemy en;
    private Enemy enemy2;
    private Enemy dragon;
    private Healing healing;
    private Trap trap;
    private TileManager TM;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Plasmaball> plasmaballs = new ArrayList<>();
    private ArrayList<Plasmaball> toRemove = new ArrayList<>();
    public static boolean spawned = false, bossKiled;

    public PlayState(StateManager SM) {
        super(SM);
        TM = new TileManager("Map/terrain_atlas.tmx");

        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);
        font = new Font("Font/x05mo.png", 16, 16);

        player = new Player(new Sprite("Male/Amychar.png"), new Vector2f((GamePanel.width / 2) - 32, (GamePanel.height / 2) - 32), 48);
        en = new GhostEnemy(new Sprite("Enemy/Enemy Sprite.png"), new Vector2f((GamePanel.width/2) -32 + 350, (GamePanel.height / 2) - 32 - 150), 48);
        enemy2 = new GhostEnemy(new Sprite("Enemy/Enemy Sprite.png"), new Vector2f((GamePanel.width/2) -32 + 350, (GamePanel.height / 2) - 32 + 170), 48);
        healing = new Healing(new Sprite("ui/Heal.png"), new Vector2f(1345, 240), 48);
        trap = new Trap(new Sprite("ui/Piege.png"), new Vector2f(237, 573), 48);
        enemies.add(enemy2);
        enemies.add(en);
    }

    public float getRandomArbitrary(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    @Override
    public void update(double time) {

        Vector2f.setWorldVar(map.x, map.y);

        if (player.isNotDead()) {
            player.update(enemies, time, dragon);
        } else {
            SM.addAndPop(3);
        }

        en = enemies.get(0);
        enemy2 = enemies.get(1);

        updateEnemy(time, en);
        updateEnemy(time, enemy2);

        healing.update(player, time);

        for (Plasmaball fb: plasmaballs) {
            if (fb.isNotDead())
                fb.update(player, time);
            else
                toRemove.add(fb);
        }

        if(trap.isNotDead())
            trap.update(player, time);

        plasmaballs.removeAll(toRemove);

        if(spawned && dragon.isNotDead())
            dragon.update(player, time);

        if(StateManager.Score == 100 && !spawned) {
            dragon = new BigDragon(new Sprite("Boss/BigDragon.png", 96, 96), new Vector2f(815-32, 900-50), 128);
            spawned = true;
        }

        if(bossKiled)
            SM.addAndPop(1);

    }

    private void updateEnemy(double time, Enemy ene) {
        if (ene.isNotDead()) {
            ene.update(player, time);
        } else {
            enemies.remove(ene);
            int random = (int) getRandomArbitrary(1, 8);
            if (random == 1)
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite blue.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            else if (random == 2)
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite green.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            else if (random == 3)
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite red.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            else if (random == 4)
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite brown.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            else if (random == 5)
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite magenta.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            else if (random == 6)
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite yellow.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            else if (random == 7)
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite purple.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            else
                ene = new GhostEnemy(new Sprite("Enemy/Enemy Sprite.png"), new Vector2f(getRandomArbitrary(758, 1157), getRandomArbitrary(160, 571)), 48);
            enemies.add(ene);
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        player.input(mouse, key);
        if (key.escape.down) {
            spawned = false;
            SM.addAndPop(2);
        }
        if (mouse.isMiddleClicked())
            System.out.println(player.getPos().x + ", " + player.getPos().y);
    }

    @Override
    public void render(Graphics2D g) {
        TM.render(g);
        /*g.setColor(Color.RED);
        g.drawRect(GPhw, 0, 0, height);*/
        for (Enemy enemy: enemies) {
            enemy.render(g);
        }

        if (player.isNotDead())
            player.render(g);

        healing.render(g);

        if(trap.isNotDead())
            trap.render(g);

        for (Plasmaball fb: plasmaballs) {
            fb.render(g);
        }

        if (spawned && dragon.isNotDead())
        dragon.render(g);

        //Sprite.drawArray(g, font, "Game starting", new Vector2f(GPhw - (35*3), 30), 32, 32, 15, 0);
        Sprite.drawArray(g, font, GamePanel.oldFrameCount + " FPS", new Vector2f(width - 100, 13), 25, 25, 10, 0);
        Sprite.drawArray(g, font, StateManager.Score + " SCORE", new Vector2f(0, 53), 25, 25, 10, 0);
    }
}
