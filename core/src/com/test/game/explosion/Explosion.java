package com.test.game.explosion;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * La classe Explosion représente une animation d'explosion dans le jeu.
 * Elle gère la position, le temps écoulé et l'état de l'animation.
 */
public class Explosion {
    // La coordonnée x de l'explosion
    private float x;

    // La coordonnée y de l'explosion
    private final float y;

    // Le temps écoulé depuis le début de l'animation
    private float elapsedTime;

    // L'animation de l'explosion
    private final Animation<TextureRegion> animation;

    // Indique si l'animation est terminée
    private boolean finished;

    /**
     * Construit une nouvelle Explosion avec la position initiale et l'animation spécifiées.
     *
     * @param x la coordonnée x initiale de l'explosion
     * @param y la coordonnée y initiale de l'explosion
     * @param animation l'animation de l'explosion
     */
    public Explosion(float x, float y, Animation<TextureRegion> animation) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.elapsedTime = 0f;
        this.finished = false;
    }

    /**
     * Met à jour la position et l'état de l'animation de l'explosion.
     * 
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    public void update(float delta) {
        x -= 100 * delta;
        elapsedTime += delta;
        if (animation.isAnimationFinished(elapsedTime)) {
            finished = true;
        }
    }

    /**
     * Dessine l'animation de l'explosion à l'écran en utilisant le SpriteBatch fourni.
     * 
     * @param batch le SpriteBatch utilisé pour dessiner l'animation
     */
    public void draw(SpriteBatch batch) {
        if (!finished) {
            // Ajouter le décalage de défilement à la position X de l'explosion
            TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, false);
            batch.draw(currentFrame, x, y, 50, 30);
        }
    }

    /**
     * Vérifie si l'animation de l'explosion est terminée.
     * 
     * @return true si l'animation est terminée, false sinon
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Retourne la coordonnée x de l'explosion.
     * 
     * @return la coordonnée x de l'explosion
     */
    public float getX() {
        return x;
    }

    /**
     * Retourne la coordonnée y de l'explosion.
     * 
     * @return la coordonnée y de l'explosion
     */
    public float getY() {
        return y;
    }
}
