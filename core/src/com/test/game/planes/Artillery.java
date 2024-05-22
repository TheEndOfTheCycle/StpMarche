package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Artillery extends Plane {
    private float speed;
    private final static Texture TEXTURE_RED = new Texture(Gdx.files.internal("Amo/obus.png"));

    public Artillery(int x, int y, int height, int width, float speed) {
        super(x, y, width, height, TEXTURE_RED);
        setSpeed(speed);
    }

    @Override
    public void update() {
       setY( getY() - getSpeed() * Gdx.graphics.getDeltaTime()) ;
    }
   
    
    
}
