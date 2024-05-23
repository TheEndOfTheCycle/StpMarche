package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Cette classe représente un projectile de type Trump dans le jeu.
 */
public class Trump extends Projectile {
    private final float angle;

    private final static int WIDTH = 80;
    private final static int HEIGHT = 80;

    /**
     * Constructeur de la classe Trump.
     *
     * @param x      la position horizontale initiale du projectile
     * @param y      la position verticale initiale du projectile
     * @param speed  la vitesse du projectile
     * @param angle  l'angle de déplacement du projectile
     */
    public Trump(float x, float y, float speed, float angle) {
        super(x, y, speed, "Amo/trump.png");
        this.angle = angle;
    }

    /**
     * Met à jour la position du projectile Trump en fonction de l'angle et de la vitesse.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(float delta) {
        x += speed * Math.cos(angle) * delta;
        y += speed * Math.sin(angle) * delta;
    }

    /**
     * Dessine le projectile Trump.
     *
     * @param batch le SpriteBatch utilisé pour dessiner le projectile
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

}
