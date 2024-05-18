package com.test.game.shoots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Projectile {
    protected float x, y;
    protected final float speed;
    protected Texture texture;

    public Projectile(float x, float y, float speed, String texturePath) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        texture = new Texture(Gdx.files.internal(texturePath));
    }

    public abstract void update(float delta);
    
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isOutOfScreen() {
        return x > Gdx.graphics.getWidth() || x < 0 || y > Gdx.graphics.getHeight() || y < 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void dispose() {
        texture.dispose();
    }

    // Méthode pour définir la position X du projectile
    public void setX(float x) {
        this.x = x;
    }
}
