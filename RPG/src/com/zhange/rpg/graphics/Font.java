package com.zhange.rpg.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Converti des String en String fait pas le font donné
 */

public class Font {
    private BufferedImage FontSheet = null;
    private BufferedImage[][] fontArray;
    private final int Tile_Size = 32;
    public int w;
    public int h;
    private int wLetter;
    private int hLetter;

    public Font(String file) {
        w = Tile_Size;
        h = Tile_Size;

        System.out.println("Loading: " + file + "...");
        FontSheet = loadFont(file);

        wLetter = FontSheet.getWidth() / w;
        hLetter = FontSheet.getHeight() / h;
        loadFontArray();
    }

    public Font(String file, int w, int h) {
        this.w = w;
        this.h = h;
        System.out.println("Loading: " + file + "...");
        FontSheet = loadFont(file);

        wLetter = FontSheet.getWidth() / w;
        hLetter = FontSheet.getHeight() / h;
        loadFontArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setHeight(int height) {
        this.h = height;
        this.hLetter = FontSheet.getHeight() / h;
    }

    public void setWidth(int width) {
        this.w = width;
        this.wLetter = FontSheet.getWidth() / w;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    private BufferedImage loadFont(String file) {
        BufferedImage font = null;
        try {
            font = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("Error");
        }
        return font;
    }

    public void loadFontArray() {
        fontArray = new BufferedImage[wLetter][hLetter];
        for (int i = 0; i < wLetter; i++) {
            for (int j = 0; j < hLetter; j++) {
                fontArray[i][j] = getLetter(i, j);
            }
        }
    }

    public BufferedImage getFontSheet() {
        return FontSheet;
    }

    public BufferedImage getLetter(int x, int y) {
        return FontSheet.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage getFont(char letter) {
        int value = letter;

        int x = value % wLetter;
        int y = value / wLetter;

        return getLetter(x, y);
    }
}
