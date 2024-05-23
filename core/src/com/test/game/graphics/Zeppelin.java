package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * La classe Zeppelin représente un zeppelin sur la carte du jeu.
 * Elle hérite des propriétés de la classe MapObjects.
 */
public class Zeppelin extends MapObjects {

    // Nom du fichier de la texture du zeppelin
    private static final String TEXTURE_FILE_NAME = "Map/zeppelin.png";

    // Texture du zeppelin
    private static final Texture textureZepplin = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    // Hauteur du zeppelin
    private static final float HEIGHT_ZEPPELIN = 160.f;

    // Largeur du zeppelin
    private static final float WIDTH_ZEPPELIN = 50.f;

    /**
     * Constructeur de la classe Zeppelin.
     *
     * @param x la coordonnée x initiale du zeppelin
     * @param y la coordonnée y initiale du zeppelin
     */
    public Zeppelin(int x, int y) {
        super(x, y, HEIGHT_ZEPPELIN, WIDTH_ZEPPELIN, textureZepplin);
    }
}
