package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * La classe Panzer représente un tank sur la carte du jeu.
 * Elle hérite des propriétés de la classe MapObjects.
 */
public class Panzer extends MapObjects {

    // Nom du fichier de la texture du tank
    private static final String TEXTURE_FILE_NAME = "Map/panzer.png";

    // Texture du tank
    private static final Texture texturePanzer = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    // Hauteur du tank
    private static final float HEIGHT_PANZER = 40.f;

    // Largeur du tank
    private static final float WIDTH_PANZER = 118.f;

    /**
     * Constructeur de la classe Panzer.
     *
     * @param x la coordonnée x initiale du tank
     * @param y la coordonnée y initiale du tank
     */
    public Panzer(int x, int y) {
        super(x, y, WIDTH_PANZER, HEIGHT_PANZER, texturePanzer);
    }
}
