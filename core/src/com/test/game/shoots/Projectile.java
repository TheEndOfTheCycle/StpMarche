package com.test.game.shoots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Projectile {
    protected float x, y;
    protected final float speed;
    protected Texture texture;
    protected boolean hit = false;// cette variable permet de vérifier si un projectile a touchee sa cible ou pas

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

    public boolean isOutOfScreen() {// on verifie si le projectile sort de l'ecran
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

    public void setHit(boolean etat) {//
        this.hit = etat;
    }

    public boolean getHit() {
        return this.hit;
    }

    // Méthode pour définir la position X du projectile
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float x) {
        this.y = y;
    }
}
