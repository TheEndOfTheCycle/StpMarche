package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bush extends MapObjects {

    private static final String TEXTURE_FILE_NAME = "Map/bush.png";
    private static final Texture textureBush = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    private static final float HEIGHT_BUSH = 40.f;
    private static final float WIDTH_BUSH = 118.f;

    // Constructor for the Bush class
    public Bush(int x, int y) {
        super(x, y, WIDTH_BUSH, HEIGHT_BUSH, textureBush);
    }
}
