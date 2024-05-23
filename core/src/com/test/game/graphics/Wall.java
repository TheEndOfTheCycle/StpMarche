package com.test.game.graphics;

import com.badlogic.gdx.graphics.Texture;

/**
 * La classe Wall représente un mur sur la carte du jeu.
 * Elle hérite des propriétés de la classe MapObjects.
 */
public class Wall extends MapObjects {
    
    // Largeur du mur
    public static final int WIDTH = 14;
    
    // Hauteur du mur
    public static final int HEIGHT = 14;

    /**
     * Constructeur de la classe Wall.
     *
     * @param x la coordonnée x initiale du mur
     * @param y la coordonnée y initiale du mur
     * @param texture la texture du mur
     */
    public Wall(int x, int y, Texture texture) {
        super(x, y, WIDTH, HEIGHT, texture);
    }
}
