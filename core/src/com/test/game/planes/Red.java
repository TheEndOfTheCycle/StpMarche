package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Red extends Plane {

    private final static int RED_WIDTH = 78;
    private final static int RED_HEIGHT = 30;
    private final static Texture TEXTURE_RED = new Texture(Gdx.files.internal("Planes/red_baron.png"));
    

    // Constructeur de la classe
    public Red(int x, int y) {
        super(x, y,  RED_WIDTH, RED_HEIGHT, TEXTURE_RED);
        setHp(3);
    }
   
}
