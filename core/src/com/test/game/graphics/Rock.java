package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * La classe Rock représente un rocher sur la carte du jeu.
 * Elle hérite des propriétés de la classe MapObjects.
 */
public class Rock extends MapObjects {

    // Nom du fichier de la texture du rocher
    private static final String TEXTURE_FILE_NAME = "Map/rock.png";

    // Texture du rocher
    private static final Texture textureRock = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    // Hauteur du rocher
    private static final float HEIGHT_ROCK = 40.f;

    // Largeur du rocher
    private static final float WIDTH_ROCK = 62.f;

    /**
     * Constructeur de la classe Rock.
     *
     * @param x la coordonnée x initiale du rocher
     * @param y la coordonnée y initiale du rocher
     */
    public Rock(int x, int y) {
        super(x, y, WIDTH_ROCK, HEIGHT_ROCK, textureRock);
    }
}
