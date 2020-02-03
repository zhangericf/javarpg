package com.zhange.rpg.tiles;


import com.zhange.rpg.graphics.Sprite;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Lis la map
 */

public class TileManager {

    public static ArrayList<TileMap> TM;
    public static int blockSize = 48;

    public TileManager() {
        TM = new ArrayList<TileMap>();
    }

    public TileManager(String path) {
        TM = new ArrayList<TileMap>();
        addTileMap(path, blockSize, blockSize);
    }

    private void addTileMap(String path, int blockWidth, int blockHeight) {

        String imagePath;
        String imagePathTSX;
        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers = 0;
        Sprite sprite;
        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePathTSX = eElement.getAttribute("source");
            imagePath = imagePathTSX.substring(0, imagePathTSX.length() - 4);

            list = doc.getElementsByTagName("map");
            node = list.item(0);
            eElement = (Element) node;

            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));

            sprite = new Sprite("Map/"+ imagePath + ".png", tileWidth, tileHeight);

            tileColumns = sprite.getSpriteSheetWidth() / tileWidth;
            tileCount = tileColumns * (sprite.getSpriteSheetHeight() / tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();
            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if (i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }
                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();
                if (i >= 1) {
                    TM.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                } else {
                    TM.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }

            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void render(Graphics2D g) {

        int xStart = 0, xEnd = 0, yStart = 0, yEnd = 0;

        for (int i = 0; i < TM.size(); i++) {
            TM.get(i).render(g);
        }
    }
}
