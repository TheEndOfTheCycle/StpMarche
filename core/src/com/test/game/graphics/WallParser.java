package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.Test;

/**
 * La classe WallParser est responsable de l'analyse des fichiers de carte et de la création des objets correspondants.
 * Elle analyse les symboles dans le fichier et crée des objets de type Wall et d'autres objets en fonction de ces symboles.
 */
public class WallParser {
    // Symboles utilisés dans les fichiers de carte
    private static final char WALL_SYMBOL = '█';
    private static final char HEART_SYMBOL = '♥';
    private static final char GRASS_SYMBOL = '▓';
    private static final char CLUB_SYMBOL = '♣';
    private static final char BUSH_SYMBOL = '$';
    private static final char AROBASE_SYMBOL = '@';
    private static final char STAR_SYMBOL = '*';
    private static final char SUP_SYMBOL = '>';

    // Chemins vers les fichiers de carte
    private static final String CARET1 = "Map/LatestMap.txt";
    private static final String CARET2 = "Map/carte3.txt";

    // Instance du jeu
    protected final Test game;

    /**
     * Constructeur de la classe WallParser.
     *
     * @param game instance du jeu
     */
    public WallParser(Test game) {
        this.game = game;
    }

    /**
     * Analyse le fichier de carte spécifié et crée des objets correspondants.
     *
     * @param fileName nom du fichier de carte à analyser
     * @return un tableau d'objets représentant les éléments de la carte
     */
    public static Array<Object> parseWalls(String fileName) {
        Array<Object> objects = new Array<>();

        FileHandle fileHandle = Gdx.files.internal(fileName);
        String[] lines = fileHandle.readString().split("\\r?\\n");

        // Textures pour les différents types de sol
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
                        switch (fileName) {
                            case CARET1:
                                objects.add(new Wall(xPos, yPos, grassTexture1));
                                break;
                            case CARET2:
                                objects.add(new Wall(xPos, yPos, grassTexture2));
                                break;
                            default:
                                objects.add(new Wall(xPos, yPos, grassTexture3));
                                break;
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

    /**
     * Calcule la largeur totale de la carte spécifiée.
     *
     * @param fileName nom du fichier de carte
     * @return la largeur totale de la carte
     */
    public static int calculateMapWidth(String fileName) {
        // Récupère le gestionnaire de fichier correspondant au nom de fichier spécifié
        FileHandle fileHandle = Gdx.files.internal(fileName);
        
        // Lit le contenu du fichier et le divise en lignes
        String[] lines = fileHandle.readString().split("\\r?\\n");
        
        // Retourne la largeur de la première ligne multipliée par la largeur d'une tuile de mur
        return lines[0].length() * Wall.WIDTH;
    }

}
