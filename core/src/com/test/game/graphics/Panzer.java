package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Panzer extends MapObjects {

    private static final String TEXTURE_FILE_NAME = "Map/panzer.png";
    private static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    private static final float HEIGHT_PANZER = 40.f;
    private static final float WIDTH_PANZER = 118.f;

    // Constructor for the Bush class
    public Panzer(int x, int y) {
        super(x, y, WIDTH_PANZER, HEIGHT_PANZER, texture);
    }
}
