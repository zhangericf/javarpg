package com.zhange.rpg.states;

import com.zhange.rpg.GamePanel;
import com.zhange.rpg.graphics.Font;
import com.zhange.rpg.graphics.Sprite;
import com.zhange.rpg.util.KeyHandler;
import com.zhange.rpg.util.MouseHandler;
import com.zhange.rpg.math.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PauseState extends State {

    private Font font;
    private int lineHeight = 80;
    private int margin_top = 100;
    private int width = GamePanel.width;
    private int height = GamePanel.height;
    private int GPhw = width/2;
    private int GPhh = height/2 - 90;
    private boolean hoverRestart, hoverMainMenu;
    private BufferedImage img;

    public PauseState(StateManager SM) {
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
        hoverRestart = false;
        if (mouse.getX() > GPhw - (68) && mouse.getY() > GPhh)
                if (mouse.getX() < GPhw - (68) + 37*3+16 && mouse.getY() < GPhh + 35) {
                    hoverRestart = true;
                    if (mouse.isLeftPressed())
                        PlayState.spawned = false;
                        SM.addAndPop(0);
                }
        hoverMainMenu = false;
        if (mouse.getX() > GPhw - (86) && mouse.getY() > GPhh + 90)
            if (mouse.getX() < GPhw - (86) + 37*3+53 && mouse.getY() < GPhh + 90 + 35) {
                hoverMainMenu = true;
                if (mouse.isLeftPressed())
                    SM.addAndPop(4);
            }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, 0,0, GamePanel.width, GamePanel.height, null);
        g.setColor(new Color(0, 0, 0, 34));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(112, 112, 112));
        g.fillRoundRect(GPhw - (68), GPhh+1, 37*3+16, 35+2, 10, 10);
        g.setColor(Color.RED);
        //g.drawRect(GPhw, 0, 0, height);
        g.fillRoundRect(GPhw - (65), GPhh, 37*3+16, 35, 10, 10);
        if(hoverRestart) {
            g.setColor(new Color(133, 13, 13));
            g.fillRoundRect(GPhw - (65), GPhh, 37*3+16, 35, 10, 10);
        }
        g.setColor(new Color(112, 112, 112));
        g.fillRoundRect(GPhw - (86), GPhh+90+1, 37*3+53, 35+2, 10, 10);
        g.setColor(Color.RED);
        //g.drawRect(GPhw, 0, 0, height);
        g.fillRoundRect(GPhw - (83), GPhh+90, 37*3+53, 35, 10, 10);
        if(hoverMainMenu) {
            g.setColor(new Color(133, 13, 13));
            g.fillRoundRect(GPhw - (83), GPhh+90, 37*3+53, 35, 10, 10);
        }
        Sprite.drawArray(g, font, "Game paused", new Vector2f(GPhw - (32*3), 30), 32, 32, 15, 0);
        Sprite.drawArray(g, font, "Restart", new Vector2f(GPhw - (62), GPhh), 32, 32, 15, 0);
        Sprite.drawArray(g, font, "Main menu", new Vector2f(GPhw - (80), GPhh + 90), 32, 32, 15, 0);

    }
}
