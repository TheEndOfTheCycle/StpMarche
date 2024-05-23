package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Rock extends MapObjects {

    private static final String TEXTURE_FILE_NAME = "Map/rock.png";
    private static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    private static final float HEIGHT_BUSH = 40.f;
    private static final float WIDTH_BUSH = 62.f;

    // Constructor for the Bush class
    public Rock(int x, int y) {
        super(x, y, WIDTH_BUSH, HEIGHT_BUSH, texture);
    }
}
