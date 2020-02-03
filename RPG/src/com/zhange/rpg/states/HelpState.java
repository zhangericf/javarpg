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

public class HelpState extends State {

    private Font font;
    private GamePanel GP;
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

    public HelpState(StateManager SM) {
        super(SM);
        font = new Font("Font/x05mo.png", 16, 16);
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Background/bg.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double time) {
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(key.escape.down)
            SM.addAndPop(4);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, 0,0, GamePanel.width, GamePanel.height, null);
       /* g.setColor(Color.RED);
        g.drawRect(GPhw, 0, 0, GamePanel.height);*/
        Sprite.drawArray(g, font, "<-- Esc", new Vector2f(30, 30), 30 , 30, 15,0);
        Sprite.drawArray(g, font, "Help", new Vector2f(GPhw - (28*2), 30), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "---------------------------------------------", new Vector2f(GPhw - 450, 80+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "W : Move forward   S : Move backward", new Vector2f(GPhw - 350, 120+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "A : Move left    D : Move right    J : Attack", new Vector2f(GPhw - 450, 180+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "---------------------------------------------", new Vector2f(GPhw - 450, 220+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "Hello, in this game you'll have 3 lives", new Vector2f(GPhw - 400, 260+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "When you kill a monster you'll gain points", new Vector2f(GPhw - 423, 320+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "At 100 points the boss will spawn", new Vector2f(GPhw - 360, 380+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "To win at this game you have to kill him", new Vector2f(GPhw - 410, 440+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "---------------------------------------------", new Vector2f(GPhw - 450, 480+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "The Blood cup can heal if you go near it", new Vector2f(GPhw - 405, 520+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "---------------------------------------------", new Vector2f(GPhw - 450, 560+50), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "Have fun playing !", new Vector2f(GPhw - 200, 640+50), 48 , 48, 20,0);

    }
}
