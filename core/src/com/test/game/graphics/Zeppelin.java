package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.graphics.MapObjects;


public class Zeppelin extends MapObjects {

    private static final String TEXTURE_FILE_NAME = "Map/zeppelin.png";
    private static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    private static final float HEIGHT_ZEPPELIN = 160.f;
    private static final float WIDTH_ZEPPELIN = 50.f;

    // Constructeur de la classe
    public Zeppelin(int x, int y) {
        super(x, y, HEIGHT_ZEPPELIN, WIDTH_ZEPPELIN, texture);
    }
}
