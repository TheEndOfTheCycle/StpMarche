package com.test.game.graphics;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends MapObjects {
    public static final int WIDTH = 14;
    public static final int HEIGHT = 14;

    // Constructeur de la classe
    public Wall(int x, int y, Texture texture) {
        super(x, y, WIDTH, HEIGHT, texture);
    }
}
