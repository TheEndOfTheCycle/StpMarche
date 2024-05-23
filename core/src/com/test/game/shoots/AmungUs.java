package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Cette classe représente un projectile de type AmungUs dans le jeu.
 */
public class AmungUs extends Projectile {

    /**
     * Constructeur de la classe AmungUs.
     *
     * @param x     la position horizontale initiale du projectile
     * @param y     la position verticale initiale du projectile
     * @param speed la vitesse du projectile
     */
    public AmungUs(float x, float y, float speed) {
        super(x, y, speed, "Amo/amungUs.png");
    }

    /**
     * Met à jour la position du projectile AmungUs en fonction du temps écoulé.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(float delta) {
        // Cette méthode peut être utilisée pour les projectiles tirés par le joueur
        x -= speed * delta;
    }

    /**
     * Dessine le projectile AmungUs.
     *
     * @param batch le SpriteBatch utilisé pour dessiner le projectile
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, getWidth(), getHeight());
    }

}
