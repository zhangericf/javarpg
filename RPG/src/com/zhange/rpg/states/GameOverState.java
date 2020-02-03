package com.zhange.rpg.states;

import com.zhange.rpg.GamePanel;
import com.zhange.rpg.graphics.Font;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.math.Vector2f;
import com.zhange.rpg.util.KeyHandler;
import com.zhange.rpg.util.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameOverState extends State {

    private Font font;
    private int lineHeight = 80;
    private int margin_top = 120;
    private int width = GamePanel.width;
    private int height = GamePanel.height;
    private int GPhw = width/2;
    private int GPhh = height/2;
    private int recSolo = GPhh + margin_top - lineHeight * 3;
    private int recMulti = GPhh + margin_top - lineHeight * 2;
    private int recExit = GPhh + margin_top - lineHeight;
    private boolean hoverSolo;
    private boolean hoverMulti;
    private boolean hoverExit;
    private BufferedImage img;

    public GameOverState(StateManager SM) {
        super(SM);
        font = new Font("Font/x05mo.png", 16, 16);
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Background/GameOver.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double time) {
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(key.escape.down) {
            PlayState.spawned = false;
            PlayState.bossKiled = false;
            SM.addAndPop(4);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, 0,0, GamePanel.width, GamePanel.height, null);
        Sprite.drawArray(g, font, "<-- Esc to main menu", new Vector2f(30, 30), 30 , 30, 15,0);
        /*g.setColor(Color.RED);
        g.drawRect(GPhw, 0, 0, GamePanel.height);*/
        Sprite.drawArray(g, font, "Score : " + (StateManager.Score), new Vector2f(GPhw - (100), recExit+80), 32 , 32, 15   ,0);
    }
}
