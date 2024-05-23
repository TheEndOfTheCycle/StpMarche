package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * La classe Arbre représente un arbre sur la carte du jeu.
 * Elle hérite des propriétés de la classe MapObjects.
 */
public class Arbre extends MapObjects {

    // Nom du fichier de la texture de l'arbre
    private static final String TEXTURE_FILE_NAME = "Map/tree.png";

    // Texture de l'arbre
    private static final Texture textureArbre = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    // Hauteur de l'arbre
    private static final float HEIGHT_TREE = 100.f;

    // Largeur de l'arbre
    private static final float WIDTH_TREE = 83.f;

    /**
     * Constructeur de la classe Arbre.
     *
     * @param x la coordonnée x initiale de l'arbre
     * @param y la coordonnée y initiale de l'arbre
     */
    public Arbre(int x, int y) {
        super(x, y, WIDTH_TREE, HEIGHT_TREE, textureArbre);
    }
}
