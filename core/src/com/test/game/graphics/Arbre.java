package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Arbre extends MapObjects {

    private static final String TEXTURE_FILE_NAME = "Map/tree.png";
    private static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    private static final float HEIGHT_TREE = 100.f;
    private static final float WIDTH_TREE = 83.f;

    // Constructor for the Bush class
    public Arbre(int x, int y) {
        super(x, y, WIDTH_TREE, HEIGHT_TREE, texture);
    }
}
