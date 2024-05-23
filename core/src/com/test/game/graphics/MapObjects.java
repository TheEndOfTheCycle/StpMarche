package com.test.game.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * La classe abstraite MapObjects représente un objet de la carte du jeu.
 * Elle gère la position, les dimensions et la texture de l'objet.
 */
public abstract class MapObjects {

    // La forme rectangulaire de l'objet de la carte
    protected final Rectangle shape;
    
    // La texture de l'objet de la carte
    protected final Texture texture;

    /**
     * Constructeur de la classe MapObjects.
     *
     * @param x la coordonnée x initiale de l'objet
     * @param y la coordonnée y initiale de l'objet
     * @param width la largeur de l'objet
     * @param height la hauteur de l'objet
     * @param texture la texture de l'objet
     */
    public MapObjects(float x, float y, float width, float height, Texture texture) {
        this.shape = new Rectangle(x, y, width, height);
        this.texture = texture;
    }

    /**
     * Dessine l'objet sur l'écran en utilisant le SpriteBatch fourni.
     *
     * @param batch le SpriteBatch utilisé pour dessiner l'objet
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
    }

    // ----- getters et setters -----

    /**
     * Retourne la coordonnée x de l'objet.
     *
     * @return la coordonnée x de l'objet
     */
    public float getX() {
        return shape.x;
    }

    /**
     * Retourne la coordonnée y de l'objet.
     *
     * @return la coordonnée y de l'objet
     */
    public float getY() {
        return shape.y;
    }

    /**
     * Définit la coordonnée x de l'objet.
     *
     * @param x la nouvelle coordonnée x de l'objet
     */
    public void setX(float x) {
        shape.x = x;
    }

    /**
     * Définit la coordonnée y de l'objet.
     *
     * @param y la nouvelle coordonnée y de l'objet
     */
    public void setY(float y) {
        shape.y = y;
    }

    /**
     * Retourne la largeur de l'objet.
     *
     * @return la largeur de l'objet
     */
    public float getWidth() {
        return shape.width;
    }

    /**
     * Retourne la hauteur de l'objet.
     *
     * @return la hauteur de l'objet
     */
    public float getHeight() {
        return shape.height;
    }

    /**
     * Retourne la position de l'objet sous forme de rectangle.
     *
     * @return la position de l'objet
     */
    public Rectangle getPosition() {
        return shape;
    }

    /**
     * Définit la position de l'objet.
     *
     * @param x la nouvelle coordonnée x de l'objet
     * @param y la nouvelle coordonnée y de l'objet
     */
    public void setPosition(float x, float y) {
        shape.setPosition(x, y);
    }
}
