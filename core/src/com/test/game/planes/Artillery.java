package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Cette classe représente un avion d'artillerie dans le jeu.
 */
public class Artillery extends Plane {
    private float speed;
    private final static Texture TEXTURE_RED = new Texture(Gdx.files.internal("Amo/obus.png"));

    /**
     * Constructeur de la classe Artillery.
     *
     * @param x      la position horizontale initiale de l'avion
     * @param y      la position verticale initiale de l'avion
     * @param height la hauteur de l'avion
     * @param width  la largeur de l'avion
     * @param speed  la vitesse de déplacement de l'avion
     */
    public Artillery(int x, int y, int height, int width, float speed) {
        super(x, y, width, height, TEXTURE_RED);
        setSpeed(speed);
    }

    /**
     * Met à jour la position de l'avion d'artillerie en fonction de sa vitesse.
     */
    @Override
    public void update() {
       setY(getY() - getSpeed() * Gdx.graphics.getDeltaTime());
    }
    
    /**
     * Obtient la vitesse de l'avion d'artillerie.
     *
     * @return la vitesse de l'avion d'artillerie
     */
    private float getSpeed() {
        return speed;
    }

    /**
     * Définit la vitesse de l'avion d'artillerie.
     *
     * @param speed la nouvelle vitesse de l'avion d'artillerie
     */
    private void setSpeed(float speed) {
        this.speed = speed;
    }
}
