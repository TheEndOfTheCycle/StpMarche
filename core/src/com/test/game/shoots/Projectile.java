package com.test.game.shoots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Cette classe abstraite représente un projectile dans le jeu.
 */
public abstract class Projectile {
    protected float x, y;
    protected final float speed;
    protected Texture texture;
    protected boolean hit = false; // cette variable permet de vérifier si un projectile a touché sa cible ou pas
    private final static int WIDTH = 40;
    private final static int HEIGHT = 40;

    /**
     * Constructeur de la classe Projectile.
     *
     * @param x           la position horizontale initiale du projectile
     * @param y           la position verticale initiale du projectile
     * @param speed       la vitesse du projectile
     * @param texturePath le chemin vers la texture du projectile
     */
    public Projectile(float x, float y, float speed, String texturePath) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        texture = new Texture(Gdx.files.internal(texturePath));
    }

    /**
     * Met à jour la position du projectile en fonction du temps écoulé.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    public abstract void update(float delta);

    /**
     * Dessine le projectile.
     *
     * @param batch le SpriteBatch utilisé pour dessiner le projectile
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    /**
     * Vérifie si le projectile est sorti de l'écran.
     *
     * @return true si le projectile est sorti de l'écran, sinon false
     */
    public boolean isOutOfScreen() {
        return x > Gdx.graphics.getWidth() || x < 0 || y > Gdx.graphics.getHeight() || y < 0;
    }

    // Méthodes getters et setters

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void dispose() {
        texture.dispose();
    }

    public void setHit(boolean etat) {
        this.hit = etat;
    }

    public boolean getHit() {
        return this.hit;
    }

    public void setX(float x) {
        this.x = x;
    }
}
