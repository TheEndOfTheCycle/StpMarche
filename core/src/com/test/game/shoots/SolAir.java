package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Cette classe représente un projectile de type Sol-Air dans le jeu.
 */
public class SolAir extends Projectile {
    private final float angle;
    public int HEIGHT = 30;
    public int WIDTH = 30;

    /**
     * Constructeur de la classe SolAir.
     *
     * @param x      la position horizontale initiale du projectile
     * @param y      la position verticale initiale du projectile
     * @param speed  la vitesse du projectile
     * @param angle  l'angle de déplacement du projectile
     */
    public SolAir(float x, float y, float speed, float angle) {
        super(x, y, speed, "Amo/solair.png");
        this.angle = angle;
    }

    /**
     * Met à jour la position du projectile Sol-Air en fonction de l'angle et de la vitesse.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(float delta) {
        x += speed * Math.cos(angle) * delta; // le mouvement de la balle varie selon le calcul de l'angle
        y += speed * Math.sin(angle) * delta;
    }

    /**
     * Dessine le projectile Sol-Air.
     *
     * @param batch le SpriteBatch utilisé pour dessiner le projectile
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, getWidth(), getHeight());
    }
}
