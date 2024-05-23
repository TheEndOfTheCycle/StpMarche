package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Cette classe représente un projectile de type Bullets dans le jeu.
 */
public class Bullets extends Projectile {

    public static final int WIDTH = 23;
    public static final int HEIGHT = 5;

    /**
     * Constructeur de la classe Bullets.
     *
     * @param x     la position horizontale initiale du projectile
     * @param y     la position verticale initiale du projectile
     * @param speed la vitesse du projectile
     */
    public Bullets(float x, float y, float speed) {
        super(x, y, speed, "Amo/bullets.png");
    }

    /**
     * Met à jour la position du projectile Bullets en fonction du temps écoulé.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(float delta) {
        // On update la position du projectile par rapport au temps delta qui représente
        // l'écoulement depuis le dernier frame
        x += speed * delta;
    }

    /**
     * Met à jour la position du projectile Bullets pour le mouvement des ennemis.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    public void updateForEnemy(float delta) {
        // Déplacer le projectile vers la gauche
        x -= speed * delta;
    }

    /**
     * Dessine le projectile Bullets.
     *
     * @param batch le SpriteBatch utilisé pour dessiner le projectile
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }
}
