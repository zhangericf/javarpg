package com.zhange.rpg.graphics;

import com.zhange.rpg.math.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Load les sprite sheet et cherche dedans
 */

public class Sprite {
    private BufferedImage SpriteSheet;
    private BufferedImage[][] spriteArray;
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;

    public Sprite(String file) {

        int tileSize = 32;

        w = tileSize;
        h = tileSize;

        System.out.println("Loading: " + file + "...");
        SpriteSheet = loadSprite(file);

        wSprite = SpriteSheet.getWidth() / w;
        hSprite = SpriteSheet.getHeight() / h;
        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {
        this.w = w;
        this.h = h;
        System.out.println("Loading: " + file + "...");
        SpriteSheet = loadSprite(file);

        wSprite = SpriteSheet.getWidth() / w;
        hSprite = SpriteSheet.getHeight() / h;
        loadSpriteArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setHeight(int height) {
        this.h = height;
        this.hSprite = SpriteSheet.getHeight() / h;
    }

    public void setWidth(int width) {
        this.w = width;
        this.wSprite = SpriteSheet.getWidth() / w;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int getSpriteSheetWidth() { return SpriteSheet.getWidth(); }
    public int getSpriteSheetHeight() { return SpriteSheet.getHeight(); }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file)));
        } catch (Exception e) {
            System.out.println("Error");
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];
            for (int j = 0; j < hSprite; j++) {
                for (int i = 0; i < wSprite; i++) {
                    spriteArray[j][i] = getSprite(i, j);
            }
        }
    }

    public BufferedImage getSpriteSheet() {
        return SpriteSheet;
    }

    public BufferedImage getSprite(int x, int y) {
        return SpriteSheet.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public BufferedImage[][] getSpriteArray2(int i) {
        return spriteArray;
    }

    public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for (BufferedImage bufferedImage : img) {
            if (bufferedImage != null) {
                g.drawImage(bufferedImage, (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

    public static void drawArray(Graphics2D g, Font f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) != 32) {
                g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }

    }
}
