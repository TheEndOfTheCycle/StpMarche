package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Cette classe représente un projectile de type Tate dans le jeu.
 */
public class Tate extends Projectile {

    private final float Yspeed = 50;
    private final float OriginalAltitude;
    private final float VariableAltitude = 50;
    private final float MAX;
    private final float MIN;
    private boolean movingUp;

    /**
     * Constructeur de la classe Tate.
     *
     * @param x     la position horizontale initiale du projectile
     * @param y     la position verticale initiale du projectile
     * @param speed la vitesse du projectile
     */
    public Tate(float x, float y, float speed) {
        super(x, y, speed, "Amo/Andrew.png");
        OriginalAltitude = y;
        MAX = OriginalAltitude + VariableAltitude;
        MIN = OriginalAltitude - VariableAltitude;
        movingUp = true;
    }

    /**
     * Met à jour la position du projectile Tate en effectuant un déplacement vertical en zigzag.
     *
     * @param deltaTime le temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(float deltaTime) {
        // Déplacement vertical en zigzag
        if (movingUp) {
            y += Yspeed * deltaTime;
            if (y >= MAX) {
                movingUp = false;
            }
        } else {
            y -= Yspeed * deltaTime;
            if (y <= MIN) {
                movingUp = true;
            }
        }

        // Déplacement horizontal vers la gauche
        x -= speed * deltaTime;
    }

    /**
     * Dessine le projectile Tate.
     *
     * @param batch le SpriteBatch utilisé pour dessiner le projectile
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, getWidth(), getHeight());
    }
}
