package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.Test;

public class WallParser {
    private static final char WALL_SYMBOL = '█';
    private static final char HEART_SYMBOL = '♥';
    private static final char GRASS_SYMBOL = '▓';
    private static final char CLUB_SYMBOL = '♣';
    private static final char BUSH_SYMBOL = '$';
    private static final char AROBASE_SYMBOL = '@';
    private static final char STAR_SYMBOL = '*';
    private static final char SUP_SYMBOL = '>';

    private final Test game;
    private static final String CARET1 = "Map/LatestMap.txt";
    private static final String CARET2 = "Map/carte3.txt";
    private static final String CARET3 = "Map/MapBoss.txt";

    public WallParser(Test game) {
        this.game = game;
    }

    // Méthode pour analyser le fichier de carte et créer des objets de type Wall et
    // Zeppelin
    public static Array<Object> parseWalls(String fileName) {
        Array<Object> objects = new Array<>();

        FileHandle fileHandle = Gdx.files.internal(fileName);
        String[] lines = fileHandle.readString().split("\\r?\\n");

        Texture dirtTexture = new Texture("Map/dirt.png");
        Texture grassTexture1 = new Texture("Map/grass.jpg");
        Texture grassTexture3 = new Texture("Map/Lava.jpg");
        Texture grassTexture2 = new Texture("Map/yellow.jpg");

        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                char symbol = line.charAt(x);
                int xPos = x * Wall.WIDTH;
                int yPos = (lines.length - 1 - y) * Wall.HEIGHT;
                switch (symbol) {
                    case WALL_SYMBOL:
                        objects.add(new Wall(xPos, yPos, dirtTexture));
                        break;
                    case GRASS_SYMBOL:
                        if (fileName == CARET1) {
                            objects.add(new Wall(xPos, yPos, grassTexture1));
                        } else if (fileName == CARET2) {
                            objects.add(new Wall(xPos, yPos, grassTexture2));

                        } else {
                            objects.add(new Wall(xPos, yPos, grassTexture3));
                        }
                        break;
                    case HEART_SYMBOL:
                        objects.add(new Zeppelin(xPos, yPos));
                        break;
                    case CLUB_SYMBOL:
                        objects.add(new AntiAir(xPos, yPos));
                        break;
                    case BUSH_SYMBOL:
                        objects.add(new Bush(xPos, yPos));
                        break;
                    case AROBASE_SYMBOL:
                        objects.add(new Arbre(xPos, yPos));
                        break;
                    case STAR_SYMBOL:
                        objects.add(new Panzer(xPos, yPos));
                        break;
                    case SUP_SYMBOL:
                        objects.add(new Rock(xPos, yPos));
                        break;
                    default:
                        break;
                }
            }
        }

        return objects;
    }

    // Méthode pour calculer la largeur totale de la carte
    public static int calculateMapWidth(String fileName) {
        FileHandle fileHandle = Gdx.files.internal(fileName);
        String[] lines = fileHandle.readString().split("\\r?\\n");
        return lines[0].length() * Wall.WIDTH;
    }
}