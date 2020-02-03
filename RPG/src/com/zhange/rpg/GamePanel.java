package com.zhange.rpg;

import com.zhange.rpg.states.StateManager;
import com.zhange.rpg.util.KeyHandler;
import com.zhange.rpg.util.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * The game loop
 */

public class GamePanel extends JPanel implements Runnable {

    public static final long serialVersionUID = 1L;

    public static int width;
    public static int height;
    public static int oldFrameCount;

    private Thread thread;
    private boolean running = false;

    private BufferedImage img;
    private Graphics2D g;

    private MouseHandler mouse;
    private KeyHandler key;

    private StateManager GSM;

    public GamePanel(int width, int height) {
        GamePanel.width = width;
        GamePanel.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        running = true;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();

        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        GSM = new StateManager();
    }

    public void run() {
        init();

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ; // Time Before Update

        final int MUBR = 3; // Must Update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        oldFrameCount = 0;

        while (running) {

            double now = System.nanoTime();
            int updateCount = 0;
            while (((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
                update(now);
                input(mouse, key);
                lastUpdateTime += TBU;
                updateCount++;
            }

            if ((now - lastUpdateTime) > TBU) {
                lastUpdateTime = now - TBU;
            }

            input(mouse, key);
            render();
            draw();
            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            // 1 second loop
            if (thisSecond > lastSecondTime) {
                if (frameCount != oldFrameCount) {
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("ERROR: yielding thread");
                }

                now = System.nanoTime();
            }

        }
    }

    public void update(double time) {
        GSM.update(time);
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        GSM.input(mouse, key);
    }

    public void render() {
        if (g != null) {
            GamePanel.height = getHeight();
            GamePanel.width = getWidth();
            g.setColor(new Color(81, 172, 244));
            g.fillRect(0, 0, getWidth(), getHeight());
            GSM.render(g);
        }
    }

    public void draw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
    }

}