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

public class StartMenuState extends State {

    private Font font;
    private GamePanel GP;
    private int lineHeight = 80;
    private int margin_top = 120;
    private int width = GamePanel.width;
    private int height = GamePanel.height;
    private int GPhw = width/2;
    private int GPhh = height/2;
    private int recSolo = GPhh + margin_top - lineHeight * 3;
    private int recHelp = GPhh + margin_top - lineHeight * 2;
    private int recExit = GPhh + margin_top - lineHeight;
    private boolean hoverSolo;
    private boolean hoverMulti;
    private boolean hoverExit;
    private BufferedImage img;

    public StartMenuState(StateManager SM) {
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

    //System.out.println("Position :" + mouse.getX() + ", " + mouse.getY());
    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (mouse.isRightPressed())
        System.out.println(mouse.getX() + ", " + mouse.getY());

        hoverSolo = false;
        hoverMulti = false;
        hoverExit = false;
        // Solo
        if (mouse.getX() > GPhw - (40) && mouse.getY() > recSolo)
            if (mouse.getX() < GPhw - (40) + 38*2 && mouse.getY() < recSolo + 35) {
                hoverSolo = true;
                if (mouse.isLeftPressed()) {
                    SM.addAndPop(0);
                }
            }
        //Multi
        if (mouse.getX() > GPhw - (40) && mouse.getY() > recHelp)
            if (mouse.getX() < GPhw - (40) + 38*2 && mouse.getY() < recHelp + 35) {
                hoverMulti = true;
                if (mouse.isLeftPressed()) {
                    SM.addAndPop(5);
                }
            }
        // Exit
        if (mouse.getX() > GPhw - (40) && mouse.getY() > recExit)
            if (mouse.getX() < GPhw - (40) + 38*2 && mouse.getY() < recExit + 35) {
                hoverExit = true;
                if (mouse.isLeftPressed()) {
                    System.exit(0);
                }
            }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, 0,0, GamePanel.width, GamePanel.height, null);
        g.setColor(new Color(112, 112, 112));
        g.fillRoundRect(GPhw - (50), recSolo+1, 38*2+16, 37, 10, 10);
        g.fillRoundRect(GPhw - (50), recHelp +1, 38*2+16, 37, 10, 10);
        g.fillRoundRect(GPhw - (50), recExit+1, 38*2+16, 37, 10, 10);
        g.setColor(Color.RED);
        //g.drawRect(GPhw, 0, 0, GamePanel.height);
        g.fillRoundRect(GPhw - (47), recSolo, 38*2+14, 35, 10, 10);
        g.fillRoundRect(GPhw - (47), recHelp, 38*2+14, 35, 10, 10);
        g.fillRoundRect(GPhw - (47), recExit, 38*2+14, 35, 10, 10);
        if(hoverSolo) {
            g.setColor(new Color(133, 13, 13));
            g.fillRoundRect(GPhw - (47), recSolo, 38*2+14, 35, 10, 10);
        }
        if(hoverMulti) {
            g.setColor(new Color(133, 13, 13));
            g.fillRoundRect(GPhw - (47), recHelp, 38*2+14, 35, 10, 10);
        }
        if(hoverExit) {
            g.setColor(new Color(133, 13, 13));
            g.fillRoundRect(GPhw - (47), recExit, 38*2+14, 35, 10, 10);
        }

        Sprite.drawArray(g, font, "RPG Title", new Vector2f(GPhw - (35*3), 30), 48 , 48, 20,0);
        Sprite.drawArray(g, font, "Solo", new Vector2f(GPhw - (40), recSolo), 32 , 32, 15,0);
        Sprite.drawArray(g, font, "Help", new Vector2f(GPhw - (40), recHelp), 32 , 32, 15,0);
        Sprite.drawArray(g, font, "Exit", new Vector2f(GPhw - (40), recExit), 32 , 32, 15   ,0);
        Sprite.drawArray(g, font, "@Copyright IDK Incorporation", new Vector2f(GPhw - (29*5), GamePanel.height - 20), 16 , 16, 10,0);

    }
}
